package net.connection;

import net.node.NodeSimple;

public class ConnectionSimple extends AbstractConnection<NodeSimple> {
    public ConnectionSimple(NodeSimple nodeLeft , NodeSimple nodeRight) {
        super(nodeLeft , nodeRight);
    }
}
