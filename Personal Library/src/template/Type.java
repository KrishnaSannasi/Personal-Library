package template;

public interface Type<U extends Template<?>> {
	U toTemplate();
}
