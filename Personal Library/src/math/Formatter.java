package math;

import java.math.BigDecimal;
import java.math.BigInteger;

public class Formatter {
	public static String		MONEY_SYMBOL	= "$";
	public static boolean	MONEY_BEFORE	= true;
	
	public static String toStringMoney(double number) {
		if(MONEY_BEFORE)
			return MONEY_SYMBOL + toString(number);
		else
			return toString(number) + MONEY_SYMBOL;
	}
	
	public static String toStringMoney(BigDecimal number) {
		if(MONEY_BEFORE)
			return MONEY_SYMBOL + toString(number);
		else
			return toString(number) + MONEY_SYMBOL;
	}
	
	public static String toString(int number) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(number);
		int size = buffer.length();
		for(int i = size - 3; i > 0; i -= 3) {
			buffer.insert(i , ',');
		}
		return buffer.toString();
	}
	
	public static String toString(BigInteger number) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(number);
		int size = buffer.length();
		for(int i = size - 3; i > 0; i -= 3) {
			buffer.insert(i , ',');
		}
		return buffer.toString();
	}
	
	public static String toString(double number) {
		return toString(number , 2);
	}
	
	public static String toString(double number , int precision) {
		return toString((int) number) + "." + String.format("%." + precision + "f" , number - (int) number).substring(2);
	}
	
	public static String toString(BigDecimal number) {
		return toString(number , 2);
	}
	
	public static String toString(BigDecimal number , int precision) {
		return toString(number.toBigInteger()) + "." + String.format("%." + precision + "f" , number.subtract(number.setScale(0 , BigDecimal.ROUND_FLOOR))).substring(2);
	}
}
