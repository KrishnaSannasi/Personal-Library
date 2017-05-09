package io;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.transform.Rotate;

public class CanvasFXUtils {
    private final static Rotate r = new Rotate(0 , 0 , 0);
    
    public static void rotate(GraphicsContext g , double angle , double px , double py) {
        r.setAngle(Math.toDegrees(angle));
        r.setPivotX(px);
        r.setPivotY(py);
        
        g.setTransform(r.getMxx() , r.getMyx() , r.getMxy() , r.getMyy() , r.getTx() , r.getTy());
    }
    
}
