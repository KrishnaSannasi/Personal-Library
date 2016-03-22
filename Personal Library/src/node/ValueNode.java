package node;

public class ValueNode<E> extends Node {
	private E value;
	
	@SafeVarargs
	public ValueNode(ValueNode<E>... connections) {
		super(connections);
	}
	
	public E getValue() {
		return value;
	}
	
	public void setValue(E value) {
		this.value = value;
	}
}
