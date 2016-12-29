package net;

import net.node.Node;
import net.node.NodeValue;
import property.propertyset.ComparablePropertySet;

public class ComparabelPropertiesNode<V,E extends Comparable<E>> extends NodeValue<V,ComparablePropertySet<E>> {
	@SafeVarargs
	public ComparabelPropertiesNode(Node<V>... connections) {
		super(connections);
		super.setValue(new ComparablePropertySet<>());
	}
	
	@Override
	public void setValue(ComparablePropertySet<E> value) {
		throw new UnsupportedOperationException("Cannot create new property set");
	}
}
