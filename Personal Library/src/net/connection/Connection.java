package net.connection;

import net.node.NodeSimple;

public class Connection extends AbstractConnection<NodeSimple> {
    public Connection(NodeSimple nodeLeft , NodeSimple nodeRight) {
        super(nodeLeft , nodeRight);
    }
}
