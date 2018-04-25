package math.hexgrid;

import static java.lang.Math.sqrt;

public enum Orientation {
    POINTY(sqrt(3) , sqrt(3) / 2 , 0 , 1.5 , 1 / sqrt(3) , -1.0 / 3 , 0 , 2.0 / 3 , .5) ,
    FLAT(1.5 , 0 , sqrt(3) / 2 , sqrt(3) , 2.0 / 3 , 0 , -1.0 / 3 , 1 / sqrt(3) , 0);

    public final double f0 , f1 , f2 , f3;
    public final double b0 , b1 , b2 , b3;
    public final double start_angle;
    
    private Orientation(double f0 , double f1 , double f2 , double f3 , double b0 , double b1 , double b2 , double b3 , double start_angle) {
        this.f0 = f0;
        this.f1 = f1;
        this.f2 = f2;
        this.f3 = f3;
        this.b0 = b0;
        this.b1 = b1;
        this.b2 = b2;
        this.b3 = b3;
        this.start_angle = start_angle;
    }
}
