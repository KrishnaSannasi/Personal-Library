package net.node;

public class NodeValueSC<V> extends NodeSimple {
    private V value;
    
    public NodeValueSC(V value) {
        setValue(value);
    }
    
    public V getValue() {
        return value;
    }
    
    public void setValue(V value) {
        this.value = value;
    }
}
