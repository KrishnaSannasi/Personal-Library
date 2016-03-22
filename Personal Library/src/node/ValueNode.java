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
	}
	
	public E getValue() {
		return value;
	}
	
	public void setValue(E value) {
		this.value = value;
	}
	
	@Override
	public String getName() {
		return value.toString();
	}
}
