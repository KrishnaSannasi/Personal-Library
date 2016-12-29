package net.node;

public interface Nodable<T extends AbstractNode<? , ?>> {
    void setNode(T node);
    
    T getNode();
}
