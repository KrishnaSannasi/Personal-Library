package node;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Represents a node in a network of connections
 * 
 * @author Krishna
 * 
 */
public class Node<E> implements Comparable<Node<E>> {
    public static int                   nextID = 0;
    
    public final int                    id;
    protected LinkedList<Connection<E>> connections;
    
    @SafeVarargs
    public Node(Node<E>... connections) {
        id = nextID++;
        this.connections = new LinkedList<>();
        addConnections(connections);
    }
    
    public final int getNumberOfConnections() {
        return connections.size();
    }
    
    public final Connection<E> getConnection(Node<E> node) {
        for(Connection<E> connection: connections) {
            if(connection.equals(node))
                return connection;
        }
        return null;
    }
    
    @SuppressWarnings("unchecked")
    public final Connection<E>[] getConnections() {
        return connections.toArray(new Connection[connections.size()]);
    }
    
    public final boolean hasConnectionWith(Node<E> node) {
        return connections.contains(node);
    }
    
    @SafeVarargs
    public static <V> LinkedList<Collection<Node<V>>> getGroups(Node<V>... nodes) {
        LinkedList<Node<V>> nonprintedNodes = new LinkedList<>();
        LinkedList<Collection<Node<V>>> groups = new LinkedList<>();
        
        for(Node<V> node: nodes) {
            nonprintedNodes.add(node);
        }
        Collection<Node<V>> all;
        for(int i = 0; i < nonprintedNodes.size();) {
            all = nonprintedNodes.get(i++).getAllConnections().keySet();
            
            groups.add(all);
            nonprintedNodes.removeAll(all);
            
            if(all.size() != 0)
                i = 0;
        }
        return groups;
    }
    
    @SafeVarargs
    public static <V , N extends Nodable<? extends Node<V>>> LinkedList<Collection<N>> getGroups(N... nodes) {
        HashMap<Node<V> , N> map = new HashMap<>();
        LinkedList<N> unprocessedNodes = new LinkedList<>();
        LinkedList<Collection<N>> groups = new LinkedList<>();
        
        for(N nodable: nodes) {
            unprocessedNodes.add(nodable);
            map.put(nodable.getNode() , nodable);
        }
        Collection<Node<V>> all;
        Collection<N> allNodable = new HashSet<>();
        
        for(int i = 0; i < unprocessedNodes.size();) {
            all = unprocessedNodes.get(i++).getNode().getAllConnections().keySet();
            
            allNodable.clear();
            for(Node<V> node: all)
                allNodable.add(map.get(node));
            
            groups.add(allNodable);
            unprocessedNodes.removeAll(all);
            if(all.size() != 0)
                i = 0;
        }
        return groups;
    }
    
    @SafeVarargs
    public static <E> LinkedList<Node<E>> pathTo(Node<E> start , Node<E> find , Node<E>... waypoints) {
        LinkedList<Node<E>> path = new LinkedList<>() , temp;
        Node<E> current = start;
        for(Node<E> node: waypoints) {
            temp = current.pathTo(node);
            if(temp == null)
                return null;
            path.addAll(temp);
            current = node;
        }
        temp = current.pathTo(find);
        if(temp == null)
            return null;
        path.addAll(temp);
        return path;
    }
    
    /**
     * Returns the path via connections to a desired node
     * 
     * @return null if there is no path and a list of nodes that is the path to
     *         the node
     */
    public final LinkedList<Node<E>> pathTo(Node<E> find) {
        return pathTo(find , new LinkedList<>());
    }
    
    @SuppressWarnings("unchecked")
    private LinkedList<Node<E>> pathTo(Node<E> find , LinkedList<Node<E>> path) {
        LinkedList<LinkedList<Node<E>>> allPaths = new LinkedList<>();
        LinkedList<Node<E>> temp , arg;
        
        if(path.contains(this))
            return null;
        
        path.add(this);
        
        if(this == find)
            return path;
        
        for(Connection<E> connection: connections) {
            arg = new LinkedList<>();
            arg.addAll(path);
            temp = connection.getOther(this).pathTo(find , arg);
            if(temp != null)
                allPaths.add(temp);
        }
        
        LinkedList<Node<E>> min = null;
        
        for(LinkedList<Node<E>> tempPath: allPaths) {
            if(tempPath.contains(find) && (min == null || min.size() > tempPath.size()))
                min = tempPath;
        }
        
        return min;
    }
    
    public Connection<E> addConnection(Node<E> node) {
        if(node == this)
            return null;
        else if(!connections.contains(node)) {
            Connection<E> conn = new Connection<>(this , node);
            connections.add(conn);
            node.connections.add(conn);
            return conn;
        }
        else
            return getConnection(node);
    }
    
