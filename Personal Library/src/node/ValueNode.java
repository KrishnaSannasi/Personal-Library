package node;

public class ValueNode<E> extends Node {
	private E value;
	
	@SafeVarargs
	public ValueNode(Node... connections) {
		super(connections);
	}
	
	public E getValue() {
		return value;
	}
	
	public void setValue(E value) {
		this.value = value;
	}
}
