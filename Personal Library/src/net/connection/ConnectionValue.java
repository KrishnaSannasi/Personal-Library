package net.connection;

import net.node.NodeValue;

public class ConnectionValue<V , N> extends AbstractConnection<NodeValue<V , N>> {
    private V value;
    
    public ConnectionValue(V value , NodeValue<V , N> nodeLeft , NodeValue<V , N> nodeRight) {
        super(nodeLeft , nodeRight);
    }
    
    public void setValue(V value) {
        this.value = value;
    }
    
    public V getValue() {
        return value;
    }
}
