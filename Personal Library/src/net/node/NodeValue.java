package net.node;

import net.connection.ConnectionValue;

public class NodeValue<C , V> extends AbstractNode<ConnectionValue<C , V> , NodeValue<C , V>> {
    private V value;
    
    public NodeValue(V value) {
        setValue(value);
    }
    
    @SafeVarargs
    public NodeValue(NodeValue<C , V>... nodes) {
        super(nodes);
    }
    
    @Override
    public ConnectionValue<C , V> createConnection(NodeValue<C , V> node) {
        return new ConnectionValue<>(null , this , node);
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
