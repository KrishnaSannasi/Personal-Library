package net.connection;

public interface Connectable<T extends AbstractConnection<?>> {
    void setConnection(T connection);
    
    T getConnection();
}
