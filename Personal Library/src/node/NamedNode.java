package node;

public class NamedNode extends ValueNode<String> {
	@SafeVarargs
	public NamedNode(String name , Node... connections) {
		super(name , connections);
	}
	
	public void setName(String name) {
		setValue(name);
	}
}
