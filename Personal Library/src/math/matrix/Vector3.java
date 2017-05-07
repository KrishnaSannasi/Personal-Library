package math.matrix;

public class Vector3 extends Vector {
    public double x , y , z;
    
    public Vector3() {
        this(0 , 0);
    }
    
    public Vector3(double x , double y) {
        this.x = x;
        this.y = y;
    }
    
    public Vector3(Vector3 v) {
        super(v.x , v.y , v.z);
    }
    
    private void updateUp() {
        value[0] = x;
        value[1] = y;
        value[2] = z;
    }
    
    private void updateDown() {
        x = value[0];
        y = value[1];
        z = value[2];
    }
    
    @Override
    public Vector3 add(Vector v) {
        updateUp();
        super.add(v);
        updateDown();
        
        return this;
    }
    
    @Override
    public Vector3 sub(Vector v) {
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
    public double dot(Vector v) {
        updateUp();
        double dot = super.dot(v);
        updateDown();
        
        return dot;
    }
}
