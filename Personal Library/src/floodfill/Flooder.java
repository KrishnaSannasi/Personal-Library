package floodfill;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.LinkedList;

import floodfill.Tile.State;

public final class Flooder {
    private Rectangle      boundry;
    private BoundryChecker boundryChecker;
    private Tile[][]       map;
    private int            width , height;
    private boolean[][]    returnFillMap;
    private boolean        hasEdges , isEightDirections;
    public boolean         useFailSafe = true;
    
    public Flooder(BoundryChecker boundryChecker , int width , int height) {
        this(boundryChecker , width , height , true);
    }
    
    public Flooder(BoundryChecker boundryChecker , int width , int height , boolean isEightDirections) {
        if(boundryChecker == null)
            throw new IllegalArgumentException("Boundry Checker CANNOT be null");
        
        boundry = new Rectangle(0 , 0 , width , height);
        this.width = width;
        this.height = height;
        map = genNewMap();
        returnFillMap = new boolean[width][height];
        
        this.isEightDirections = isEightDirections;
        this.boundryChecker = boundryChecker;
        reset();
    }
    
    private Tile[][] genNewMap() {
        Tile[][] map = new Tile[width][height];
        
        for(int x = 0; x < width; x++) {
            for(int y = 0; y < height; y++) {
                map[x][y] = new Tile(x , y);
            }
        }
        return map;
    }
    
    /*
     * Places an edge at the given location
     * 
     * @param x - x coordinate
     * @param y - y coordinate
     */
    public void put(int x , int y) {
        if(isInBounds(x , y))
            map[x][y].state = Tile.State.EDGE;
    }
    
    /**
     * calls <code>{@link Flooder#floodTick()}</code>) till there are no more
     * <code>{@link Tile.State#EDGE}</code>
     */
    public void flood() {
        while(floodTick())
            ;
    }
    
    public void flood(int x , int y) {
        try {
            floodRecursive(x , y);
        }
        catch (StackOverflowError e) {
            reset();
            if(useFailSafe) {
                seed(x , y);
                flood();
            }
            else {
                throw new UnsupportedOperationException("Area too large for recursive flood");
            }
        }
    }
    
    public void floodQueue(int x , int y) {
        if(!isInBounds(x , y))
            throw new RuntimeException("Out of bounds!");
        LinkedList<Point> list = new LinkedList<>();
        list.add(new Point(x , y));
        while(!list.isEmpty()) {
            Point p = list.removeFirst();
            
            map[p.x][p.y].state = State.FILLED;
            
            add(list , p , 1 , 0);
            add(list , p , -1 , 0);
            add(list , p , 0 , 1);
            add(list , p , 0 , -1);
            
            if(isEightDirections) {
                add(list , p , 1 , 1);
                add(list , p , 1 , -1);
                add(list , p , -1 , 1);
                add(list , p , -1 , -1);
            }
        }
    }
    
    private void add(LinkedList<Point> l , Point p , int dx , int dy) {
        if(check(map , p.x , p.y , dx , dy)) {
            Point n = new Point(p.x + dx , p.y + dy);
            if(!l.contains(n))
                l.add(n);
        }
    }
    
    public void floodRecursive(int x , int y) {
        if(isInBounds(x , y)) {
            if(map[x][y].state != State.NONE)
                return;
            map[x][y].state = State.FILLED;
        }
        else {
            return;
        }
        
        floodBlockR(map , x , y , 1 , 0);
        floodBlockR(map , x , y , -1 , 0);
        floodBlockR(map , x , y , 0 , 1);
        floodBlockR(map , x , y , 0 , -1);
        
        if(isEightDirections) {
            floodBlockR(map , x , y , 1 , 1);
            floodBlockR(map , x , y , 1 , -1);
            floodBlockR(map , x , y , -1 , 1);
            floodBlockR(map , x , y , -1 , -1);
        }
    }
    
    /**
     * Puts down <code>{@link Tile.State#EDGE}</code> at desired locations
     */
    public void seed(int x , int y) {
        map[x][y].state = State.EDGE;
        hasEdges = true;
    }
    
    /**
     * Sets all tiles in map to a state of <code>{@link Tile.State#NONE}</code>
     */
    public void reset() {
        map = genNewMap();
        returnFillMap = new boolean[width][height];
    }
    
    public boolean hasEdges() {
        return hasEdges;
    }
    
