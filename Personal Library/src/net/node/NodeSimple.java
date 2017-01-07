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
    public ConnectionSimple addConnection(NodeSimple node) {
        if(node == this)
            return null;
        else if(!connections.contains(node)) {
            ConnectionSimple conn = new ConnectionSimple(this , node);
            connections.add(conn);
            node.connections.add(conn);
            return conn;
        }
        else
            return getConnection(node);
    }

    @Override
    public ConnectionSimple[] getConnections() {
        return connections.toArray(new ConnectionSimple[connections.size()]);
    }
}
