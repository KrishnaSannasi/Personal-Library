package math.matrix;

public class Matrix extends AbstractMatrix<Double , Matrix> {
	public Matrix(int width , int height) {
		this(new Double[width][height]);
	}
	
	public Matrix(Double[][] matrix) {
		super(matrix);
	}
	
	@Override
	public Matrix add(Matrix matrix) {
		if(matrix.width != width || matrix.height != height)
			throw new IllegalArgumentException();
			
		Double[][] sum = new Double[width][height];
		
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < height; j++) {
				sum[i][j] = get(i , j) + matrix.get(i , j);
			}
		}
		
		return new Matrix(sum);
	}
	
	@Override
	public Matrix subtract(Matrix matrix) {
		if(matrix.width != width || matrix.height != height)
			throw new IllegalArgumentException();
			
		Double[][] sum = new Double[width][height];
		
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < height; j++) {
				sum[i][j] = get(i , j) - matrix.get(i , j);
			}
		}
		
		return new Matrix(sum);
	}
	
	@Override
	public Matrix multiply(Double scalar) {
		Double[][] product = new Double[width][height];
		
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < height; j++) {
				product[i][j] = scalar * get(i , j);
			}
		}
		
		return new Matrix(product);
	}
	
	@Override
	public Matrix multiply(Matrix matrix) {
		if(matrix.height != width) {
			throw new UnsupportedOperationException();
		}
		
		Double[][] product = new Double[height][matrix.width];
		
		for(int i = 0; i < height; i++) {
			for(int j = 0; j < matrix.width; j++) {
				product[i][j] = 0d;
				for(int ii = 0; ii < width; ii++) {
					product[i][j] += get(i , ii) * matrix.get(ii , j);
				}
			}
		}
		
		return new Matrix(product);
	}
	
	@Override
	public Matrix pow(int pow) {
		if(!isSquare())
			throw new UnsupportedOperationException();
		if(pow == 0) {
			return new IdentityMatrix(width);
		}
		if(pow < 0) {
			return invert().pow(-pow);
		}
		Matrix matrix = this;
		
		for(int i = 0; i < pow; i++) {
			matrix = matrix.multiply(this);
		}
		
		return matrix;
	}
	
	@Override
	public Double det() {
		if(!isSquare()) {
			throw new UnsupportedOperationException();
		}
		
		if(width == 0)
			return 0d;
		if(width == 1)
			return get(0 , 0);
		if(width == 2)
			return get(0 , 0) * get(1 , 1) - get(1 , 0) * get(0 , 1);
			
		double det = 0;
		
		for(int i = 0; i < width; i++) {
			det += Math.pow(-1 , i) * get(0 , i) * adj(0 , i).det();
		}
		
		return det;
	}
	
	@Override
	public Matrix negate() {
		return multiply(-1d);
	}
	
	@Override
	public Matrix transpose() {
		Double[][] matrix = new Double[height][width];
		
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < height; j++) {
				matrix[j][i] = get(i , j);
			}
		}
		
		return new Matrix(matrix);
	}
	
	@Override
	public Matrix adj(int row , int col) {
		Double[][] matrix = new Double[width - 1][height - 1];
		
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < height; j++) {
				if(i != row && j != col)
					matrix[i - (i > row ? 1 : 0)][j - (j > col ? 1 : 0)] = get(i , j);
			}
		}
		
		return new Matrix(matrix);
	}
	
	@Override
	public Matrix adj() {
		Double[][] adj = new Double[width][height];
		Matrix m = this.transpose();
		
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < height; j++) {
				adj[i][j] = Math.pow(-1 , i + j) * m.adj(i , j).det();
			}
		}
		
		return new Matrix(adj);
	}
	
	@Override
	public Matrix invert() {
		return adj().multiply(1 / det());
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
				builder.append(get(j , i));
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
	
	public static Matrix createIdentityMatrix(int size) {
		return new IdentityMatrix(size);
	}
	
	private static class IdentityMatrix extends Matrix {
		public IdentityMatrix(int size) {
			super(create(size));
		}
		
		private static Double[][] create(int size) {
			Double[][] matrix = new Double[size][size];
			for(int i = 0; i < size; i++)
				matrix[i][i] = 1.;
			return matrix;
		}
	}
}
