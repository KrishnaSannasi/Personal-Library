package node;

public class ValueNode<E> extends Node {
	private E value;
	
	@SafeVarargs
	public ValueNode(Node... connections) {
		super(connections);
	}
	
	@SafeVarargs
	public ValueNode(E value , Node... connections) {
		super(connections);
		this.value = value;
		if(value instanceof Nodable)
			((Nodable) value).setNode(this);
	}
	
	public E getValue() {
		return value;
	}
	
	public void setValue(E value) {
		if(this.value instanceof Nodable)
			((Nodable) this.value).setNode(null);
		this.value = value;
		if(value instanceof Nodable)
			((Nodable) value).setNode(this);
	}
	
	@Override
	public String getName() {
		return value.toString();
	}
}
