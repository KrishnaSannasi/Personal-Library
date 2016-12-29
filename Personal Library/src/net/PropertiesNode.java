package net;

import net.node.NodeSimple;
import net.node.NodeValue;
import property.propertyset.PropertySet;

public class PropertiesNode<V , E> extends NodeValue<V , PropertySet<E>> {
	@SafeVarargs
	public PropertiesNode(NodeSimple<V>... connections) {
		super(new PropertySet<>() , connections);
	}
	
	@Override
	public void setValue(PropertySet<E> value) {
		throw new UnsupportedOperationException("Cannot create new property set");
	}
}
