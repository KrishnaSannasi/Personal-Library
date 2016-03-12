package property.propertyset;

import property.ComparableProperty;

public class ComparablePropertySet<V extends Comparable<V>> extends AbstractPropertySet<ComparableProperty<V> , V> {
	@SafeVarargs
	public ComparablePropertySet(ComparableProperty<V>... initialProperties) {
		super();
		for(ComparableProperty<V> property: initialProperties) {
			ComparableProperty<V> clone = new ComparableProperty<V>(property);
			put((ComparableProperty<V>) clone);
		}
	}
	
	public ComparablePropertySet(ComparablePropertySet<V> initialProperties) {
		this(initialProperties.getValues());
	}
	
	@Override
	public void put(String name , ComparableProperty<V> property) {
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
			put((ComparableProperty<V>) new ComparableProperty<>(name , value));
		}
	}
	
	@Override
	public void put(ComparableProperty<V> clone) {
		if(isBaked())
			throw new UnsupportedOperationException("This set is now baked");
		properties.put(format(clone.getName()) , clone);
	}
	
	@Override
	public void remove(ComparableProperty<V> property) {
		remove(property.getName());
	}
	
	@Override
	public ComparableProperty<V> get(String name) {
		return properties.get(format(name));
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public ComparableProperty<V>[] getValues() {
		return (ComparableProperty<V>[]) properties.values().toArray(new ComparableProperty[size()]);
	}
}
