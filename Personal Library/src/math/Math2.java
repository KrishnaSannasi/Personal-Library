package math;

import math.matrix.Vector;

public final class Math2 {
    private static final double STD_CURVE_COEFF[] = {0.0000014232966572456734266807 , 0.79845014549212401191185 , -0.0070208686008880734030572 , -0.10550649458477175765778 , -0.050170092689955576171875 , 0.069361597650519055175781 , -0.027250505403858675286851 , 0.00533072557369898852571 , -0.00053348158351417130394325 , 0.00002185752235202593136528};
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
        
        double val = 0 , x1 = 1 / x;
        boolean isneg = x < 0;
        
        if(isneg)
            x *= -1;
        
        for(int i = 0; i < STD_CURVE_COEFF.length; i++)
            val += STD_CURVE_COEFF[i] * (x1 *= x);
        
        if(isneg)
            return (1 - val) / 2;
        else
            return (val + 1) / 2;
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
