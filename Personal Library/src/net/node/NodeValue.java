package net.node;

import net.connection.ConnectionValue;

public class NodeValue<C , V> extends AbstractNode<ConnectionValue<C , V> , NodeValue<C , V>> {
    private V value;
    
    public NodeValue(V value) {
        setValue(value);
    }
    
    @Override
    public ConnectionValue<C , V> addConnection(NodeValue<C , V> node) {
        if(node == this)
            return null;
        else if(!connections.contains(node)) {
            ConnectionValue<C , V> conn = new ConnectionValue<>(null , this , node);
            connections.add(conn);
            node.connections.add(conn);
            return conn;
        }
        else
            return getConnection(node);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public ConnectionValue<C , V>[] getConnections() {
        return connections.toArray(new ConnectionValue[connections.size()]);
    }
    
    public void setValue(V value) {
        this.value = value;
    }
    
    public V getValue() {
        return value;
    }
}
