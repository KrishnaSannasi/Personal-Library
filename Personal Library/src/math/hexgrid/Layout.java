package math.hexgrid;

import javafx.geometry.Point2D;

import static java.lang.Math.PI;
import static java.lang.Math.sin;
import static java.lang.Math.cos;

public class Layout {
    public Orientation   orientation;
    public final Point2D size;
    public final Point2D origin;
    
    public Layout(Orientation orientation , Point2D size , Point2D origin) {
        this.orientation = orientation;
        this.size = size;
        this.origin = origin;
    }
    
    public Point2D hex_to_pixel(HexPos h) {
        Orientation M = orientation;
        double x = (M.f0 * h.q + M.f1 * h.r) * size.getX();
        double y = (M.f2 * h.q + M.f3 * h.r) * size.getY();
        return new Point2D(x + origin.getX() , y + origin.getY());
    }
    
    public HexPos pixel_to_hex(Point2D p) {
        Orientation M = orientation;
        Point2D pt = new Point2D((p.getX() - origin.getX()) / size.getX() , (p.getY() - origin.getY()) / size.getY());
        double q = M.b0 * pt.getX() + M.b1 * pt.getY();
        double r = M.b2 * pt.getX() + M.b3 * pt.getY();
        return new HexPos(q , r);
    }
    
    Point2D hex_corner_offset(int corner) {
        double angle = 2.0 * PI * (orientation.start_angle + corner) / 6;
        return new Point2D(size.getX() * cos(angle) , size.getY() * sin(angle));
    }
    
    Point2D[] polygon_corners(Layout layout , HexPos h) {
        Point2D[] corners = new Point2D[6];
        Point2D center = hex_to_pixel(h);
        for(int i = 0; i < 6; i++) {
            Point2D offset = hex_corner_offset(i);
            corners[i] = new Point2D(center.getX() + offset.getX() , center.getY() + offset.getY());
        }
        return corners;
    }
}
