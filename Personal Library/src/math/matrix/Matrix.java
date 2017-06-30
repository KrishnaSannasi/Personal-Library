package math.matrix;

public class Matrix extends AbstractMatrix<Double , Matrix> {
    private int rank;
    
    public Matrix(Double[][] matrix) {
        this(matrix , false);
    }
    
    public Matrix(int numOfRow , int numOfCol) {
        this(new Double[numOfRow][numOfCol] , false , false);
    }
    
    public Matrix(Double[][] matrix , boolean isImmutable) {
        this(matrix , isImmutable , true);
    }
    
    private Matrix(Double[][] matrix , boolean isImmutable , boolean findRank) {
        super(matrix , isImmutable);
        if(findRank)
            findRank();
    }
    
    public Matrix(Matrix transfer) {
        super(transfer);
    }
    
    public int getRank() {
        return rank;
    }
    
    private int findRank() {
        return rank = rref().rank;
    }
    
    public Matrix sub(int ix , int iy , int nor , int noc) {
        Double[][] m = new Double[nor][noc];
        for(int x = 0; x < nor; x++) {
            for(int y = 0; y < nor; y++) {
                m[x][y] = get(ix + x , iy + y);
            }
        }
        return new Matrix(m , true , true);
    }
    
    @Override
    public Matrix add(Matrix matrix) {
        if(matrix.numOfCol != numOfCol || matrix.numOfRow != numOfRow)
            throw new IllegalArgumentException();
        
        Double[][] sum = new Double[numOfRow][numOfCol];
        
        for(int i = 0; i < numOfRow; i++) {
            for(int j = 0; j < numOfCol; j++) {
                sum[i][j] = get(i , j) + matrix.get(i , j);
            }
        }
        
        return new Matrix(sum);
    }
    
    @Override
    public Matrix subtract(Matrix matrix) {
        if(matrix.numOfCol != numOfCol || matrix.numOfRow != numOfRow)
            throw new IllegalArgumentException();
        
        Double[][] sum = new Double[numOfRow][numOfCol];
        
        for(int i = 0; i < numOfRow; i++) {
            for(int j = 0; j < numOfCol; j++) {
                sum[i][j] = get(i , j) - matrix.get(i , j);
            }
        }
        
        return new Matrix(sum);
    }
    
    @Override
    public Matrix multiply(Double scalar) {
        Double[][] product = new Double[numOfRow][numOfCol];
        
        for(int i = 0; i < numOfRow; i++) {
            for(int j = 0; j < numOfCol; j++) {
                product[i][j] = scalar * get(i , j);
            }
        }
        
        return new Matrix(product);
    }
    
