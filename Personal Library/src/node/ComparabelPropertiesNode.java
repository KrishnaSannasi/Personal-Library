package node;

import property.propertyset.ComparablePropertySet;

public class ComparabelPropertiesNode<V extends Comparable<V>> extends ValueNode<ComparablePropertySet<V>> {
	@SafeVarargs
	public ComparabelPropertiesNode(ComparabelPropertiesNode<V>... connections) {
		super(connections);
		super.setValue(new ComparablePropertySet<>());
	}
	
	@Override
	public void setValue(ComparablePropertySet<V> value) {
		throw new UnsupportedOperationException("Cannot create new property set");
	}
}
