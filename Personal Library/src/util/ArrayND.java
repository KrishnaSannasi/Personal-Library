package util;

public class ArrayND<T> {
    private static final <T> String createFormat(T[] coordinates , int[] aggregateDim , int[] dimensions) {
        final int rank = dimensions.length;
        StringBuffer s = new StringBuffer();
        
        for(int i = 0; i <= coordinates.length; i++) {
            for(int j = rank - 1; j > 0; j--) {
                if(i % aggregateDim[j] == 0) {
                    if(i > 0) {
                        s.deleteCharAt(s.length() - 1);
                        for(int k = rank - j; k < rank; k++)
                            s.append(']');
                        if(i != coordinates.length && j != dimensions[j] - 1)
                            s.append(' ');
                    }
                    if(i != coordinates.length)
                        for(int k = rank - j; k < rank; k++)
                            s.append('[');
                    break;
                }
            }
            if(i != coordinates.length)
                s.append("%s,");
        }
        
        return "[" + s.toString() + "]";
    }
    
    public final int rank;
    private T[]      values;
    private int[]    dimensions , aggregateDim;
    
    private Object[] dispValues;
    
    private String format;
    
    @SuppressWarnings("unchecked")
    public ArrayND(int... dimensions) {
        rank = dimensions.length;
        this.dimensions = new int[rank];
        this.aggregateDim = new int[rank];
        
        for(int i = 0; i < rank; i++)
            this.dimensions[rank - 1 - i] = dimensions[i];
        
        dimensions = this.dimensions;
        
        int size = 1;
        for(int i = 0; i < rank; i++) {
            this.aggregateDim[i] = size;
            size *= dimensions[i];
        }
        
        values = (T[]) new Object[size];
        dispValues = new Object[size];
        for(int i = 0; i < size; i++)
            dispValues[i] = 0d;
        
        format = createFormat(values , aggregateDim , dimensions);
    }
    
    private int convertIndex(int... index) {
        if(index.length != rank)
            throw new IndexOutOfBoundsException();
        int idx = 0 , row = 1;
        for(int i = 0; i < dimensions.length; i++) {
            if(dimensions[i] > index[rank - 1 - i]) {
                idx += row * index[rank - 1 - i];
                row *= dimensions[i];
            }
            else {
                throw new IndexOutOfBoundsException();
            }
        }
        return idx;
    }
    
    public boolean equalDim(ArrayND<?> t) {
        if(t == this)
            return true;
        else if(rank != t.rank)
            return false;
        else {
            for(int i = 0; i < rank; i++)
                if(dimensions[i] != t.dimensions[i])
                    return false;
            return true;
        }
    }
    
    public T get(int... index) {
        return values[convertIndex(index)];
    }
    
    public void set(T value , int... index) {
        int i = convertIndex(index);
        values[i] = value;
        dispValues[i] = value;
    }
    
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof ArrayND) {
            try {
                @SuppressWarnings("unchecked")
                ArrayND<T> t = (ArrayND<T>) obj;
                if(!equalDim(t))
                    return false;
                for(int i = 0; i < values.length; i++)
                    if(!t.values[i].equals(values[i]))
                        return false;
                return true;
            }
            catch (Exception e) {
                return false;
            }
            
        }
        return false;
    }
    
    @Override
    public String toString() {
        return String.format(format , dispValues);
    }
}
