package math.matrix;

public class Vector2 extends Vector {
    public double x , y;
    
    public Vector2() {
        this(0 , 0);
    }
    
    public Vector2(double x , double y) {
        super(x , y);
        this.x = x;
        this.y = y;
    }
    
    public Vector2(Vector2 v) {
        this(v.x , v.y);
    }
    
    private void updateUp() {
        value[0] = x;
        value[1] = y;
    }
    
    private void updateDown() {
        x = value[0];
        y = value[1];
    }
    
    @Override
    public Vector2 add(Vector v) {
        updateUp();
        super.add(v);
        updateDown();
        
        return this;
    }
    
    @Override
    public Vector2 sub(Vector v) {
        updateUp();
        super.sub(v);
        updateDown();
        
        return this;
    }
    
    @Override
    public Vector mult(double d) {
        updateUp();
        super.mult(d);
        updateDown();
        
        return this;
    }
    
    @Override
    public Vector div(double d) {
        updateUp();
        super.div(d);
        updateDown();
        
        return this;
    }
    
    @Override
    public Vector2 setMag(double mag) {
        updateUp();
        super.setMag(mag);
        updateDown();
        
        return this;
    }
    
    @Override
    public double dot(Vector v) {
        updateUp();
        double dot = super.dot(v);
        updateDown();
        
        return dot;
    }
}
