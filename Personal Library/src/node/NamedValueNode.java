package node;

public class NamedValueNode<V,E> extends ValueNode<V,E> {
	public String name;
	
	@SafeVarargs
	public NamedValueNode(String name , Node<V>... connections) {
		super(connections);
	}
	
	@SafeVarargs
	public NamedValueNode(String name , E value , Node<V>... connections) {
		super(value , connections);
		this.name = name;
	}
}
