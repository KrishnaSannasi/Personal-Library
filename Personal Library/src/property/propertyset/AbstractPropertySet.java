package property.propertyset;

import java.util.HashMap;

import property.AbstractProperty;

public abstract class AbstractPropertySet<V extends AbstractProperty<K> , K> {
	private boolean							baked;
	private boolean							finalized;
	protected final HashMap<String , V>	properties;
	
	public static String format(String name) {
		return name.toLowerCase().replaceAll(" +" , ".");
	}
	
	public AbstractPropertySet() {
		properties = new HashMap<>();
		baked = false;
	}
	
	public abstract void put(String name , V property);
	
	public abstract void put(String name , K value);
	
	public abstract void put(V clone);
	
	public abstract void remove(V property);
	
	public abstract V get(String name);
	
	public abstract V[] getValues();
	
	public final V remove(String name) {
		if(isBaked())
			throw new UnsupportedOperationException("This set is now baked");
		return (V) properties.remove(format(name));
	}
	
	public final String[] getNames() {
		return properties.keySet().toArray(new String[size()]);
	}
	
	public final boolean contains(String name) {
		return get(name) != null;
	}
	
	public final int size() {
		return properties.size();
	}
	
	public final boolean isBaked() {
		return baked;
	}
	
	public boolean isFinalized() {
		return finalized;
	}
	
	public final void bake() {
		baked = true;
	}
	
	public final void finalize() {
		finalized = true;
		bake();
	}
	
	@Override
	public final String toString() {
		return properties.values().toString();
	}
}
