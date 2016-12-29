package net;

import net.connection.Connection;

public class ComparableConnection<E> extends Connection<E>implements Comparable<ComparableConnection<E>> {
	public static boolean doAsending = true;
	
	public double distance;
	
	public ComparableConnection(ComparableNode<E , ?> node1 , ComparableNode<E , ?> node2) {
		super(node1 , node2);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public int compareTo(ComparableConnection<E> o) {
		if(doAsending)
			return (int) (distance - o.distance);
		else
			return (int) (o.distance - distance);
	}
	
	@Override
	public String toString() {
		return node1.id + " " + node2.id + " " + distance;
	}
}
