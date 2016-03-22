package node;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Represents a node in a network of connections
 * 
 * @author Krishna
 * 			
 */
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
	
	public static LinkedList<Collection<Node>> getGroups(Node... nodes) {
		LinkedList<Node> nonprintedNodes = new LinkedList<>();
		LinkedList<Collection<Node>> groups = new LinkedList<>();
		
		for(Node node: nodes) {
			nonprintedNodes.add(node);
		}
		Collection<Node> all;
		for(int i = 0; i < nonprintedNodes.size();) {
			all = nonprintedNodes.get(i++).getAllConnections().keySet();
			groups.add(all);
			StringBuffer connections = new StringBuffer();
			connections.delete(0 , connections.length());
			connections.append(all.size()).append(" [");
			for(Node connection: all) {
				connections.append(connection.getName()).append(", ");
			}
			connections.delete(connections.length() - 2 , connections.length());
			connections.append(']');
			nonprintedNodes.removeAll(all);
			if(all.size() != 0) {
				i = 0;
			}
		}
		return groups;
	}
	
	public static LinkedList<Node> pathTo(Node start , Node find , Node... waypoints) {
		LinkedList<Node> path = new LinkedList<>() , temp;
		Node current = start;
		for(Node node: waypoints) {
			temp = current.pathTo(node);
			if(temp == null)
				return null;
			path.addAll(temp);
			current = node;
		}
		temp = current.pathTo(find);
		if(temp == null)
			return null;
		path.addAll(temp);
		return path;
	}
	
	/**
	 * Returns the path via connections to a desired node
	 * 
	 * @return null if there is no path and a list of nodes that is the path to
	 *         the node
	 */
	public final LinkedList<Node> pathTo(Node find) {
		return pathTo(find , new LinkedList<>());
	}
	
	private LinkedList<Node> pathTo(Node find , LinkedList<Node> path) {
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
	
	/**
	 * Returns a map of all the nodes that this node can access indirectly via
	 * connections to other nodes and how many connections away this node is from
	 * this node. The key is the node and the value is how many connections away
	 * the node is.
	 */
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
	
	/**
	 * Returns the name of this node
	 */
	public String getName() {
		return "Node " + id;
	}
	
	/**
	 * Compares the id of the nodes for easy sorting using TreeSet and TreeMap
	 * 
	 */
	@Override
	public int compareTo(Node link) {
		return (int) Math.signum(id - link.id);
	}
	
	@Override
	public final String toString() {
		StringBuffer connections = new StringBuffer();
		connections.append("[");
		for(Node connection: this.connections) {
			connections.append(connection.getName()).append(", ");
		}
		if(this.connections.size() != 0)
			connections.delete(connections.length() - 2 , connections.length());
		connections.append(']');
		return String.format("%s->%s" , getName() , connections.toString());
//		return "" + id;
	}
}
