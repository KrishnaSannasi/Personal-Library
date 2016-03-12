package property.propertyset;

import property.Property;

/**
 * 
 * @author Krishna
 * 			
 * @param <V>
 *           the type of the values in the parameter
 */
public class PropertySet<V> extends AbstractPropertySet<Property<V> , V> {
	@SafeVarargs
	public PropertySet(Property<V>... initialProperties) {
		super();
		for(Property<V> property: initialProperties) {
			Property<V> clone = new Property<>(property);
			put((Property<V>) clone);
		}
	}
	
	public PropertySet(PropertySet<V> initialProperties) {
		this(initialProperties.getValues());
	}
	
	@Override
	public void put(String name , Property<V> property) {
		if(contains(name)) {
			if(isFinalized())
				throw new UnsupportedOperationException("This set is finalized (values cannot be changed)");
			properties.get(format(name)).setValue(property.getValue());
		}
		else {
			put(property);
		}
	}
	
	@Override
	public void put(String name , V value) {
		if(contains(name)) {
			if(isFinalized())
				throw new UnsupportedOperationException("This set is finalized (values cannot be changed)");
			properties.get(format(name)).setValue(value);
		}
		else {
			put((Property<V>) new Property<>(name , value));
		}
	}
	
	@Override
	public void put(Property<V> clone) {
		if(isBaked())
			throw new UnsupportedOperationException("This set is now baked");
		properties.put(format(clone.getName()) , clone);
	}
	
	@Override
	public void remove(Property<V> property) {
		remove(property.getName());
	}
	
	@Override
	public Property<V> get(String name) {
		return properties.get(format(name));
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Property<V>[] getValues() {
		return (Property<V>[]) properties.values().toArray(new Property[size()]);
	}
}
