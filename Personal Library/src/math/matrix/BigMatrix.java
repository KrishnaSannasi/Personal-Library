package math.matrix;

import java.math.MathContext;

import math.BigDecimal_INF;

public class BigMatrix extends AbstractMatrix<BigDecimal_INF , BigMatrix> {
    private MathContext mc;
    private int         rank;
    
    public BigMatrix(BigDecimal_INF[][] matrix) {
        this(matrix , false);
    }
    
    public BigMatrix(int width , int height) {
        this(new BigDecimal_INF[width][height] , false , false);
    }
    
    public BigMatrix(BigDecimal_INF[][] matrix , boolean isImmutable) {
        this(matrix , isImmutable , true);
    }
    
    private BigMatrix(BigDecimal_INF[][] matrix , boolean isImmutable , boolean findRank) {
        super(matrix , isImmutable);
        mc = MathContext.DECIMAL128;
        if(findRank)
            findRank();
    }
    
    public int getRank() {
        return rank;
    }
    
    private int findRank() {
        if(isSquare() && !BigDecimal_INF.ZERO.equals(det()))
            return rank = numOfRow;
        else if(isSquare() && numOfRow == 1)
            return rank = 0;
        else {
            int size = Math.min(numOfCol , numOfRow - 1);
            rank = sub(0 , 0 , size , size).rank;
            
            if(rank == 0) {
                for(int i = 0; i < numOfRow; i++) {
                    for(int j = 0; j < numOfCol; j++)
                        if(!BigDecimal_INF.ZERO.equals(get(i , j))) {
                            rank = 1;
                        }
                }
            }
            
            return rank;
        }
    }
    
    public BigMatrix sub(int ix , int iy , int nor , int noc) {
        BigMatrix m = new BigMatrix(nor , noc);
        for(int x = 0; x < nor; x++) {
            for(int y = 0; y < nor; y++) {
                m.set(x , y , get(ix + x , iy + y));
            }
        }
        m.findRank();
        m.setImmutable();
        return m;
    }
    
    public void setMathContext(MathContext mc) {
        if(mc != null)
            this.mc = mc;
    }
    
    @Override
    public BigMatrix add(BigMatrix matrix) {
        if(matrix.numOfCol != numOfCol || matrix.numOfRow != numOfRow)
            throw new IllegalArgumentException();
        
        BigDecimal_INF[][] sum = new BigDecimal_INF[numOfCol][numOfRow];
        
        for(int i = 0; i < numOfCol; i++) {
            for(int j = 0; j < numOfRow; j++) {
                sum[i][j] = get(i , j).add(matrix.get(i , j) , mc);
            }
        }
        
        return new BigMatrix(sum);
    }
    
    @Override
    public BigMatrix subtract(BigMatrix matrix) {
        if(matrix.numOfCol != numOfCol || matrix.numOfRow != numOfRow)
            throw new IllegalArgumentException();
        
        BigDecimal_INF[][] sum = new BigDecimal_INF[numOfCol][numOfRow];
        
        for(int i = 0; i < numOfCol; i++) {
            for(int j = 0; j < numOfRow; j++) {
                sum[i][j] = get(i , j).subtract(matrix.get(i , j) , mc);
            }
        }
        
        return new BigMatrix(sum);
    }
    
    @Override
    public BigMatrix multiply(BigDecimal_INF scalar) {
        BigDecimal_INF[][] product = new BigDecimal_INF[numOfCol][numOfRow];
        
        for(int i = 0; i < numOfCol; i++) {
            for(int j = 0; j < numOfRow; j++) {
                product[i][j] = get(i , j).multiply(scalar , mc);
            }
        }
        
        return new BigMatrix(product);
    }
    
