package net.connection;

import net.node.NodeProperty;
import net.node.NodeValue;
import property.propertyset.PropertySet;

public class ConnectionProperty<V> extends AbstractConnection<NodeValue<ConnectionProperty<V> , PropertySet<V>>> {
    public ConnectionProperty(NodeProperty<V> nodeLeft , NodeProperty<V> nodeRight) {
        super(nodeLeft , nodeRight);
    }
}
