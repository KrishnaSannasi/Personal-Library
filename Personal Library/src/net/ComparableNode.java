package net;

import net.node.NodeSimple;
import net.node.NodeValue;

public abstract class ComparableNode<V , E> extends NodeValue<V , E> {
	public static boolean doAsending = true;
	
	public abstract int getComparableValue();
	
	@Override
	public final int compareTo(NodeSimple<V> compare) {
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
