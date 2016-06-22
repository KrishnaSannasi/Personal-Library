package math;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class BigDecimal_INF extends BigDecimal {
	public static final BigDecimal_INF	POS_INF	= new BigDecimal_INF(true) , NEG_INF = new BigDecimal_INF(false);
	public static final BigDecimal_INF	ZERO		= new BigDecimal_INF(0);
	public static final BigDecimal_INF	NEG_ONE	= new BigDecimal_INF(-1);
	public static final BigDecimal_INF	ONE		= new BigDecimal_INF(1);
	public static final BigDecimal_INF	TEN		= new BigDecimal_INF(10);
	private boolean							isInfinite;
	
	private BigDecimal_INF(boolean isPositive) {
		super(isPositive ? 1 : -1);
		isInfinite = true;
	}
	
	public BigDecimal_INF(double val) {
		super(val);
		isInfinite = false;
	}
	
	public BigDecimal_INF(String val) {
		super(val);
		isInfinite = false;
	}
	
	public BigDecimal_INF(BigDecimal val) {
		super(val.toString());
		isInfinite = false;
	}
	
	public BigDecimal_INF multiply(BigDecimal_INF multiplicand) {
		if(isInfinite)
			if(multiplicand.equals(BigDecimal.ZERO))
				throw new ArithmeticException("Inf * 0 = Undefined");
			else
				return this;
		else
			return new BigDecimal_INF(super.multiply((BigDecimal) multiplicand));
	}
	
	public BigDecimal_INF multiply(BigDecimal_INF multiplicand , MathContext mc) {
		if(isInfinite)
			if(multiplicand.equals(BigDecimal.ZERO))
				throw new ArithmeticException("Inf * 0 = Undefined");
			else
				return this;
		else
			return new BigDecimal_INF(super.multiply((BigDecimal) multiplicand , mc));
	}
	
	public BigDecimal_INF divide(BigDecimal_INF divisor) {
		if(isInfinite)
			if(divisor.isInfinite)
				throw new ArithmeticException("Inf / Inf = Undefined");
			else
				return this;
		else
			return new BigDecimal_INF(super.divide((BigDecimal) divisor));
	}
	
	public BigDecimal_INF divide(BigDecimal_INF divisor , MathContext mc) {
		if(isInfinite)
			if(divisor.isInfinite)
				throw new ArithmeticException("Inf / Inf = Undefined");
			else
				return this;
		else
			return new BigDecimal_INF(super.divide((BigDecimal) divisor , mc));
	}
	
	public BigDecimal_INF divide(BigDecimal_INF divisor , int roundingMode) {
		if(isInfinite)
			if(divisor.isInfinite)
				throw new ArithmeticException("Inf / Inf = Undefined");
			else
				return this;
		else
			return new BigDecimal_INF(super.divide((BigDecimal) divisor , roundingMode));
	}
	
	public BigDecimal_INF pow(int n) {
		if(isInfinite)
			if(n % 2 == 0)
				return POS_INF;
			else
				return this;
		return new BigDecimal_INF(super.pow(n));
	}
	
	public BigDecimal_INF pow(int n , MathContext mc) {
		if(isInfinite)
			if(n % 2 == 0)
				return POS_INF;
			else
				return this;
		return new BigDecimal_INF(super.pow(n , mc));
	}
	
	public BigDecimal_INF add(BigDecimal_INF augend) {
		if(isInfinite)
			if(augend.isInfinite && signum() != augend.signum())
				throw new ArithmeticException("+INF - INF = Undefined");
			else
				return this;
		else
			return new BigDecimal_INF(super.add((BigDecimal) augend));
	}
	
	public BigDecimal_INF add(BigDecimal_INF augend , MathContext mc) {
		if(isInfinite)
			if(augend.isInfinite && signum() != augend.signum())
				throw new ArithmeticException("+INF - INF = Undefined");
			else
				return this;
		else
			return new BigDecimal_INF(super.add((BigDecimal) augend , mc));
	}
	
	public BigDecimal_INF subtract(BigDecimal_INF augend) {
		if(isInfinite)
			if(augend.isInfinite && signum() != augend.signum())
				throw new ArithmeticException("+INF - INF = Undefined");
			else
				return this;
		else
			return new BigDecimal_INF(super.subtract((BigDecimal) augend));
	}
	
	public BigDecimal_INF subtract(BigDecimal_INF augend , MathContext mc) {
		if(isInfinite)
			if(augend.isInfinite && signum() != augend.signum())
				throw new ArithmeticException("+INF - INF = Undefined");
			else
				return this;
		else
			return new BigDecimal_INF(super.subtract((BigDecimal) augend , mc));
	}
	
	@Override
	public BigDecimal_INF negate() {
		return multiply(NEG_ONE);
	}
	
	@Override
	public BigDecimal_INF negate(MathContext mc) {
		return multiply(NEG_ONE , mc);
	}
	
	@Override
	public BigDecimal_INF max(BigDecimal val) {
		if(compareTo(val) < 0)
			return new BigDecimal_INF(val);
		else
			return this;
	}
	
	@Override
	public BigDecimal_INF min(BigDecimal val) {
		if(compareTo(val) > 0)
			return new BigDecimal_INF(val);
		else
			return this;
	}
	
	@Override
	public BigDecimal_INF abs() {
		if(signum() < 0)
			return negate();
		else
			return this;
	}
	
	@Override
	public BigDecimal_INF abs(MathContext mc) {
		if(signum() < 0)
			return negate(mc);
		else
			return multiply(ONE , mc);
	}
	
	@Override
	public double doubleValue() {
		if(isInfinite)
			if(signum() < 0)
				return Double.NEGATIVE_INFINITY;
			else
				return Double.POSITIVE_INFINITY;
		return super.doubleValue();
	}
	
	public BigDecimal_INF setScale(int newScale) {
		return new BigDecimal_INF(super.setScale(newScale));
	}
	
	public BigDecimal_INF setScale(int newScale , int roundingMode) {
		return new BigDecimal_INF(super.setScale(newScale , roundingMode));
	}
	
	public BigDecimal_INF setScale(int newScale , RoundingMode roundingMode) {
		return new BigDecimal_INF(super.setScale(newScale , roundingMode));
	}
	
	@Override
	public int compareTo(BigDecimal val) {
		if(val instanceof BigDecimal_INF) {
			BigDecimal_INF d = (BigDecimal_INF) val;
			if(isInfinite && !d.isInfinite)
				if(signum() > 0)
					return 1;
				else
					return -1;
		}
		return super.compareTo(val);
		
	}
	
	@Override
	public boolean equals(Object x) {
		if(x instanceof BigDecimal_INF) {
			BigDecimal_INF d = (BigDecimal_INF) x;
			return d.isInfinite == isInfinite && super.equals(d);
		}
		else if(x instanceof BigDecimal) {
			return !isInfinite && super.equals(x);
		}
		return super.equals(x);
	}
	
	@Override
	public String toString() {
		if(isInfinite)
			if(signum() < 0)
				return "-INF";
			else
				return "+INF";
		else
			return super.toString();
	}
}
