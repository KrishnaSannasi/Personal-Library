package internet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.CharBuffer;
import java.util.TreeSet;
import java.util.function.Consumer;

public class Server implements AutoCloseable {
	private static TreeSet<Integer> availablePorts , portsInUse;
	
	static {
		portsInUse = new TreeSet<>();
		availablePorts = new TreeSet<>();
		for(int i = 1024; i < 65536; i++) {
			availablePorts.add(i);
		}
	}
	
	private static int getNextAvailablePort() {
		int port = availablePorts.first();
		availablePorts.remove(port);
		portsInUse.add(port);
		return port;
	}
	
	private static int releasePort(int port) {
		availablePorts.add(port);
		portsInUse.remove(port);
		return port;
	}
	
	private int					port;
	private ServerSocket		serverSocket;
	private PrintWriter		out;
	private BufferedReader	in;
	private Socket				clientSocket;
	
	public Server() throws IOException {
		this.port = getNextAvailablePort();
		serverSocket = new ServerSocket(port);
		clientSocket = null;
	}
	
	public void getConnection() throws IOException {
		clientSocket = serverSocket.accept();
		out = new PrintWriter(clientSocket.getOutputStream() , true);
		in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
	}
	
	public int getPort() {
		return port;
	}
	
	public ServerSocket getServerSocket() {
		return serverSocket;
	}
	
	public Socket getClientSocket() {
		return clientSocket;
	}
	
	public PrintWriter getOut() {
		return out;
	}
	
	public BufferedReader getIn() {
		return in;
	}
	
	public String getHostName() {
		return serverSocket.getInetAddress().getHostName();
	}
	
	public void printfln(String format , Object... args) {
		out.printf(format + '\n' , args);
	}
	
	public void println(Object o) {
		out.println(o);
	}
	
	public void printf(String format , Object... args) {
		out.printf(format , args);
	}
	
	public void print(Object o) {
		out.print(o);
	}
	
	public String readLine() throws IOException {
		return in.readLine();
	}
	
	public int read() throws IOException {
		return in.read();
	}
	
	public int read(char[] charBuff) throws IOException {
		return in.read(charBuff);
	}
	
	public int read(CharBuffer target) throws IOException {
		return in.read(target);
	}
	
	public int read(char[] charBuff , int offset , int length) throws IOException {
		return in.read(charBuff , offset , length);
	}
	
	@Override
	public void close() throws IOException {
		out.close();
		in.close();
		clientSocket.close();
		serverSocket.close();
		releasePort(port);
	}
	
	@SafeVarargs
	public static void startServer(Consumer<Server>... operations) {
		try {
			startServer(new Server() , operations);
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	@SafeVarargs
	public static void startServer(Server s , Consumer<Server>... operations) {
		new Thread() {
			public void run() {
				try(
				ServerSocket serverSocket = s.serverSocket;
				Socket clientSocket = serverSocket.accept();
				PrintWriter out = new PrintWriter(clientSocket.getOutputStream() , true);
				BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));) {
					String inputLine;
					while((inputLine = in.readLine()) != null) {
						out.println(inputLine);
					}
				}
				catch(IOException e) {
					System.out.println("Exception caught when trying to listen on port " + s.port + " or listening for a connection");
					System.out.println(e.getMessage());
				}
			}
		}.start();
	}
}
