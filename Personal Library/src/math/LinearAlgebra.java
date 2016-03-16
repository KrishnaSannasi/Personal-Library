package math;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class LinearAlgebra {
	public static void main(String[] args) {
		final double stay_in , stay_out;
		
		stay_in = .99;
		stay_out = .995;
		System.out.println(stay_in + "\t" + stay_out);
		
		double[][] a = {{stay_in , 1 - stay_out} , {1 - stay_in , stay_out}};
		double[][] b = {{(int) (Math.random() * 1000000)} , {(int) (Math.random() * 1000000)}};
		double[][] c;
		int y = 0;
		do {
			System.out.println(b[0][0] + "\t" + b[1][0]);
			c = round(b , 0);
			b = multiply(a , b);
			y++;
		} while(!Arrays.deepEquals(round(b , 0) , c));
		System.out.println(y);
	}
	
	public static void main1(String[] args) {
		Scanner scan = new Scanner(System.in);
		ArrayList<String> arrayString = new ArrayList<>();
		String line = "";
		boolean f = false;
		System.out.println("enter array");
		do {
			if(f)
				arrayString.add(line);
			line = scan.nextLine().trim().toLowerCase();
			f = true;
		} while(line.charAt(0) != 'd');
		scan.close();
		double[][] array = new double[arrayString.size()][];
		for(int i = 0; i < array.length; i++) {
			String[] parts = arrayString.get(i).split("[ ,]+");
			array[i] = new double[parts.length];
			for(int j = 0; j < array[i].length; j++) {
				array[i][j] = Double.parseDouble(parts[j]);
			}
		}
		try {
			rref(array , new ByteArrayOutputStream());
		}
		catch(NoSolutionException e) {
			System.out.println("No solutions");
		}
		catch(InfiniteSolutionException e) {
			System.out.println("Infinite solutions");
		}
	}
	
	private static double[][] copy(double[][] m) {
		double[][] t = new double[m.length][];
		for(int i = 0; i < m.length; i++) {
			t[i] = new double[m[i].length];
			for(int j = 0; j < m[i].length; j++) {
				t[i][j] = m[i][j];
			}
		}
		return t;
	}
	
	public static double[][] round(double[][] a , int precision) {
		a = copy(a);
		int power = (int) Math.pow(10 , precision);
		for(int i = 0; i < a.length; i++) {
			for(int j = 0; j < a[i].length; j++) {
				a[i][j] = Math.round(a[i][j] * power) / power;
			}
		}
		return a;
	}
	
	private static boolean isRectangle(double[][] a) {
		if(a.length == 0)
			return true;
		int size = a[0].length;
		for(double[] e: a) {
			if(size != e.length)
				return false;
		}
		return true;
	}
	
	public static int[] dim(double[][] a) {
		if(isRectangle(a))
			return new int[] {a.length == 0 ? 0 : a[0].length , a.length};
		throw new IllegalArgumentException("Non-Square Matrix");
	}
	
	public static double[][] multiply(double[][] a , double b) {
		a = copy(a);
		for(int i = 0; i < a.length; i++) {
			for(int j = 0; j < a[i].length; j++) {
				a[i][j] *= b;
			}
		}
		return a;
	}
	
	public static double[][] multiply(double[][] a , double[][] b) {
		int[] dima = dim(a);
		int[] dimb = dim(b);
		double[][] result = new double[dima[1]][dimb[0]];
		if(dimb[1] != dima[0])
			throw new IllegalArgumentException("Invalid dimentions");
		int s = dimb[1];
		for(int i = 0; i < dimb[0]; i++) {
			for(int j = 0; j < dima[1]; j++) {
				for(int k = 0; k < s; k++) {
					result[j][i] += a[j][k] * b[k][i];
				}
			}
		}
		return result;
	}
	
	public static double det(double[][] matrix) {
		return det(matrix , false);
	}
	
	public static double det(double[][] matrix , boolean print) {
		return det(matrix , print , System.out);
	}
	
	public static double det(double[][] matrix , boolean print , OutputStream stream) {
		final int size = matrix.length;
		for(int i = 0; i < matrix.length; i++) {
			if(size != matrix[i].length)
				throw new IllegalArgumentException("Matrix is not a square");
		}
		if(print) {
			PrintStream out = new PrintStream(stream);
			for(double[] row: matrix) {
				out.println(Arrays.toString(row));
			}
		}
		switch(size) {
			case 0:
				return 0;
			case 1:
				return matrix[0][0];
			case 2:
				return matrix[0][0] * matrix[1][1] - matrix[1][0] * matrix[0][1];
			default:
				break;
		}
		double[][] smaller = new double[size - 1][size - 1];
		double det = 0 , mul = -1;
		int l;
		for(int i = 0; i < size; i++) {
			for(int j = 1; j < size; j++) {
				l = 0;
				for(int k = 0; k < size; k++) {
					if(i == k)
						l = 1;
					else {
						smaller[j - 1][k - l] = matrix[j][k];
					}
				}
			}
			det += (mul *= -1) * matrix[0][i] * det(smaller , false);
		}
		return det;
	}
	
	public static boolean isSingular(double[][] matrix) {
		return det(matrix) == 0;
	}
	
	public static final double[][] rref(double[][] array) throws NoSolutionException , InfiniteSolutionException {
		return rref(array , System.out);
	}
	
	public static final double[][] rref(double[][] array , OutputStream... streams) throws NoSolutionException , InfiniteSolutionException {
		PrintStream[] printStreams = new PrintStream[streams.length];
		for(int i = 0; i < streams.length; i++) {
			if(streams[i] == null)
				continue;
			printStreams[i] = new PrintStream(streams[i]);
		}
		print(array);
		for(int row = 0; row < array.length - 1; row++) {
			for(int r = row + 1; r < array.length; r++) {
				double a = array[row][row] , b = array[r][row];
				for(int col = 0; col < array[r].length; col++) {
					array[r][col] = array[r][col] * a - array[row][col] * b;
				}
				print(array);
			}
		}
		for(int row = 0; row < array.length; row++) {
			double a = array[row][row];
			if(a == 0)
				continue;
			for(int col = 0; col < array[row].length; col++) {
				array[row][col] /= a;
			}
		}
		print(array);
		for(int i = 0; i < array.length; i++) {
			int sum = 0;
			for(int j = 0; j < array[i].length - 1; j++) {
				sum += array[i][j];
			}
			if(sum == 0) {
				if(array[i][array[i].length - 1] != 0)
					throw new NoSolutionException();
				else
					throw new InfiniteSolutionException();
			}
		}
		for(int row = array.length - 1; row > 0; row--) {
			for(int r = row - 1; r >= 0; r--) {
				double a = array[r][row];
				for(int col = array.length - 1; col >= 0; col--) {
					array[r][col] = array[r][col] - a * array[row][col];
				}
				print(array);
			}
		}
		return array;
	}
	
	public static final void print(double[][] array) {
		int i = 0;
		for(double[] e: array) {
			System.out.println((i++ == 0 ? "[" : " ") + Arrays.toString(e).replace(".0," , ",").replace(".0]" , "]") + (i == array.length ? "]" : ""));
		}
		System.out.println();
	}
	
	private static class InfiniteSolutionException extends Exception {
		private static final long serialVersionUID = -5076054239018266909L;
		
		public InfiniteSolutionException() {
			super("Infinite Solutions");
		}
	}
	
	private static class NoSolutionException extends Exception {
		private static final long serialVersionUID = -5281834175723867387L;
		
		public NoSolutionException() {
			super("No Solutions");
		}
	}
}
