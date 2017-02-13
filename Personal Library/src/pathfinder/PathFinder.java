package pathfinder;

import static pathfinder.PathFinder.Node;

import java.util.ArrayList;

public abstract class PathFinder<T extends Node> {
    public static class Node {
        public static enum Type {
            OBSTACLE , EMPTY , ATTRACTOR , REPULSOR;
        }
        
        private int             dim;
        private double[]        point;
        private ArrayList<Node> neighbors;
        
        public Node(double... p) {
            point = new double[dim = p.length];
            for(int i = 0; i < p.length; i++)
                point[i] = p[i];
            
            neighbors = new ArrayList<>();
            
        }
        
        protected final double[] getPoint() {
            return point;
        }
        
        public void addNeighbor(Node node) {
            if(node == null || neighbors.contains(node))
                return;
            neighbors.add(node);
            node.neighbors.add(this);
        }
        
        public void clearNeighbors() {
            for(int i = 0; i < neighbors.size(); i++) {
                neighbors.get(i).neighbors.remove(this);
            }
            neighbors.clear();
        }
        
        public Node[] getNeighbors() {
            return neighbors.toArray(new Node[neighbors.size()]);
        }
        
        public int getDimension() {
            return dim;
        }
        
        public double distanceSq(Node n) {
            double sum = 0;
            
            for(int i = 0; i < dim; i++) {
                double diff = n.point[i] - point[i];
                sum += diff * diff;
            }
            
            return sum;
        }
        
        public double distance(Node n) {
            return Math.sqrt(distanceSq(n));
        }
    }
    
    public class NodePath {
        private NodePath next;
        private T        node;
        
        public NodePath(T node) {
            this.node = node;
        }
        
        public NodePath(T node , NodePath next) {
            this(node);
            this.next = next;
        }
        
        void setNext(NodePath nodePath) {
            this.next = nodePath;
        }
        
        public T getNode() {
            return node;
        }
        
        public NodePath getNext() {
            return next;
        }
    }
    
    protected ArrayList<T> nodes;
    private boolean        neighborsSetUp = false;
    
    public PathFinder(T[] nodes) {
        this.nodes = new ArrayList<>();
        
        for(T node: nodes)
            add(node);
    }
    
    public boolean contains(T node) {
        return nodes.contains(node);
    }
    
    public void add(T node) {
        if(node != null) {
            neighborsSetUp = false;
            nodes.add(node);
        }
    }
    
    public void updateNeighbors() {
        T n1 , n2;
        for(int i = 0; i < nodes.size(); i++)
            nodes.get(i).clearNeighbors();
        
        for(int i = 0; i < nodes.size(); i++) {
            n1 = nodes.get(i);
            for(int j = i + 1; j < nodes.size(); j++) {
                n2 = nodes.get(j);
                if(n1.distanceSq(n2) <= Math.max(n1.getDimension() , n2.getDimension()))
                    n1.addNeighbor(n2);
            }
        }
        neighborsSetUp = true;
    }
    
    public T[] getPath(T start , T end) {
        if(contains(start) && contains(end)) {
            if(!neighborsSetUp)
                updateNeighbors();
            return getPathArray(start , end);
        }
        else {
            return null;
        }
    }
    
    protected abstract T[] getPathArray(T start , T end);
}
