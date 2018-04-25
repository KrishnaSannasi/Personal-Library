package math.hexgrid;

import java.util.ArrayList;

public class HexUtil {
    public static final double lerp(double a , double b , double t) {
        return a + (b - a) * t;
    }
    
    HexPos hex_lerp(HexPos a , HexPos b , double t) {
        return new HexPos(lerp(a.q , b.q , t) , lerp(a.r , b.r , t));
    }
    
    HexPos[] HexPos_linedraw(HexPos a , HexPos b) {
        if(a.equals(b))
            return new HexPos[] {new HexPos(a)};
        
        int N = a.dist(b);
        
        ArrayList<HexPos> results = new ArrayList<>();
        double step = 1.0 / N;
        
        for(int i = 0; i <= N; i++)
            results.add(new HexPos(hex_lerp(a , b , step * i)));
        
        return results.toArray(new HexPos[results.size()]);
    }
}
