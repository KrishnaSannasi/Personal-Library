package math;

import math.matrix.Vector;

public final class Math2 {
    private static final double STD_CURVE_COEFF[] = {0.00489149039998 , -0.00233559754606 , -0.00524015425347 , -0.00319480364672 , -0.00124132244791};
    private static final double STD_CURVE_PERIOD  = 0.688648400484;
    public static final double  TAU               = 2 * Math.PI;
    
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
    
    public static final double stdCurve(double x0 , double x1) {
        return stdCurve(x1) - stdCurve(x0);
    }
    
    public static final double stdCurve(double x) {
        if(x < -5)
            return 0;
        if(x > 5)
            return 1;
        if(x == 0)
            return .5;
        double std = Math.exp(-1.7 * x) + 1;
        std = 1 / std;
        for(int i = 1; i <= STD_CURVE_COEFF.length; i++) {
            std += STD_CURVE_COEFF[i - 1] * Math.sin(i * STD_CURVE_PERIOD * x);
        }
        
        return std;
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
                cx[j] = x[j].value[i];
            point[i] = bezier(t , cx);
        }
        
        return new Vector(point);
    }
}