    public final void removeConnection(Node<E> node) {
        connections.remove(node);
        node.connections.remove(this);
    }
    
    @SafeVarargs
    public final void addConnections(Node<E>... connections) {
        for(Node<E> connection: connections) {
            addConnection(connection);
        }
    }
    
    @SafeVarargs
    public final void removeConnections(Node<E>... connections) {
        for(Node<E> connection: connections) {
            removeConnection(connection);
        }
    }
    
    public final void clearConnections() {
        for(Connection<E> connection: connections) {
            removeConnection(connection.getOther(this));
        }
    }
    
    /**
     * Returns a map of all the nodes that this node can access indirectly via
     * connections to other nodes and how many connections away this node is
     * from this node. The key is the node and the value is how many connections
     * away the node is.
     */
    public final Map<Node<E> , Integer> getAllConnections() {
        TreeMap<Node<E> , Integer> deepStorage = new TreeMap<>();
        TreeSet<Node<E>> uncheckedCurrent = new TreeSet<>() , uncheckedTemp = new TreeSet<>();
        int depth = 1;
        deepStorage.put(this , 0);
        for(Connection<E> connection: this.connections) {
            deepStorage.put(connection.getOther(this) , depth);
            uncheckedCurrent.add(connection.getOther(this));
        }
        
        do {
            ++depth;
            for(Node<E> node: uncheckedCurrent) {
                for(Connection<E> connection: node.connections) {
                    Node<E> other = connection.getOther(node);
                    if(!deepStorage.containsKey(other)) {
                        deepStorage.put(other , depth);
                        uncheckedTemp.add(other);
                    }
                }
            }
            
            TreeSet<Node<E>> temp = uncheckedTemp;
            uncheckedTemp = uncheckedCurrent;
            uncheckedCurrent = temp;
            
            uncheckedTemp.clear();
        } while(uncheckedCurrent.size() != 0);
        
        return deepStorage;
    }
    
    /**
     * Returns the name of this node
     */
    public String getName() {
        return "Node " + id;
    }
    
    /**
     * Compares the id of the nodes for easy sorting using TreeSet and TreeMap
     * 
     */
    @Override
    public int compareTo(Node<E> link) {
        return (int) Math.signum(id - link.id);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Node) {
            return ((Node<E>) obj).id == id;
        }
        else if(obj instanceof Connection) {
            return equals(((Connection<E>) obj).node1) || equals(((Connection<E>) obj).node2);
        }
        else {
            return false;
        }
    }
    
    @Override
    public final String toString() {
        StringBuffer connections = new StringBuffer();
        for(Connection<E> connection: this.connections) {
            connections.append(connection.getOther(this).getName()).append(", ");
        }
        if(this.connections.size() != 0)
            connections.delete(connections.length() - 2 , connections.length());
        return String.format("%s -> %s" , getName() , connections.toString());
    }
    
    private Link link;
    
    private class Link {
        public Link    parent;
        public Node<E> node;
        public int     depth;
    }
    
    private void createLink(Link parent , int depth) {
        link = new Link();
        link.depth = depth;
        link.parent = parent;
        link.node = this;
    }
    
    @SuppressWarnings("rawtypes")
    public Node[] getPath(Node<E> find) {
        TreeMap<Node<E> , Integer> deepStorage = new TreeMap<>();
        TreeSet<Node<E>> uncheckedCurrent = new TreeSet<>() , uncheckedTemp = new TreeSet<>();
        int depth = 1;
        
        createLink(null , 0);
        
        deepStorage.put(this , 0);
        for(Connection<E> connection: this.connections) {
            deepStorage.put(connection.getOther(this) , depth);
            uncheckedCurrent.add(connection.getOther(this));
            connection.getOther(this).createLink(link , depth);
        }
        
        do {
            ++depth;
            for(Node<E> node: uncheckedCurrent) {
                for(Connection<E> connection: node.connections) {
                    Node<E> other = connection.getOther(node);
                    if(!deepStorage.containsKey(other)) {
                        deepStorage.put(other , depth);
                        uncheckedTemp.add(other);
                        other.createLink(node.link , depth);
                        
                        if(other == find) {
                            Link link = other.link;
                            Node[] path = new Node[depth + 1];
                            while(link != null) {
                                path[link.depth] = link.node;
                                link = link.parent;
                            }
                            return path;
                        }
                    }
                }
            }
            
            TreeSet<Node<E>> temp = uncheckedTemp;
            uncheckedTemp = uncheckedCurrent;
            uncheckedCurrent = temp;
            
            uncheckedTemp.clear();
        } while(uncheckedCurrent.size() != 0);
        
        return null;
    }
}
