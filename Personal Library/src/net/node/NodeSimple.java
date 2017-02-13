package net.node;

import net.connection.ConnectionSimple;

/**
 * Represents a node in a network of connections
 * 
 * @author Krishna
 * 
 */
public class NodeSimple extends AbstractNode<ConnectionSimple , NodeSimple> {
    @Override
    public ConnectionSimple createConnection(NodeSimple node) {
        return new ConnectionSimple(this , node);
    }

    @Override
    public ConnectionSimple[] getConnections() {
        return connections.toArray(new ConnectionSimple[connections.size()]);
    }
}
