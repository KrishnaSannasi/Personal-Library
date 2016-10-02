package math.matrix;

import java.util.Arrays;

abstract class AbstractMatrix<T extends Number , U extends AbstractMatrix<T , U>> {
    private final T[][] matrix;
    
    public final int    width , height;
    private boolean     immutable;
    
    public AbstractMatrix(T[][] matrix , boolean isImmutable) {
        this.matrix = Arrays.copyOf(matrix , matrix.length);
        for(int i = 0; i < matrix.length; i++) {
            this.matrix[i] = Arrays.copyOf(matrix[i] , matrix[i].length);
            if(i > 0 && matrix[i].length != matrix[i - 1].length) {
                throw new IllegalArgumentException();
            }
        }
        height = matrix.length;
        width = height == 0 ? 0 : matrix[0].length;
        this.immutable = isImmutable;
    }
    
    public AbstractMatrix(AbstractMatrix<T , U> m) {
        this(m.matrix , false);
    }
    
    public abstract U add(U matrix);
    
    public abstract U subtract(U matrix);
    
    public abstract U multiply(U matrix);
    
    public abstract U multiply(T scalar);
    
    public abstract U pow(int pow);
    
    public abstract U negate();
    
    public abstract U transpose();
    
    public abstract T det();
    
    public abstract U adj(int row , int col);
    
    public abstract U adj();
    
    public abstract U invert();
    
    public final boolean isSquare() {
        return width == height;
    }
    
    public final boolean equalDim(AbstractMatrix<T , U> other) {
        return width == other.width && height == other.height;
    }
    
    public final void setImmutable() {
        immutable = true;
    }
    
    public final T get(int row , int col) {
        return matrix[row][col];
    }
    
    public final T set(int row , int col , T val) {
        T old = get(row , col);
        if(!immutable)
            matrix[row][col] = val;
        return old;
    }
    
    public abstract String toString();
}
