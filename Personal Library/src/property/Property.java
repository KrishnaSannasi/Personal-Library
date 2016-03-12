package property;

import property.propertyset.AbstractPropertySet;

public class Property<V> extends AbstractProperty<V> {
	public Property(String name , V value) {
		super(name , value);
	}
	
	public Property(AbstractPropertySet<AbstractProperty<V> , V> propertySet , String name , V value) {
		super(propertySet , name , value);
	}
	
	public Property(Property<V> property) {
		super(property);
	}
	
	@Override
	public void setValue(V v) {
		if(v == null)
			return;
		if(isBaked())
			throw new UnsupportedOperationException("Cannot change baked property value " + getName());
		this.value = v;
	}
}
