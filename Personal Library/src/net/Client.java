package net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.function.BiConsumer;

import io.InputOutput;

public class Client implements AutoCloseable {
	private Socket				socket;
	private BufferedReader	input;
	private PrintWriter		output;
	private InputOutput		io;
	private String				hostName;
	private int					port;
	
	public Client(int port , String hostName) throws UnknownHostException , IOException {
		socket = new Socket(hostName , port);
		this.hostName = hostName;
		this.port = port;
	}
	
	public Socket getSocket() {
		return socket;
	}
	
	public InputOutput getIO() {
		return io;
	}
	
	@Override
	public void close() throws IOException {
		if(io != null)
			io.close();
		if(input != null)
			input.close();
		if(input != null)
			output.close();
		socket.close();
		io = null;
		socket = null;
	}
	
	@SafeVarargs
	public static void startClient(Client c , InputStream inputStream , BiConsumer<BufferedReader , PrintWriter>... consumers) {
		new Thread() {
			public void run() {
				try(
				Socket echoSocket = c.socket = new Socket(c.hostName , c.port);
				BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))) {
					PrintWriter out = c.output = new PrintWriter(echoSocket.getOutputStream() , true);
					BufferedReader in = c.input = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
					String userInput;
					while((userInput = stdIn.readLine()) != null) {
						out.println(userInput);
						System.out.println("echo: " + in.readLine());
					}
				}
				catch(UnknownHostException e) {
					System.err.println("Don't know about host " + c.hostName);
					System.exit(1);
				}
				catch(IOException e) {
					e.printStackTrace();
					System.err.println("Couldn't get I/O for the connection to " + c.hostName);
					System.exit(1);
				}
			}
		}.start();
	}
}
