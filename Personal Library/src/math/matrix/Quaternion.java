package math.matrix;

public class Quaternion extends Vector {
    public double  a , b , c , d;
    private Vector v;
    
    public Quaternion() {
        this(0 , 0 , 0 , 0);
    }
    
    public Quaternion(Quaternion q) {
        this(q.a , q.b , q.c , q.d);
    }
    
    public Quaternion(double a , double b , double c , double d) {
        super(a , b , c , d);
        v = new Vector(b , c , d);
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }
    
    private void updateUp() {
        value[0] = a;
        value[1] = b;
        value[2] = c;
        value[3] = d;
    }
    
    private void updateDown() {
        a = value[0];
        b = value[1];
        c = value[2];
        d = value[3];
    }
    
    @Override
    public Quaternion add(Vector v) {
        updateUp();
        super.add(v);
        updateDown();
        
        return this;
    }
    
    @Override
    public Quaternion sub(Vector v) {
        updateUp();
        super.sub(v);
        updateDown();
        
        return this;
    }
    
    @Override
    public Quaternion mult(double val) {
        updateUp();
        super.mult(val);
        updateDown();
        
        return this;
    }
    
    @Override
    public Quaternion div(double val) {
        updateUp();
        super.div(val);
        updateDown();
        
        return this;
    }
    
    @Override
    public Quaternion normalize() {
        updateUp();
        super.normalize();
        updateDown();
        
        return this;
    }
    
    @Deprecated
    @Override
    public double dot(Vector v) {
        return super.dot(v);
    }
    
    public Quaternion mult(Quaternion q) {
        double a1 = a , b1 = b , c1 = c , d1 = d;
        double a2 = q.a , b2 = q.b , c2 = q.c , d2 = q.d;
        
        a = a1 * a2 - b1 * b2 - c1 * c2 - d1 * d2;
        v.value[0] = b = a1 * b2 + b1 * a2 + c1 * d2 - d1 * c2;
        v.value[1] = c = a1 * c2 - b1 * d2 + c1 * a2 + d1 * b2;
        v.value[2] = d = a1 * d2 + b1 * c2 - c1 * b2 + d1 * a2;
        
        updateUp();
        
        return this;
    }
    
    public Quaternion getConjugate() {
        return new Quaternion(a , -b , -c , -d);
    }
    
    public Quaternion conjugation(Quaternion q) {
        return new Quaternion(q).mult(this).mult(q.getConjugate());
    }
    
    public Quaternion getInvert() {
        return getConjugate().div(magSq());
    }
}
