package util;

public interface Type<U extends Template<?>> {
	U toTemplate();
}