    @Override
    public Matrix multiply(Matrix matrix) {
        if(matrix.numOfRow != numOfCol) {
            throw new UnsupportedOperationException(String.format("cannot multiply a %dx%d matrix with a %dx%d matrix" , numOfRow , numOfCol , matrix.numOfRow , numOfCol));
        }
        
        Double[][] product = new Double[numOfRow][matrix.numOfCol];
        
        for(int r = 0; r < numOfRow; r++) {
            for(int c = 0; c < matrix.numOfCol; c++) {
                product[r][c] = 0d;
                for(int ii = 0; ii < numOfCol; ii++) {
                    product[r][c] += get(r , ii) * matrix.get(ii , c);
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
            return new IdentityMatrix(numOfCol);
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
        
        if(numOfCol == 0)
            return 0d;
        if(numOfCol == 1)
            return get(0 , 0);
        if(numOfCol == 2)
            return get(0 , 0) * get(1 , 1) - get(1 , 0) * get(0 , 1);
        
        double det = 0;
        
        for(int i = 0; i < numOfCol; i++) {
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
        Double[][] matrix = new Double[numOfCol][numOfRow];
        
        for(int c = 0; c < numOfCol; c++) {
            for(int r = 0; r < numOfRow; r++) {
                matrix[c][r] = get(r , c);
            }
        }
        
        return new Matrix(matrix);
    }
    
    @Override
    public Matrix adj(int row , int col) {
        Double[][] matrix = new Double[numOfRow - 1][numOfCol - 1];
        
        for(int c = 0; c < numOfCol; c++) {
            for(int r = 0; r < numOfRow; r++) {
                if(c != row && r != col)
                    matrix[r - (r > col ? 1 : 0)][c - (c > row ? 1 : 0)] = get(r , c);
            }
        }
        
        return new Matrix(matrix);
    }
    
    @Override
    public Matrix adj() {
        Double[][] adj = new Double[numOfRow][numOfCol];
        Matrix m = this.transpose();
        
        for(int c = 0; c < numOfCol; c++) {
            for(int r = 0; r < numOfRow; r++) {
                adj[r][c] = Math.pow(-1 , c + r) * m.adj(r , c).det();
            }
        }
        
        return new Matrix(adj);
    }
    
    @Override
    public Matrix invert() {
        return adj().multiply(1 / det());
    }
    
    public Matrix rref() {
        Double[][] m = new Double[numOfRow][numOfCol];
        
        //Make columns cumulative
        for(int i = numOfRow - 1; i >= 0; i--) {
            for(int j = 0; j < numOfCol; j++) {
                double n = get(i , j);
                if(i < numOfRow - 1) {
                    n = m[i + 1][j] + n;
                }
                m[i][j] = n;
            }
        }
        
        //Go down matrix
        double pivotValue;
        
        for(int i = 0; i < Math.min(numOfRow - 1 , numOfCol); i++) {
            if((pivotValue = m[i][i]) == 0)
                continue;
            for(int j = i + 1; j < numOfRow; j++) {
                double c = -m[j][i] / pivotValue;
                for(int k = 0; k < numOfCol; k++)
                    m[j][k] = m[i][k] * c + m[j][k];
            }
        }
        
        //Set diagonal to one
        for(int i = 0; i < Math.min(numOfRow , numOfCol); i++) {
            double diag = m[i][i];
            if(diag == 0)
                continue;
            for(int j = 0; j < numOfCol; j++)
                m[i][j] = m[i][j] / diag;
        }
        
        //Go up matrix
        double[] row = new double[numOfCol];
        for(int i = numOfRow - 2; i >= 0; i--) {
            for(int j = 0; j < numOfCol; j++)
                row[j] = m[i][j];
            for(int j = 0; j < numOfCol; j++)
                for(int k = 1; i + k < numOfRow; k++)
                    if(i + k < numOfCol)
                        m[i][j] = m[i][j] - m[i + k][j] * row[i + k];
        }
        
        Matrix matrix = new Matrix(m , true , false);
        matrix.rank = 0;
        
        for(int i = 0; i < numOfRow; i++) {
            for(int j = 0; j < numOfCol; j++) {
                if(m[i][j] != 0) {
                    matrix.rank++;
                    break;
                }
            }
        }
        
        return matrix;
    }
    
    /**
     * Algorithm:<br>
     * <a href=
     * "https://en.wikipedia.org/wiki/QR_decomposition#Computing_the_QR_decomposition">Computing
     * QR Decomposition</a>
     * 
     */
    public Matrix[] getQR() {
        if(isSquare()) {
            Double[][] Q = new Double[numOfRow][numOfCol] , R = new Double[numOfRow][numOfCol];
            Vector[] e = new Vector[numOfCol] , a = new Vector[numOfRow];
            
            // Find u vectors
            for(int c = 0; c < numOfCol; c++) {
                e[c] = new Vector(new double[numOfRow]);
                a[c] = new Vector(new double[numOfRow]);
                
                for(int r = 0; r < numOfRow; r++) {
                    double val = get(r , c);
                    e[c].value[r] = val;
                    a[c].value[r] = val;
                }
                
                for(int j = 0; j < c; j++) {
                    Vector proj = a[c].proj(e[j]);
                    proj.mult(-1);
                    e[c].add(proj);
                }
            }
            
            // Make e all unit vectors
            for(int c = 0; c < numOfCol; c++)
                for(int r = 0; r < numOfRow; r++)
                    e[c].setMag(1);
                
            for(int c = 0; c < numOfCol; c++) {
                for(int r = 0; r < numOfRow; r++) {
                    Q[r][c] = e[c].value[r];
                    if(c >= r) {
                        System.out.println(r + " " + c);
                        R[r][c] = e[r].dot(a[c]);
                    }
                    else
                        R[r][c] = 0d;
                }
            }
            
            return new Matrix[] {new Matrix(Q) , new Matrix(R)};
        }
        else {
            return null;
        }
    }
    
    @Override
    public String toString() {
        if(numOfRow == 0 || numOfCol == 0) {
            return "[ ]";
        }
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        for(int j = 0; j < numOfRow; j++) {
            if(j != 0)
                builder.append(" ");
            builder.append("[");
            for(int i = 0; i < numOfCol; i++) {
                builder.append(get(j , i));
                if(i != numOfCol - 1)
                    builder.append(", ");
            }
            builder.append("]");
            if(j != numOfRow - 1)
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
                for(int j = 0; j < size; j++)
                    if(i == j)
                        matrix[i][j] = 1d;
                    else
                        matrix[i][j] = 0d;
            return matrix;
        }
    }
}