    @Override
    public BigMatrix multiply(BigMatrix matrix) {
        if(matrix.numOfRow != numOfCol) {
            throw new UnsupportedOperationException();
        }
        
        BigDecimal_INF[][] product = new BigDecimal_INF[numOfRow][matrix.numOfCol];
        
        for(int i = 0; i < numOfRow; i++) {
            for(int j = 0; j < matrix.numOfCol; j++) {
                product[i][j] = BigDecimal_INF.ZERO;
                for(int ii = 0; ii < numOfCol; ii++) {
                    product[i][j] = get(i , ii).multiply(matrix.get(ii , j) , mc).add(product[i][j] , mc);
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
            return new IdentityMatrix(numOfCol);
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
    
    public BigMatrix pow(double pow) {
        return null;
    }
    
    @Override
    public BigDecimal_INF det() {
        if(!isSquare()) {
            throw new UnsupportedOperationException();
        }
        
        if(numOfCol == 0)
            return BigDecimal_INF.ZERO;
        if(numOfCol == 1)
            return get(0 , 0);
        if(numOfCol == 2)
            return get(0 , 0).multiply(get(1 , 1) , mc).subtract(get(1 , 0).multiply(get(0 , 1) , mc) , mc);
        
        BigDecimal_INF det = BigDecimal_INF.ZERO;
        
        for(int i = 0; i < numOfCol; i++) {
            BigDecimal_INF num = get(0 , i).multiply(adj(0 , i).det());
            if(i % 2 == 0)
                det = det.add(num , mc);
            else
                det = det.subtract(num , mc);
        }
        
        return det;
    }
    
    @Override
    public BigMatrix negate() {
        return multiply(BigDecimal_INF.NEG_ONE);
    }
    
    @Override
    public BigMatrix transpose() {
        BigDecimal_INF[][] matrix = new BigDecimal_INF[numOfCol][numOfRow];
        
        for(int i = 0; i < numOfRow; i++) {
            for(int j = 0; j < numOfCol; j++) {
                matrix[j][i] = get(i , j);
            }
        }
        
        return new BigMatrix(matrix);
    }
    
    @Override
    public BigMatrix adj(int row , int col) {
        BigDecimal_INF[][] matrix = new BigDecimal_INF[numOfCol - 1][numOfRow - 1];
        
        for(int i = 0; i < numOfCol; i++) {
            for(int j = 0; j < numOfRow; j++) {
                if(i != row && j != col)
                    matrix[i - (i > row ? 1 : 0)][j - (j > col ? 1 : 0)] = get(i , j);
            }
        }
        
        return new BigMatrix(matrix);
    }
    
    @Override
    public BigMatrix adj() {
        BigDecimal_INF[][] adj = new BigDecimal_INF[numOfCol][numOfRow];
        BigMatrix m = this.transpose();
        
        for(int i = 0; i < numOfCol; i++) {
            for(int j = 0; j < numOfRow; j++) {
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
    
    public BigMatrix rref() {
        BigMatrix m = new BigMatrix(numOfRow , numOfCol);
        
        //Make columns cumulative
        for(int i = numOfRow - 1; i >= 0; i--) {
            for(int j = 0; j < numOfCol; j++) {
                BigDecimal_INF n = get(i , j);
                if(i < numOfRow - 1) {
                    n = m.get(i + 1 , j).add(n);
                }
                m.set(i , j , n);
            }
        }
        
        //Go down matrix
        BigDecimal_INF pivotValue;
        
        for(int i = 0; i < numOfRow - 1; i++) {
            if((pivotValue = m.get(i , i)).equals(BigDecimal_INF.ZERO))
                continue;
            for(int j = i + 1; j < numOfRow; j++) {
                BigDecimal_INF c = m.get(j , i).divide(pivotValue , mc).negate();
                for(int k = 0; k < numOfCol; k++)
                    m.set(j , k , m.get(i , k).multiply(c , mc).add(m.get(j , k) , mc));
            }
        }
        
        //Set diagonal to one
        for(int i = 0; i < numOfRow; i++) {
            BigDecimal_INF diag = m.get(i , i);
            if(diag.equals(BigDecimal_INF.ZERO))
                continue;
            for(int j = 0; j < numOfCol; j++)
                m.set(i , j , m.get(i , j).divide(diag , mc));
        }
        
        //Go up matrix
        BigDecimal_INF[] row = new BigDecimal_INF[numOfCol];
        for(int i = numOfRow - 2; i >= 0; i--) {
            for(int j = 0; j < numOfCol; j++)
                row[j] = m.get(i , j);
            for(int j = 0; j < numOfCol; j++)
                for(int k = 1; i + k < numOfRow; k++)
                    m.set(i , j , m.get(i , j).subtract(m.get(i + k , j).multiply(row[i + k] , mc) , mc));
        }
        
        return m;
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
                if(BigDecimal_INF.ZERO.equals(get(j , i)))
                    builder.append("0");
                else
                    builder.append(get(j , i) == null ? "null" : get(j , i).setScale(mc.getPrecision() , mc.getRoundingMode()));
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
    
    public static BigMatrix createIdentityMatrix(int size) {
        return new IdentityMatrix(size);
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
