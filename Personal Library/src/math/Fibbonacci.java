package math;

import java.math.BigInteger;
import java.util.HashMap;

public class Fibbonacci {
	private static final HashMap<Integer , BigInteger> FIBBONACCI = new HashMap<>();
	
	static {
		put(1 , BigInteger.ZERO);
		put(2 , BigInteger.ONE);
		fibb(10000);
	}
	
	public static BigInteger fibb(int n) {
		if(n < 1)
			return null;
		BigInteger fibb = FIBBONACCI.get(n);
		if(fibb != null)
			return fibb;
		BigInteger fibb1 = FIBBONACCI.get(n - 1);
		BigInteger fibb2 = FIBBONACCI.get(n - 2);
		if(fibb2 == null)
			fibb2 = fibb(n - 2);
		if(fibb1 == null)
			fibb1 = fibb(n - 1);
		fibb = fibb1.add(fibb2);
		put(n , fibb);
		return fibb;
	}
	
	private static void put(int n , BigInteger fibb) {
		if(!FIBBONACCI.containsKey(n))
			FIBBONACCI.put(n , fibb);
	}
}
