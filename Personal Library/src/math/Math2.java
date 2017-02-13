package math;

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
}
