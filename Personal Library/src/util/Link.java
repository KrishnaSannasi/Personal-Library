package util;

import java.util.Collection;
import java.util.LinkedList;

public class Link<T> {
	public Link<T>	parent;
	public T			value;
	
	public Link() {
	}
	
	public Link(T value) {
		this.value = value;
	}
	
	public Collection<T> getPath() {
		LinkedList<T> links = new LinkedList<>();
		Link<T> link = this;
		do {
			links.add(link.value);
			link = link.parent;
		} while(link != null);
		return links;
	}
}
