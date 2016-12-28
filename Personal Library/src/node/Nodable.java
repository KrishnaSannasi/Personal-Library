package node;

public interface Nodable<T extends Node<?>> {
    void setNode(T node);
    
    T getNode();
}
