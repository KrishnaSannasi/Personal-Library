package node;

import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

public class Node implements Comparable<Node> {
	public static int nextID = 0;
	
	public final int			id;
	private TreeSet<Node>	connections;
	
	public Node(Node... connections) {
		id = nextID++;
		this.connections = new TreeSet<>();
		addConnections(connections);
	}
	
	public final int getNumberOfConnections() {
		return connections.size();
	}
	
	public final LinkedList<Node> pathTo(Node find) {
		return pathTo(find , new LinkedList<>());
	}
	
	public final LinkedList<Node> pathTo(Node find , LinkedList<Node> path) {
		LinkedList<LinkedList<Node>> allPaths = new LinkedList<>();
		LinkedList<Node> temp , arg;
		
		if(path.contains(this))
			return null;
			
		path.add(this);
		
		if(this == find)
			return path;
			
		for(Node node: connections) {
			arg = new LinkedList<>();
			arg.addAll(path);
			temp = node.pathTo(find , arg);
			if(temp != null)
				allPaths.add(temp);
		}
		
		LinkedList<Node> min = null;
		
		for(LinkedList<Node> tempPath: allPaths) {
			if(tempPath.contains(find) && (min == null || min.size() > tempPath.size()))
				min = tempPath;
		}
		
		return min;
	}
	
	public final void addConnection(Node connection) {
		if(connection != this && !connections.contains(connection)) {
			connections.add(connection);
			connection.connections.add(this);
		}
	}
	
	public final void removeConnection(Node connection) {
		if(connection != this && connections.contains(connection)) {
			connections.remove(connection);
			connection.connections.remove(this);
		}
	}
	
	public final void addConnections(Node... connections) {
		for(Node connection: connections) {
			addConnection(connection);
		}
	}
	
	public final void removeConnections(Node... connections) {
		for(Node connection: connections) {
			removeConnection(connection);
		}
	}
	
	public final void clearConnections() {
		for(Node connection: connections) {
			connection.removeConnection(this);
		}
	}
	
	public final Map<Node , Integer> getAllConnections() {
		TreeMap<Node , Integer> deepStorage = new TreeMap<>();
		TreeSet<Node> uncheckedCurrent = new TreeSet<>() , uncheckedTemp = new TreeSet<>();
		int depth = 1;
		deepStorage.put(this , 0);
		for(Node node: this.connections) {
			deepStorage.put(node , depth);
			uncheckedCurrent.add(node);
		}
		
		do {
			++depth;
			for(Node node: uncheckedCurrent) {
				for(Node conn: node.connections) {
					if(!deepStorage.containsKey(conn)) {
						deepStorage.put(conn , depth);
						uncheckedTemp.add(conn);
					}
				}
			}
			
			TreeSet<Node> temp = uncheckedTemp;
			uncheckedTemp = uncheckedCurrent;
			uncheckedCurrent = temp;
			
			uncheckedTemp.clear();
		} while(uncheckedCurrent.size() != 0);
		
		return deepStorage;
	}
	
	@Override
	public int compareTo(Node link) {
		return (int) Math.signum(id - link.id);
	}
	
	@Override
	public String toString() {
		int buffer = (int) Math.ceil(Math.log10(nextID));
		StringBuffer connections = new StringBuffer();
		connections.append("[");
		for(Node connection: this.connections) {
			connections.append(connection.id).append(", ");
		}
		if(this.connections.size() != 0)
			connections.delete(connections.length() - 2 , connections.length());
		connections.append(']');
		return String.format("%" + buffer + "d->%s" , id , connections.toString());
//		return "" + id;
	}
}
