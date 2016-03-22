package node;

import property.propertyset.PropertySet;

public class PropertiesNode<V> extends ValueNode<PropertySet<V>> {
	@SafeVarargs
	public PropertiesNode(PropertiesNode<V>... connections) {
		super(connections);
		super.setValue(new PropertySet<>());
	}
	
	@Override
	public void setValue(PropertySet<V> value) {
		throw new UnsupportedOperationException("Cannot create new property set");
	}
}
