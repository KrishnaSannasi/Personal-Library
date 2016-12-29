package net.node;

import net.connection.Connection;

/**
 * Represents a node in a network of connections
 * 
 * @author Krishna
 * 
 */
public class Node extends AbstractNode<Connection , Node> {
    @Override
    public Connection addConnection(Node node) {
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
