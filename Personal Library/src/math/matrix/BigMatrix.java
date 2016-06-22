package math.matrix;

import java.math.MathContext;

import math.BigDecimal_INF;

public class BigMatrix extends AbstractMatrix<BigDecimal_INF , BigMatrix> {
	private MathContext mc;
	
	public BigMatrix(int width , int height) {
		this(new BigDecimal_INF[width][height]);
	}
	
	private BigMatrix(BigDecimal_INF[][] matrix) {
		super(matrix);
		mc = MathContext.DECIMAL128;
	}
	
	public void setMathContext(MathContext mc) {
		if(mc != null)
			this.mc = mc;
	}
	
	@Override
	public BigMatrix add(BigMatrix matrix) {
		if(matrix.width != width || matrix.height != height)
			throw new IllegalArgumentException();
			
		BigDecimal_INF[][] sum = new BigDecimal_INF[width][height];
		
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < height; j++) {
				sum[i][j] = get(i , j).add(matrix.get(i , j));
			}
		}
		
		return new BigMatrix(sum);
	}
	
	@Override
	public BigMatrix subtract(BigMatrix matrix) {
		if(matrix.width != width || matrix.height != height)
			throw new IllegalArgumentException();
			
		BigDecimal_INF[][] sum = new BigDecimal_INF[width][height];
		
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < height; j++) {
				sum[i][j] = get(i , j).subtract(matrix.get(i , j));
			}
		}
		
		return new BigMatrix(sum);
	}
	
	@Override
	public BigMatrix multiply(BigDecimal_INF scalar) {
		BigDecimal_INF[][] product = new BigDecimal_INF[width][height];
		
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < height; j++) {
				product[i][j] = get(i , j).multiply(scalar , mc);
			}
		}
		
		return new BigMatrix(product);
	}
	
	@Override
	public BigMatrix multiply(BigMatrix matrix) {
		if(matrix.height != width) {
			throw new UnsupportedOperationException();
		}
		
		BigDecimal_INF[][] product = new BigDecimal_INF[height][matrix.width];
		
		for(int i = 0; i < height; i++) {
			for(int j = 0; j < matrix.width; j++) {
				product[i][j] = BigDecimal_INF.ZERO;
				for(int ii = 0; ii < width; ii++) {
					product[i][j] = get(i , ii).multiply(matrix.get(ii , j)).add(product[i][j]);
				}
			}
		}
		
		return new BigMatrix(product);
	}
	
	@Override
	public BigMatrix pow(int pow) {
		if(!isSquare())
			throw new UnsupportedOperationException();
		if(pow == 0) {
			return new IdentityMatrix(width);
		}
		if(pow < 0) {
			return invert().pow(-pow);
		}
		BigMatrix matrix = this;
		
		for(int i = 0; i < pow; i++) {
			matrix = matrix.multiply(this);
		}
		
		return matrix;
	}
	
	@Override
	public BigDecimal_INF det() {
		if(!isSquare()) {
			throw new UnsupportedOperationException();
		}
		
		if(width == 0)
			return BigDecimal_INF.ZERO;
		if(width == 1)
			return get(0 , 0);
		if(width == 2)
			return get(0 , 0).multiply(get(1 , 1) , mc).subtract(get(1 , 0).multiply(get(0 , 1) , mc));
			
		BigDecimal_INF det = BigDecimal_INF.ZERO;
		
		for(int i = 0; i < width; i++) {
			BigDecimal_INF num = get(0 , i).multiply(adj(0 , i).det());
			if(i % 2 == 0)
				det = det.add(num);
			else
				det = det.subtract(num);
		}
		
		return det;
	}
	
	@Override
	public BigMatrix negate() {
		return multiply(BigDecimal_INF.NEG_ONE);
	}
	
	@Override
	public BigMatrix transpose() {
		BigDecimal_INF[][] matrix = new BigDecimal_INF[height][width];
		
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < height; j++) {
				matrix[j][i] = get(i , j);
			}
		}
		
		return new BigMatrix(matrix);
	}
	
	@Override
	public BigMatrix adj(int row , int col) {
		BigDecimal_INF[][] matrix = new BigDecimal_INF[width - 1][height - 1];
		
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < height; j++) {
				if(i != row && j != col)
					matrix[i - (i > row ? 1 : 0)][j - (j > col ? 1 : 0)] = get(i , j);
			}
		}
		
		return new BigMatrix(matrix);
	}
	
	@Override
	public BigMatrix adj() {
		BigDecimal_INF[][] adj = new BigDecimal_INF[width][height];
		BigMatrix m = this.transpose();
		
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < height; j++) {
				adj[i][j] = m.adj(i , j).det();
				if(i + j % 2 == 1)
					adj[i][j] = adj[i][j].negate();
			}
		}
		
		return new BigMatrix(adj);
	}
	
	@Override
	public BigMatrix invert() {
		return adj().multiply(BigDecimal_INF.ONE.divide(det() , mc));
	}
	
	@Override
	public String toString() {
		if(height == 0 || width == 0) {
			return "[ ]";
		}
		StringBuilder builder = new StringBuilder();
		builder.append("[");
		for(int j = 0; j < height; j++) {
			if(j != 0)
				builder.append(" ");
			builder.append("[");
			for(int i = 0; i < width; i++) {
				builder.append(get(j , i).setScale(mc.getPrecision()));
				if(i != width - 1)
					builder.append(", ");
			}
			builder.append("]");
			if(j != height - 1)
				builder.append("\n");
			else
				builder.append("]");
		}
		return builder.toString();
	}
	
	private static class IdentityMatrix extends BigMatrix {
		public IdentityMatrix(int size) {
			super(create(size));
		}
		
		private static BigDecimal_INF[][] create(int size) {
			BigDecimal_INF[][] matrix = new BigDecimal_INF[size][size];
			for(int i = 0; i < size; i++)
				for(int j = 0; j < size; j++)
					if(i == j)
						matrix[i][j] = BigDecimal_INF.ONE;
					else
						matrix[i][j] = BigDecimal_INF.ZERO;
			return matrix;
		}
	}
}
