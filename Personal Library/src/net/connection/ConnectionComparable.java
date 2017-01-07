package net.connection;

import net.node.NodeValue;

public class ConnectionComparable extends AbstractConnection<NodeValue<ConnectionComparable , ?>> {
    public ConnectionComparable(NodeValue<ConnectionComparable , ?> nodeLeft , NodeValue<ConnectionComparable , ?> nodeRight) {
        super(nodeLeft , nodeRight);
    }
}
