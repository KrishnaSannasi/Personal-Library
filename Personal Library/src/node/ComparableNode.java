package node;

public abstract class ComparableNode<V , E> extends ValueNode<V , E> {
	public static boolean doAsending = true;
	
	public abstract int getComparableValue();
	
	@Override
	public final int compareTo(Node<V> compare) {
		try {
			@SuppressWarnings("unchecked")
			ComparableNode<V , E> o = (ComparableNode<V , E>) compare;
			if(doAsending)
				return getComparableValue() - o.getComparableValue();
			else
				return o.getComparableValue() - getComparableValue();
		}
		catch(Exception e) {
			return super.compareTo(compare);
		}
	}
}
