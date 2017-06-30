package math.matrix;

public class Vector {
    public static double  treshold = 1e-12;
    public final double[] value;
    
    public Vector() {
        this(0 , 0 , 0);
    }
    
    public Vector(double... value) {
        this.value = value;
    }
    
    public Vector(Vector v) {
        this.value = new double[v.value.length];
        for(int i = 0; i < value.length; i++)
            value[i] = v.value[i];
    }
    
    @Deprecated
    public double get(int i) {
        if(i < value.length)
            return value[i];
        else
            return 0;
    }
    
    @Deprecated
    public void set(int i , double val) {
        if(i < value.length)
            value[i] = val;
        else
            throw new IndexOutOfBoundsException();
    }
    
    public int getDim() {
        return value.length;
    }
    
    public Vector add(Vector v) {
        if(v.value.length == value.length) {
            for(int i = 0; i < value.length; i++)
                value[i] += v.value[i];
        }
        else {
            throw new IndexOutOfBoundsException();
        }
        return this;
    }
    
    public Vector sub(Vector v) {
        if(v.value.length == value.length) {
            for(int i = 0; i < value.length; i++)
                value[i] -= v.value[i];
        }
        else {
            throw new IndexOutOfBoundsException();
        }
        return this;
    }
    
    public Vector mult(double d) {
        for(int i = 0; i < value.length; i++)
            value[i] *= d;
        return this;
    }
    
    public Vector div(double d) {
        for(int i = 0; i < value.length; i++)
            value[i] /= d;
        return this;
    }
    
    public double dot(Vector v) {
        double dot = 0;
        for(int i = Math.min(value.length , v.value.length) - 1; i >= 0; i--)
            dot += value[i] * v.value[i];
        return dot;
    }
    
    public double magSq() {
        return dot(this);
    }
    
    public double mag() {
        return Math.sqrt(magSq());
    }
    
    public Vector setMag(double mag) {
        return normalize().mult(mag);
    }
    
    public Vector normalize() {
        double mag = mag();
        if(mag == 0)
            return this;
        else
            return div(mag());
    }
    
    @Override
    public boolean equals(Object obj) {
        Vector v;
        if(obj instanceof Vector)
            v = (Vector) obj;
        else
            return false;
        
        int min = Math.min(value.length , v.value.length);
        int max = Math.max(value.length , v.value.length);
        
        for(int i = 0; i < min; i++)
            if(value[i] != v.value[i])
                return false;
            
        if(max > value.length) {
            for(int i = min; i < max; i++)
                if(v.value[i] != 0)
                    return false;
        }
        else {
            for(int i = min; i < max; i++)
                if(value[i] != 0)
                    return false;
        }
        
        return true;
    }
    
    /**
     * Project onto Vector v <br>
     * proj_v(this)
     */
    public Vector proj(Vector v) {
        Vector proj = new Vector(v);
        proj.mult(dot(v) / v.dot(v));
        return proj;
    }
    
    public double getAngle(Vector v) {
        double m1 , m2;
        
        m1 = mag();
        m2 = v.mag();
        
        if(m1 == 0)
            throw new IllegalArgumentException("zero vector cannot be used to get angles");
        
        if(m2 == 0)
            throw new IllegalArgumentException("Cannot check angle against zero vector");
        
        double angle = (dot(v) / m1 / m2) % 1;
        
        return Math.acos(angle);
    }
    
    public double distSq(Vector v) {
        mult(-1).add(v);
        double dist = magSq();
        mult(-1).add(v);
        
        return dist;
    }
    
    public double dist(Vector v) {
        mult(-1).add(v);
        double dist = mag();
        mult(-1).add(v);
        
        return dist;
    }
    
    @Override
    public Object clone() {
        return new Vector(this);
    }
    
    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        
        for(double v: value)
            buffer.append(", ").append(Math.abs(v) < treshold ? 0 : v);
        
        return "<" + buffer.toString().substring(2) + ">";
    }
}
