package node;

public class NamedValueNode<E> extends ValueNode<E> {
	public String name;
	
	@SafeVarargs
	public NamedValueNode(String name , Node... connections) {
		super(connections);
	}
	
	@SafeVarargs
	public NamedValueNode(String name , E value , Node... connections) {
		super(value , connections);
		this.name = name;
	}
}
