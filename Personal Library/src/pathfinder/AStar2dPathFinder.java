package pathfinder;

import static pathfinder.AStar2dPathFinder.AStar2dNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class AStar2dPathFinder extends PathFinder<AStar2dNode> {
    private static int nextid = 0;
    
    public static class AStar2dNode extends PathFinder.Node implements Comparable<AStar2dNode> {
        public final int  id = nextid++;
        private Node.Type type;
        double            sdist , edist;
        AStar2dNode       pointTo;
        
        public AStar2dNode(Node.Type type , int x , int y) {
            super(x , y);
            this.type = type;
        }
        
        public Node.Type getType() {
            return type;
        }
        
        public int getX() {
            return (int) getPoint()[0];
        }
        
        public int getY() {
            return (int) getPoint()[1];
        }
        
        private static int sign(double num) {
            if(num < 0)
                return -1;
            else if(num > 0)
                return 1;
            else
                return 0;
        }
        
        @Override
        public int compareTo(AStar2dNode o) {
            return sign(sign(edist + sdist - o.edist - o.sdist) * 4 + sign(edist - o.edist) * 2 + sign(id - o.id));
        }
        
        @Override
        public String toString() {
            return "" + (edist + sdist);
        }
    }
    
    public AStar2dPathFinder(AStar2dNode[] nodes) {
        super(nodes);
    }
    
    public AStar2dNode getNode(int x , int y) {
        for(AStar2dNode n: nodes) {
            if(n.getX() == x && n.getY() == y)
                return n;
        }
        return null;
    }
    
    @Override
    protected AStar2dNode[] getPathArray(AStar2dNode start , AStar2dNode end) {
        for(AStar2dNode n: nodes) {
            n.sdist = Double.POSITIVE_INFINITY;
            n.edist = 0;
        }
        
        LinkedList<AStar2dNode> path = new LinkedList<>();
        
        PriorityQueue<AStar2dNode> seed = new PriorityQueue<>();
        ArrayList<AStar2dNode> completed = new ArrayList<>();
        AStar2dNode current;
        
        boolean running = true;
        seed.add(start);
        
        start.pointTo = null;
        start.edist = start.distance(end);
        start.sdist = 0;
        
        do {
            current = seed.poll();
            completed.add(current);
            
            if(current == null)
                break;
            
            Node[] nodes = current.getNeighbors();
            
            for(Node n: nodes) {
                AStar2dNode asn = (AStar2dNode) n;
                if(completed.contains(asn))
                    continue;
                
                double sdist;
                
                asn.edist = asn.distance(end);
                sdist = current.sdist + current.distance(n);
                
                if(asn.sdist > sdist) {
                    asn.sdist = sdist;
                    asn.pointTo = current;
                }
                
                switch (asn.type) {
                    case EMPTY:
                        break;
                    
                    case OBSTACLE: {
                        completed.add(asn);
                        continue;
                    }
                    
                    case ATTRACTOR: {
                        asn.edist -= 100;
                        for(Node a: n.getNeighbors()) {
                            ((AStar2dNode) a).edist -= 50;
                        }
                        break;
                    }
                    
                    case REPULSOR: {
                        asn.edist += 100;
                        for(Node a: n.getNeighbors()) {
                            ((AStar2dNode) a).edist += 50;
                        }
                        
                        break;
                    }
                }
                
                if(!seed.contains(asn))
                    seed.add(asn);
                
                if(asn == end)
                    running = false;
            }
            
            if(seed.size() == 0)
                break;
        } while(running);
        
        if(running)
            return new AStar2dNode[] {start};
        
        current = end;
        while(current != null) {
            path.addFirst(current);
            current = current.pointTo;
        }
        
        return path.toArray(new AStar2dNode[path.size()]);
    }
}
