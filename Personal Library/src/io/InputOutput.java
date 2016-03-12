package io;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Scanner;

public final class InputOutput {
	public static final InputOutput	STD			= new InputOutput(System.in , System.out , true) ,
	ERR = new InputOutput(System.err , false);
	public boolean							writeToSTD	= false;
	private OutputStream					output;
	private PrintStream					pStream;
	private BufferedInputStream		input;
	private Scanner						scan;
	private boolean						isConstant;
	
	static {
		STD.makeConstant();
		ERR.makeConstant();
	}
	
	public InputOutput(InputStream input , OutputStream output , boolean writeToSTD) {
		setInput(input);
		setOutput(output);
		this.writeToSTD = writeToSTD;
		isConstant = false;
	}
	
	public InputOutput(InputStream input , OutputStream output) {
		this(input , output , false);
	}
	
	public InputOutput(OutputStream output , boolean writeToSTD) {
		this(null , output , writeToSTD);
	}
	
	public InputOutput(OutputStream output) {
		this(output , false);
	}
	
	public InputOutput(InputStream input) {
		this(input , null);
	}
	
	public InputOutput() {
		this((InputStream) null);
	}
	
	public InputOutput(String filePath) throws FileNotFoundException {
		this(new FileInputStream(new File(filePath).getAbsolutePath().replace("\\.." , "")));
	}
	
	public InputOutput(InputOutput other) {
		this(other.input , other.output , other.writeToSTD);
		isConstant = other.isConstant;
	}
	
	public void println() {
		print("\n");
	}
	
	public void println(Object message) {
		message = convertMessage(message);
		print(message + "\n");
	}
	
	private Object convertMessage(Object message) {
		if(message instanceof Object[]) {
			message = Arrays.deepToString((Object[]) message);
		}
		if(message instanceof float[]) {
			message = Arrays.toString((float[]) message);
		}
		if(message instanceof double[]) {
			message = Arrays.toString((double[]) message);
		}
		if(message instanceof byte[]) {
			message = Arrays.toString((byte[]) message);
		}
		if(message instanceof short[]) {
			message = Arrays.toString((short[]) message);
		}
		if(message instanceof int[]) {
			message = Arrays.toString((int[]) message);
		}
		if(message instanceof long[]) {
			message = Arrays.toString((long[]) message);
		}
		if(message instanceof boolean[]) {
			message = Arrays.toString((boolean[]) message);
		}
		return message;
	}
	
	public void printfln(String format , Object... args) {
		print(String.format(format + "\n" , args));
	}
	
	public void printf(String format , Object... args) {
		print(String.format(format , args));
	}
	
	public synchronized void print(Object message) {
		message = convertMessage(message);
		if(writeToSTD) {
			System.out.print(message);
		}
		if(output != System.out) {
			pStream.print(message);
		}
	}
	
	public String next() {
		return scan.next();
	}
	
	public byte nextByte() {
		return scan.nextByte();
	}
	
	public short nextShort() {
		return scan.nextShort();
	}
	
	public int nextInt() {
		int i = scan.nextInt();
		scan.nextLine();
		return i;
	}
	
	public long nextLong() {
		return scan.nextLong();
	}
	
	public float nextFloat() {
		return scan.nextFloat();
	}
	
	public double nextDouble() {
		return scan.nextDouble();
	}
	
	public boolean nextBoolean() {
		return scan.nextBoolean();
	}
	
	public String nextLine() {
		return scan.nextLine();
	}
	
	public BigInteger nextBigInteger() {
		return scan.nextBigInteger();
	}
	
	public BigInteger nextBigInteger(int radix) {
		return scan.nextBigInteger(radix);
	}
	
	public BigDecimal nextBigDecimal() {
		return scan.nextBigDecimal();
	}
	
	public String next(String text) {
		print(text);
		return scan.next();
	}
	
	public byte nextByte(String text) {
		print(text);
		return scan.nextByte();
	}
	
	public short nextShort(String text) {
		print(text);
		return scan.nextShort();
	}
	
	public int nextInt(String text) {
		print(text);
		int i = scan.nextInt();
		scan.nextLine();
		return i;
	}
	
	public long nextLong(String text) {
		print(text);
		return scan.nextLong();
	}
	
	public float nextFloat(String text) {
		print(text);
		return scan.nextFloat();
	}
	
	public double nextDouble(String text) {
		print(text);
		return scan.nextDouble();
	}
	
	public boolean nextBoolean(String text) {
		print(text);
		return scan.nextBoolean();
	}
	
	public String nextLine(String text) {
		print(text);
		return scan.nextLine();
	}
	
	public BigInteger nextBigInteger(String text) {
		print(text);
		return scan.nextBigInteger();
	}
	
	public BigInteger nextBigInteger(String text , int radix) {
		print(text);
		return scan.nextBigInteger(radix);
	}
	
	public BigDecimal nextBigDecimal(String text) {
		print(text);
		return scan.nextBigDecimal();
	}
	
	public boolean hasNextByte() {
		return scan.hasNextByte();
	}
	
	public boolean hasNextShort() {
		return scan.hasNextShort();
	}
	
	public boolean hasNextInt() {
		return scan.hasNextInt();
	}
	
	public boolean hasNextLong() {
		return scan.hasNextLong();
	}
	
	public boolean hasNextFloat() {
		return scan.hasNextFloat();
	}
	
	public boolean hasNextDouble() {
		return scan.hasNextDouble();
	}
	
	public boolean hasNextBoolean() {
		return scan.hasNextBoolean();
	}
	
	public boolean hasNextLine() {
		return scan.hasNextLine();
	}
	
	public boolean hasNextBigInteger() {
		return scan.hasNextBigInteger();
	}
	
	public boolean hasNextBigInteger(int radix) {
		return scan.hasNextBigInteger(radix);
	}
	
	public boolean hasNextBigDecimal() {
		return scan.hasNextBigDecimal();
	}
	
	public void reset() {
		try {
			input.reset();
		}
		catch(IOException e) {
		}
	}
	
	public Scanner getScan() {
		return scan;
	}
	
	public PrintStream getpStream() {
		return pStream;
	}
	
	public OutputStream getOutput() {
		return output;
	}
	
	public InputStream getInput() {
		return input;
	}
	
	public void setOutput(OutputStream output) {
		if(isConstant) {
			throw new RuntimeException("Cannot set IO output");
		}
		closeOutput();
		this.output = output;
		pStream = new PrintStream(output);
	}
	
	public void setOutput(OutputStream outputStream , boolean writeToSTD) {
		setOutput(outputStream);
		this.writeToSTD = writeToSTD;
	}
	
	public void setInput(InputStream input) {
		if(isConstant) {
			throw new RuntimeException("Cannot set constant IO input");
		}
		closeInput();
		this.input = new BufferedInputStream(input);
		scan = new Scanner(this.input);
	}
	
	public void closeOutput() {
		if(output != System.out) {
			try {
				output.close();
				pStream.close();
				output = null;
				pStream = null;
			}
			catch(NullPointerException e) {
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void closeInput() {
		if(output != System.out) {
			try {
				scan.close();
				input.close();
				scan = null;
				input = null;
			}
			catch(NullPointerException e) {
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void close() {
		if(isConstant) {
			return;
		}
		closeOutput();
		closeInput();
	}
	
	public void makeConstant() {
		isConstant = true;
	}
	
	public boolean isConstant() {
		return isConstant;
	}
	
	@Override
	public InputOutput clone() {
		return new InputOutput(output , writeToSTD);
	}
	
	public static void exit(int status) {
		System.exit(status);
	}
	
	public static void exit() {
		exit(0);
	}
}
