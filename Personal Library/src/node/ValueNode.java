package node;

public class ValueNode<E> extends Node {
	private E value;
	
	@SafeVarargs
	public ValueNode(Node... connections) {
		super(connections);
	}
	
	@SuppressWarnings("unchecked")
	@SafeVarargs
	public ValueNode(E value , Node... connections) {
		super(connections);
		this.value = value;
		if(value instanceof Nodable)
			((Nodable<E>) value).setNode(this);
	}
	
	public E getValue() {
		return value;
	}
	
	@SuppressWarnings("unchecked")
	public void setValue(E value) {
		if(this.value instanceof Nodable) {
			((Nodable<E>) this.value).setNode(null);
		}
		this.value = value;
		if(value instanceof Nodable)
			((Nodable<E>) value).setNode(this);
	}
	
	@Override
	public String getName() {
		return value.toString();
	}
}
