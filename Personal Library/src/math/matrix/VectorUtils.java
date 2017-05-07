package math.matrix;

import java.util.Random;

public class VectorUtils {
    private static final Random RANDOM = new Random();
    private static Quaternion   v      = new Quaternion() , trans = new Quaternion();
    
    public static final Vector rotate(double x , double y , double z , double ax , double ay , double az , double angle) {
        v.a = 0;
        v.b = x;
        v.c = y;
        v.d = z;
        
        double hangle = angle / 2;
        double cos = Math.cos(hangle);
        double sin = Math.sin(hangle);
        
        trans.a = cos;
        trans.b = sin * ax;
        trans.c = sin * ay;
        trans.d = sin * az;
        
        Quaternion q = v.conjugation(trans.normalize());
        
        return new Vector(q.b , q.c , q.d);
    }
    
    public static final Quaternion toQuaternion(Vector v) {
        if(v.getDim() == 3)
            return new Quaternion(0 , v.value[0] , v.value[1] , v.value[2]);
        else
            throw new IllegalArgumentException("only 3-d vectors can be converted");
    }
    
    public static Vector cross(Vector... vs) {
        if(vs.length == 0)
            throw new IndexOutOfBoundsException();
        double[] point = new double[vs.length + 1];
        for(Vector v: vs) {
            if(v.value.length != point.length)
                throw new IndexOutOfBoundsException();
        }
        
        double[][] mat = new double[vs.length][vs.length];
        double[][][] temps = new double[vs.length][][];
        
        for(int i = 0; i < temps.length; i++)
            temps[i] = new double[i][i];
        
        int mj;
        int sign = 1;
        
        for(int i = 0; i < point.length; i++) {
            for(int j = 0; j < vs.length; j++) {
                mj = 0;
                for(int k = 0; k < point.length; k++) {
                    if(i != k)
                        mat[j][mj++] = vs[j].value[k];
                }
            }
            point[i] = sign * det(mat , temps , mat.length);
            sign *= -1;
        }
        
        return new Vector(point);
    }
    
    private static void getCofactor(double[][] mat , double[][] temp , int p , int q , int n) {
        int i = 0 , j = 0;
        
        for(int row = 0; row < n; row++) {
            for(int col = 0; col < n; col++) {
                if(row != p && col != q) {
                    temp[i][j++] = mat[row][col];
                    
                    if(j == n - 1) {
                        j = 0;
                        i++;
                    }
                }
            }
        }
    }
    
    private static double det(double[][] mat , double[][][] temps , int n) {
        double det = 0;
        
        if(n == 1)
            return mat[0][0];
        
        int sign = 1;
        
        for(int f = 0; f < n; f++) {
            getCofactor(mat , temps[n - 1] , 0 , f , n);
            det += sign * mat[0][f] * det(temps[n - 1] , temps , n - 1);
            
            sign = -sign;
        }
        
        return det;
    }
    
    public static Vector createRand(int dim) {
        return createRand(RANDOM , dim);
    }
    
    public static final double getAngle(Vector a , Vector b) {
        return Math.acos(a.dot(b) / a.mag() / b.mag());
    }
    
    /**
     * Create random unit vector
     */
    public static Vector createRand(final Random RAND , int dim) {
        final double TAU = 2 * Math.PI;
        
        if(dim < 1)
            throw new IllegalArgumentException();
        
        switch (dim) {
            case 1: {
                return new Vector(RAND.nextDouble() < .5 ? 1 : -1);
            }
            case 2: {
                double theta = RAND.nextDouble() * TAU;
                return new Vector2(Math.cos(theta) , Math.sin(theta));
            }
            case 3: {
                double theta = RAND.nextDouble() * TAU;
                double phi = RAND.nextDouble() * Math.PI;
                return new Vector3(Math.cos(theta) * Math.sin(phi) , Math.sin(theta) * Math.sin(phi) , Math.cos(phi));
            }
            case 4: {
                double theta1 = RAND.nextDouble() * TAU;
                double theta2 = RAND.nextDouble() * TAU;
                double phi = RAND.nextDouble() * Math.PI;
                return new Vector(Math.cos(theta1) * Math.sin(phi) , Math.sin(theta1) * Math.sin(phi) , Math.cos(theta2) * Math.cos(phi) , Math.sin(theta2) * Math.cos(phi));
            }
            default: {
                double[] point , hsphere;
                point = new double[dim];
                hsphere = new double[dim];
                
                //Start with n-spherical coordinates for uniform randomness
                hsphere[0] = 1;
                for(int i = 1; i < dim; i++)
                    if(i == dim - 1)
                        hsphere[i] = RAND.nextDouble() * TAU;
                    else
                        hsphere[i] = RAND.nextDouble() * Math.PI;
                System.out.println(hsphere[1]);
                for(int i = 0; i < dim; i++) {
                    double p = hsphere[0];
                    int j;
                    for(j = 1; j <= i; j++) {
                        p *= Math.sin(hsphere[j]);
                    }
                    if(j < dim) {
                        p *= Math.cos(hsphere[j]);
                    }
                    point[i] = p;
                }
                
                return new Vector(point);
            }
        }
    }
}
