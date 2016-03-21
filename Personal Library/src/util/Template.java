package util;

public interface Template<T extends Type<?>> {
	T create();
	
	Template<T> clone();
}
