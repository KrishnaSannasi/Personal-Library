package net.connection;

import net.node.AbstractNode;

public class ConnectionBasic<T extends AbstractNode<ConnectionBasic<T> , T>> extends AbstractConnection<T> {
    public ConnectionBasic(T nodeLeft , T nodeRight) {
        super(nodeLeft , nodeRight);
    }
}
