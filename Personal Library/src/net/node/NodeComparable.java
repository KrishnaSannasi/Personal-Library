package net.node;

import net.connection.ConnectionComparable;

public class NodeComparable<V extends Comparable<V>> extends NodeValue<ConnectionComparable , V> {
    public NodeComparable(V value) {
        super(value);
    }
    
    @SafeVarargs
    public NodeComparable(NodeComparable<V>... nodes) {
        super(nodes);
    }
    
    public final int compareTo(NodeComparable<V> compare) {
        return getValue().compareTo(compare.getValue());
    }
}
