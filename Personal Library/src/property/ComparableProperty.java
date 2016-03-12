package property;

import property.propertyset.AbstractPropertySet;

public class ComparableProperty<V extends Comparable<V>> extends AbstractProperty<V> {
	public boolean	setMode;
	private V		min , max;
	
	public ComparableProperty(AbstractPropertySet<AbstractProperty<V> , V> propertySet , String name , V value) {
		super(propertySet , name , value);
		this.max = null;
		this.min = null;
		setMode = true;
	}
	
	public ComparableProperty(ComparableProperty<V> property) {
		super(property);
		this.max = null;
		this.min = null;
		setMode = true;
	}
	
	public ComparableProperty(String name , V value) {
		super(name , value);
		this.max = null;
		this.min = null;
		setMode = true;
	}
	
	public ComparableProperty(AbstractPropertySet<AbstractProperty<V> , V> propertySet , String name , V value , V min , V max) {
		super(propertySet , name , value);
		setMin(min);
		setMax(max);
		setMode = true;
	}
	
	public ComparableProperty(ComparableProperty<V> property , V min , V max) {
		super(property);
		setMin(min);
		setMax(max);
		setMode = true;
	}
	
	public ComparableProperty(String name , V value , V min , V max) {
		super(name , value);
		setMin(min);
		setMax(max);
		setMode = true;
	}
	
	public V getMin() {
		return min;
	}
	
	public V getMax() {
		return max;
	}
	
	public void setMax(V max) {
		if(max == null)
			return;
		if(min == null || max.compareTo(min) > 0)
			this.max = max;
	}
	
	public void setMin(V min) {
		if(min == null)
			return;
		if(max == null || min.compareTo(max) < 0)
			this.min = min;
	}
	
	@Override
	public void setValue(V v) {
		if(v == null || isBaked())
			return;
		if((min == null || min.compareTo(v) <= 0) && (max == null || v.compareTo(max) <= 0))
			this.value = v;
		else if(setMode)
			if(v.compareTo(max) <= 0)
				this.value = min;
			else
				this.value = max;
	}
}