    /**
     * Floods the field by one tick
     */
    public boolean floodTick() {
        boolean hasEdges = false;
        Tile[][] tempMap = genNewMap();
        
        for(int x = boundry.x; x < boundry.getMaxX(); x++) {
            for(int y = boundry.y; y < boundry.getMaxY(); y++) {
                if(boundryChecker.isInBounds(x , y)) {
                    tempMap[x][y].state = map[x][y].state;
                    tempMap[x][y].owner = map[x][y].owner;
                }
            }
        }
        
        for(int x = boundry.x; x < boundry.x + boundry.width; x++) {
            for(int y = boundry.y; y < boundry.y + boundry.height; y++) {
                if(!boundryChecker.isInBounds(x , y))
                    continue;
                if(map[x][y].state == Tile.State.EDGE) {
                    tempMap[x][y].state = Tile.State.FILLED;
                    hasEdges = floodBlock(tempMap , x , y , 1 , 0) || hasEdges;
                    hasEdges = floodBlock(tempMap , x , y , -1 , 0) || hasEdges;
                    hasEdges = floodBlock(tempMap , x , y , 0 , 1) || hasEdges;
                    hasEdges = floodBlock(tempMap , x , y , 0 , -1) || hasEdges;
                    if(isEightDirections) {
                        hasEdges = floodBlock(tempMap , x , y , 1 , 1) || hasEdges;
                        hasEdges = floodBlock(tempMap , x , y , 1 , -1) || hasEdges;
                        hasEdges = floodBlock(tempMap , x , y , -1 , 1) || hasEdges;
                        hasEdges = floodBlock(tempMap , x , y , -1 , -1) || hasEdges;
                    }
                }
            }
        }
        
        for(int x = boundry.x; x < boundry.getMaxX(); x++) {
            for(int y = boundry.y; y < boundry.getMaxY(); y++) {
                if(boundryChecker.isInBounds(x , y))
                    map[x][y] = new Tile(tempMap[x][y]);
            }
        }
        
        return this.hasEdges = hasEdges;
    }
    
    private boolean check(Tile[][] tempMap , int x , int y , int dx , int dy) {
        x += dx;
        y += dy;
        boolean check = boundryChecker.isInBounds(x , y);
        check = check && isInBounds(x , y);
        check = check && map[x][y].state == Tile.State.NONE;
        if(check) {
            if(boundryChecker.is1Parameter)
                check = !boundryChecker.isBoundaryType(map[x][y]);
            else
                check = !boundryChecker.isBoundaryType(map[x - dx][y - dy] , dx , dy);
        }
        return check;
    }
    
    private boolean floodBlock(Tile[][] tempMap , int x , int y , int dx , int dy) {
        if(check(tempMap , x , y , dx , dy)) {
            tempMap[x + dx][y + dy].state = Tile.State.EDGE;
            if(tempMap[x + dx][y + dy].owner == null)
                tempMap[x + dx][y + dy].owner = map[x][y];
            return true;
        }
        return false;
    }
    
    private void floodBlockR(Tile[][] map , int x , int y , int dx , int dy) {
        if(check(map , x , y , dx , dy))
            floodRecursive(x + dx , y + dy);
    }
    
    /**
     * Check if point is in bounds
     * 
     * @param point
     *            - point coordinate
     */
    public boolean isInBounds(Point2D point) {
        return isInBounds((int) point.getX() , (int) point.getY());
    }
    
    /**
     * Check if location is in bounds
     * 
     * @param x
     *            - x coordinate
     * @param y
     *            - y coordinate
     */
    public boolean isInBounds(int x , int y) {
        return boundry.contains(x , y);
    }
    
    /**
     * returns a copy of the flooded map
     */
    public boolean[][] getFillMap() {
        for(int x = 0; x < width; x++) {
            for(int y = 0; y < height; y++) {
                returnFillMap[x][y] = (map[x][y].state == Tile.State.FILLED);
            }
        }
        return returnFillMap;
    }
    
    /**
     * Traces the shortest path to between given points
     */
    public Point[] getPath(Point point1 , Point point2) {
        return genPath(point1.x , point1.y , point2.x , point2.y);
    }
    
    /**
     * Traces the shortest path to between given point
     */
    public Point[] genPath(int x1 , int y1 , int x2 , int y2) {
        reset();
        put(x1 , y1);
        flood();
        ArrayList<Point> points = new ArrayList<>();
        Tile t = map[x2][y2];
        while(true) {
            points.add(t);
            t = t.owner;
            if(t == null)
                break;
        }
        return points.toArray(new Point[points.size()]);
    }
    
    public void setRange(int x1 , int y1 , int x2 , int y2) {
        if(x2 <= x1 || y2 <= y1)
            throw new IllegalArgumentException();
        boundry.x = x1;
        boundry.y = y1;
        boundry.width = x2 - x1;
        boundry.height = y2 - y1;
    }
}
