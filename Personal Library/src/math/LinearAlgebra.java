package math;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class LinearAlgebra {
	public static void main(String[] args) {
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
		Arrays.stream(array).forEach(e -> System.out.println(Arrays.toString(e).replace(".0," , ",").replace(".0]" , "]")));
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
