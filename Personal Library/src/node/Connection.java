package node;

public class Connection<E> {
	public final Node<E> node1 , node2;
	
	public E value;
	
	public Connection(Node<E> node1 , Node<E> node2) {
		this.node1 = node1;
		this.node2 = node2;
	}
	
	public final Node<E> getOther(Node<E> node) {
		if(node == node1)
			return node2;
		if(node == node2)
			return node1;
		return null;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Node) {
			return node1 == obj || node2 == obj;
		}
		else if(obj instanceof Connection) {
			return obj.equals(node1) && obj.equals(node2);
		}
		return super.equals(obj);
	}
}
