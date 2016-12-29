package net.node;

import net.connection.Connection;

/**
 * Represents a node in a network of connections
 * 
 * @author Krishna
 * 
 */
public class NodeSimple extends AbstractNode<Connection , NodeSimple> {
    @Override
    public Connection addConnection(NodeSimple node) {
        if(node == this)
            return null;
        else if(!connections.contains(node)) {
            Connection conn = new Connection(this , node);
            connections.add(conn);
            node.connections.add(conn);
            return conn;
        }
        else
            return getConnection(node);
    }

    @Override
    public Connection[] getConnections() {
        return connections.toArray(new Connection[connections.size()]);
    }
}
