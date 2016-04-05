package node;

public class ComparableConnection<E> extends Connection<E>implements Comparable<ComparableConnection<E>> {
	public static boolean doAsending = true;
	
	public int distance;
	
	public ComparableConnection(ComparableNode<E , ?> node1 , ComparableNode<E , ?> node2) {
		super(node1 , node2);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public int compareTo(ComparableConnection<E> o) {
		if(doAsending)
			return distance - o.distance;
		else
			return o.distance - distance;
	}
}
