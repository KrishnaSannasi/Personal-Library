package math;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;

public class Formatter {
	private static final HashMap<Integer , String> prefixes = new HashMap<>();
	
	public static String		MONEY_SYMBOL	= "$";
	public static boolean	MONEY_BEFORE	= true;
	
	static {
		prefixes.put(0 , "");
		prefixes.put(1 , "K");
		prefixes.put(2 , "M");
		prefixes.put(3 , "B");
		prefixes.put(4 , "T");
		prefixes.put(5 , "Qa");
		prefixes.put(6 , "Qi");
		prefixes.put(7 , "Sx");
		prefixes.put(8 , "Sp");
		prefixes.put(9 , "Oc");
		prefixes.put(10 , "No");
		int n = 10;
		String[] pre1 = {"Dc" , "Vi" , "Tri" , "Qa" , "Qi" , "Sx" , "Sp" , "Oc" , "No"};
		String[] pre2 = {"" , "U" , "Du" , "Tre" , "Qa" , "Qi" , "Sx" , "Sp" , "Oc" , "No"};
		for(int i = 0; i < pre1.length; i++) {
			for(int j = 0; j < pre2.length; j++) {
				prefixes.put(++n , pre2[j] + pre1[i]);
			}
		}
	}
	
	public static String formatformat(double number , int precision) {
		return format((int) number) + "." + String.format("%." + precision + "f" , number - (int) number).substring(2);
	}
	
	public static String format(BigDecimal number , int precision) {
		return format(number.toBigInteger()) + "." + String.format("%." + precision + "f" , number.subtract(number.setScale(0 , BigDecimal.ROUND_FLOOR))).substring(2);
	}
	
	public static String formatMoney(double number) {
		if(MONEY_BEFORE)
			return MONEY_SYMBOL + format(number);
		else
			return format(number) + MONEY_SYMBOL;
	}
	
	public static String formatMoney(BigDecimal number) {
		if(MONEY_BEFORE)
			return MONEY_SYMBOL + " " + format(number);
		else
			return format(number) + " " + MONEY_SYMBOL;
	}
	
	public static String format(double number) {
		int l = (int) (Math.log10(number) - 1) / 3;
		return String.format("%.3f %s" , number / Math.pow(10 , l * 3) , prefixes.get(l));
	}
	
	public static String format(BigDecimal bigDecimal) {
		int l = bigDecimal.toString().indexOf('.');
		if(l == -1)
			l = bigDecimal.toString().length();
		l--;
		l /= 3;
		BigDecimal power = BigDecimal.TEN.pow(l * 3);
		return bigDecimal.divide(power).setScale(3 , BigDecimal.ROUND_FLOOR) + " " + prefixes.get(l);
	}
	
	public static String format(BigInteger bigInteger) {
		int l = (bigInteger.toString().length() - 1) / 3;
		BigDecimal power = BigDecimal.TEN.pow(l * 3);
		return new BigDecimal(bigInteger).divide(power).setScale(3 , BigDecimal.ROUND_FLOOR) + " " + prefixes.get(l);
	}
}
