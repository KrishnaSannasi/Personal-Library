package net.node;

import net.connection.ConnectionProperty;
import property.Property;
import property.propertyset.PropertySet;

public class NodeProperty<V> extends NodeValue<ConnectionProperty<V> , PropertySet<V>> {
    @SafeVarargs
    public NodeProperty(Property<V>... properties) {
        super(new PropertySet<>(properties));
    }
    
    @SafeVarargs
    public NodeProperty(NodeProperty<V>... connections) {
        super(connections);
    }
    
    @Override
    public void setValue(PropertySet<V> value) {
        throw new UnsupportedOperationException("Cannot create new property set");
    }
}
