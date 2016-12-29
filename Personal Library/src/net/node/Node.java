package net.node;

import net.connection.AbstractConnection;
import net.connection.Connection;

/**
 * Represents a node in a network of connections
 * 
 * @author Krishna
 * 
 */
public class Node extends AbstractNode<Connection , Node> {
    @Override
    public AbstractConnection<Node> addConnection(Node node) {
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
}
