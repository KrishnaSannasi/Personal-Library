package net.node;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

import net.connection.AbstractConnection;

public abstract class AbstractNode<C extends AbstractConnection<N> , N extends AbstractNode<C , N>> implements Comparable<N> {
    public static int nextID = 0;
    
    public static final <C extends AbstractConnection<N> , N extends AbstractNode<C , N>> Map<N , Integer> getAllConnections(N init) {
        TreeMap<N , Integer> deepStorage = new TreeMap<>();
        TreeSet<N> uncheckedCurrent = new TreeSet<>() , uncheckedTemp = new TreeSet<>();
        int depth = 1;
        deepStorage.put(init , 0);
        for(C connection: init.connections) {
            deepStorage.put(connection.getOther(init) , depth);
            uncheckedCurrent.add(connection.getOther(init));
        }
        
        do {
            ++depth;
            for(N node: uncheckedCurrent) {
                for(C connection: node.connections) {
                    N other = connection.getOther(node);
                    if(!deepStorage.containsKey(other)) {
                        deepStorage.put(other , depth);
                        uncheckedTemp.add(other);
                    }
                }
            }
            
            TreeSet<N> temp = uncheckedTemp;
            uncheckedTemp = uncheckedCurrent;
            uncheckedCurrent = temp;
            
            uncheckedTemp.clear();
        } while(uncheckedCurrent.size() != 0);
        
        return deepStorage;
    }
    
    /**
     * Returns the path via connections to a desired node
     * 
     * @return null if there is no path and a list of nodes that is the path to
     *         the node
     */
    public static final <C extends AbstractConnection<N> , N extends AbstractNode<C , N>> LinkedList<N> getPath(N start , N find) {
        if(start == null || find == null)
            return null;
        else
            return getPath(start , find , new LinkedList<>());
    }
    
    @SuppressWarnings("unchecked")
    private static <C extends AbstractConnection<N> , N extends AbstractNode<C , N>> LinkedList<N> getPath(N current , N find , LinkedList<N> path) {
        if(path.contains(current))
            return null;
        
        path.add(current);
        
        if(current == find)
            return path;
        
        LinkedList<LinkedList<N>> allPaths = new LinkedList<>();
        LinkedList<N> temp , arg;
        
        for(C connection: current.connections) {
            arg = new LinkedList<>();
            arg.addAll(path);
            temp = getPath(connection.getOther(current) , find , arg);
            if(temp != null)
                allPaths.add(temp);
        }
        
        LinkedList<N> min = null;
        
        for(LinkedList<N> tempPath: allPaths) {
            if(tempPath.contains(find) && (min == null || min.size() > tempPath.size()))
                min = tempPath;
        }
        
        return min;
    }
    
    @SafeVarargs
    public static <C extends AbstractConnection<N> , N extends AbstractNode<C , N>> LinkedList<Collection<N>> getGroups(N... nodes) {
        LinkedList<N> unprocessed = new LinkedList<>();
        LinkedList<Collection<N>> groups = new LinkedList<>();
        
        for(N node: nodes) {
            unprocessed.add(node);
        }
        Collection<N> all;
        for(int i = 0; i < unprocessed.size();) {
            all = getAllConnections(unprocessed.get(i++)).keySet();
            
            groups.add(all);
            unprocessed.removeAll(all);
            
            if(all.size() != 0)
                i = 0;
        }
        return groups;
    }
    
    @SafeVarargs
    public static <C extends AbstractConnection<N> , N extends AbstractNode<C , N> , Y extends Nodable<N>> LinkedList<Collection<Y>> getGroups(Y... nodables) {
        Map<N , Y> map = new HashMap<>();
        LinkedList<N> unprocessed = new LinkedList<>();
        LinkedList<Collection<Y>> groups = new LinkedList<>();
        
        for(Y nodable: nodables) {
            unprocessed.add(nodable.getNode());
            map.put(nodable.getNode() , nodable);
        }
        Collection<N> all;
        for(int i = 0; i < unprocessed.size();) {
            all = getAllConnections(unprocessed.get(i++)).keySet();
            Collection<Y> allNodable = new LinkedList<>();
            
            for(N node: all)
                allNodable.add(map.get(node));
            
            groups.add(allNodable);
            unprocessed.removeAll(all);
            
            if(all.size() != 0)
                i = 0;
        }
        return groups;
    }
    
    public final int        id;
    protected LinkedList<C> connections;
    
    @SafeVarargs
    public AbstractNode(N... connections) {
        id = nextID++;
        this.connections = new LinkedList<>();
        addConnections(connections);
    }
    
    public final C getConnection(N node) {
        for(C connection: connections) {
            if(connection.equals(node))
                return connection;
        }
        return null;
    }
    
    public final int getNumberOfConnections() {
        return connections.size();
    }
    
    @SuppressWarnings("unchecked")
    public abstract C[] getConnections();
    
    public final boolean hasConnectionWith(N node) {
        return connections.contains(node);
    }
    
    public abstract C addConnection(N node);
    
    public final void removeConnection(N node) {
        connections.remove(node);
        node.connections.remove(this);
    }
    
    public final void removeConnection(C conn) {
        conn.nodeLeft.connections.remove(conn);
        conn.nodeRight.connections.remove(conn);
    }
    
    @SafeVarargs
    public final void addConnections(N... connections) {
        for(N connection: connections) {
            addConnection(connection);
        }
    }
    
    @SafeVarargs
    public final void removeConnections(N... connections) {
        for(N connection: connections) {
            removeConnection(connection);
        }
    }
    
    public final void clearConnections() {
        for(C connection: connections) {
            removeConnection(connection);
        }
    }
    
    public String getName() {
        return "A-Node " + id;
    }
    
    @Override
    public int compareTo(N node) {
        return (int) Math.signum(id - node.id);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof AbstractNode) {
            return ((AbstractNode<C , N>) obj).id == id;
        }
        else if(obj instanceof AbstractConnection) {
            return obj.equals(this);
        }
        else {
            return false;
        }
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public final String toString() {
        StringBuffer connections = new StringBuffer();
        for(C connection: this.connections) {
            connections.append(connection.getOther((N) this).getName()).append(", ");
        }
        if(this.connections.size() != 0)
            connections.delete(connections.length() - 2 , connections.length());
        return String.format("%s -> {%s}" , getName() , connections.toString());
    }
}
