package property;

import property.propertyset.AbstractPropertySet;

public abstract class AbstractProperty<V> {
	protected V				value;
	private final String	name;
	private boolean		isBaked;
	public final boolean	positive;
	
	public AbstractProperty(String name , V value) {
		if(name.contains("+") ^ name.contains("-"))
			positive = name.contains("+");
		else
			throw new IllegalArgumentException("Please choose to put + OR - to signify posative or negative when evaluating");
		this.name = name.toLowerCase().replaceAll(" +" , ".").replace("+" , "").replace("-" , "");
		this.value = value;
	}
	
	public AbstractProperty(AbstractPropertySet<AbstractProperty<V> , V> propertySet , String name , V value) {
		this(name , value);
		if(propertySet.contains(name))
			throw new IllegalArgumentException("Duplicate Name " + name);
		propertySet.put(this);
	}
	
	public AbstractProperty(AbstractProperty<V> property) {
		this.name = property.name;
		this.positive = property.positive;
		this.value = property.value;
	}
	
	public abstract void setValue(V value);
	
	public final V getValue() {
		return value;
	}
	
	public final boolean isBaked() {
		return isBaked;
	}
	
	public final boolean isPositive() {
		return positive;
	}
	
	public final String getName() {
		return name;
	}
	
	public final void bake() {
		isBaked = true;
	}
	
	@Override
	public final String toString() {
		return String.format("%s=%s" , name , value);
	}
}
