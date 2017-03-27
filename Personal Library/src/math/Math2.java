package math;

import math.matrix.Vector;

public final class Math2 {
    public static final double TAU = 2 * Math.PI;
    
    public static final double min(double... ds) {
        if(ds.length == 0)
            throw new UnsupportedOperationException();
        double m = ds[0];
        for(double d: ds)
            if(d < m)
                m = d;
        return m;
    }
    
    public static final double max(double... ds) {
        if(ds.length == 0)
            throw new UnsupportedOperationException();
        double m = ds[0];
        for(double d: ds)
            if(d > m)
                m = d;
        return m;
    }
    
    public static final int min(int... ds) {
        if(ds.length == 0)
            throw new UnsupportedOperationException();
        int m = ds[0];
        for(int d: ds)
            if(d < m)
                m = d;
        return m;
    }
    
    public static final int max(int... ds) {
        if(ds.length == 0)
            throw new UnsupportedOperationException();
        int m = ds[0];
        for(int d: ds)
            if(d > m)
                m = d;
        return m;
    }
    
    public static final double bezier(double t , double... x) {
        if(x.length < 2)
            throw new IllegalArgumentException();
        
        double[] out = new double[x.length];
        for(int i = 0; i < x.length; i++)
            out[i] = x[i];
        
        for(int n = x.length - 1; n > 0; n--)
            for(int i = 0; i < n; i++)
                out[i] = t * (out[i + 1] - out[i]) + out[i];
            
        return out[0];
    }
    
    public static final Vector bezier(double t , Vector... x) {
        if(x.length < 2)
            throw new IllegalArgumentException();
        
        int dim = x[0].getDim();
        
        for(Vector v: x)
            if(v.getDim() != dim)
                throw new IllegalArgumentException();
            
        double[] point = new double[dim] , cx = new double[x.length];
        
        for(int i = 0; i < dim; i++) {
            for(int j = 0; j < x.length; j++)
                cx[j] = x[j].get(i);
            point[i] = bezier(t , cx);
        }
        
        return new Vector(point);
    }
}
