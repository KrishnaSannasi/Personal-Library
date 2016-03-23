package node;

public class NamedNode<V> extends ValueNode<V , String> {
	@SafeVarargs
	public NamedNode(String name , Node<V>... connections) {
		super(name , connections);
	}
	
	public void setName(String name) {
		setValue(name);
	}
}
