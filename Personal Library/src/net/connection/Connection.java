package net.connection;

import net.node.Node;

public class Connection extends AbstractConnection<Node> {
    public Connection(Node nodeLeft , Node nodeRight) {
        super(nodeLeft , nodeRight);
    }
}
