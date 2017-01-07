package net.connection;

import net.node.AbstractNode;

public abstract class AbstractConnection<N extends AbstractNode<? , N>> {
    public final N nodeLeft , nodeRight;
    
    public AbstractConnection(N nodeLeft , N nodeRight) {
        this.nodeLeft = nodeLeft;
        this.nodeRight = nodeRight;
    }
    
    public final N getOther(N node) {
        if(node == nodeLeft)
            return nodeRight;
        if(node == nodeRight)
            return nodeLeft;
        return null;
    }
    
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof AbstractNode) {
            return nodeLeft.equals(obj) || nodeRight.equals(obj);
        }
        else if(obj instanceof ConnectionSimple) {
            return obj.equals(nodeLeft) && obj.equals(nodeRight);
        }
        return super.equals(obj);
    }
}
