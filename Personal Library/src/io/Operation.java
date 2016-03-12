package io;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public final class Operation {
	@SafeVarargs
	public static <T> T[] addTo(T[] array , T... addTo) {
		if(array.getClass() != addTo.getClass())
			return null;
		LinkedList<T> list = new LinkedList<>();
		for(T t: array) {
			list.add(t);
		}
		for(T t: addTo) {
			list.add(t);
		}
		return list.toArray(array);
	}
	
	public static void none(Object toOprtateOn) {
	}
	
	public static void print(Object toOprtateOn) {
		System.out.println(toOprtateOn);
	}
	
	@SuppressWarnings("unchecked")
	public static <T> void detailedPrint(Class<T> toOprtateOn) {
		System.out.println("Loaded" + toOprtateOn);
		Class<? super T> last = null , current;
		for(Field field: sortFields(toOprtateOn)) {
			current = (Class<? super T>) field.getDeclaringClass();
			if(current != last) {
				System.out.println("\tFROM: " + current);
				last = current;
			}
			System.out.println("\t\t" + field);
		}
		System.out.println();
		last = current = null;
		for(Method method: sortMethods(toOprtateOn)) {
			current = (Class<? super T>) method.getDeclaringClass();
			if(current != last) {
				System.out.println("\tFROM: " + current);
				last = current;
			}
			System.out.println("\t\t" + method);
		}
		System.out.println();
	}
	
	private static Field[] sort(Field[] fields) {
		for(int i = fields.length - 1; i >= 0; i--) {
			for(int j = i - 1; j >= 0; j--) {
				if(!compare(fields[j] , fields[i])) {
					Field field = fields[j];
					fields[j] = fields[i];
					fields[i] = field;
				}
			}
		}
		return fields;
	}
	
	private static Field[] sortFields(Class<?> clazz) {
		List<Field> toReturn = new LinkedList<>();
		List<Class<?>> superClasses = new LinkedList<>();
		while(clazz.getSuperclass() != Object.class) {
			superClasses.add(clazz = clazz.getSuperclass());
		}
		superClasses.add(Object.class);
		for(Class<?> superClass: superClasses) {
			toReturn.addAll(Arrays.asList(sort(superClass.getDeclaredFields())));
		}
		return toReturn.toArray(new Field[toReturn.size()]);
	}
	
	private static Method[] sort(Method[] methods) {
		for(int i = methods.length - 1; i >= 0; i--) {
			for(int j = i - 1; j > 0; j--) {
				if(!compare(methods[j] , methods[j - 1])) {
					Method method = methods[j];
					methods[j] = methods[j - 1];
					methods[j - 1] = method;
				}
			}
		}
		return methods;
	}
	
	private static Method[] sortMethods(Class<?> clazz) {
		List<Method> toReturn = new LinkedList<>();
		List<Class<?>> superClasses = new LinkedList<>();
		while(clazz.getSuperclass() != Object.class) {
			superClasses.add(clazz = clazz.getSuperclass());
		}
		superClasses.add(Object.class);
		for(Class<?> superClass: superClasses) {
			toReturn.addAll(Arrays.asList(sort(superClass.getDeclaredMethods())));
		}
		return toReturn.toArray(new Method[toReturn.size()]);
	}
	
	private static long value(Field field) {
		int modRaw = field.getModifiers();
		long mod = 0;
		long num = 2;
		if(!field.getType().isPrimitive())
			mod += num;
		num *= 2;
		if(field.getType().isArray())
			mod += num;
		num *= 2;
		if(Modifier.isFinal(modRaw))
			mod += num;
		num *= 2;
		if(Modifier.isPublic(modRaw))
			mod += num;
		num *= 2;
		if(Modifier.isProtected(modRaw))
			mod += num;
		num *= 2;
		if(Modifier.isPrivate(modRaw))
			mod += num;
		num *= 2;
		if(Modifier.isStatic(modRaw))
			mod += num;
		return mod;
	}
	
	private static long value(Method method) {
		int modRaw = method.getModifiers();
		long mod = 0;
		long num = 2;
		num *= 2;
		if(Modifier.isFinal(modRaw))
			mod += num;
		num *= 2;
		if(Modifier.isPublic(modRaw))
			mod += num;
		num *= 2;
		if(Modifier.isProtected(modRaw))
			mod += num;
		num *= 2;
		if(Modifier.isPrivate(modRaw))
			mod += num;
		num *= 2;
		if(!Modifier.isNative(modRaw))
			mod += num;
		num *= 2;
		if(Modifier.isStatic(modRaw))
			mod += num;
		return mod + value(method.getReturnType()) / 16;
	}
	
	private static long value(Class<?> clazz) {
		int modRaw = clazz.getModifiers();
		long mod = 0;
		long num = 2;
		if(clazz.isPrimitive())
			mod += num;
		num *= 2;
		if(Modifier.isFinal(modRaw))
			mod += num;
		num *= 2;
		if(Modifier.isPublic(modRaw))
			mod += num;
		num *= 2;
		if(Modifier.isProtected(modRaw))
			mod += num;
		num *= 2;
		if(Modifier.isPrivate(modRaw))
			mod += num;
		num *= 2;
		if(Modifier.isStatic(modRaw))
			mod += num;
		return mod;
	}
	
	private static boolean compare(Field field1 , Field field2) {
		long mod1 = value(field1) , mod2 = value(field2);
		if(field1.getName().compareTo(field2.getName()) > 0)
			mod1++;
		else
			mod2++;
		return mod1 > mod2;
	}
	
	private static boolean compare(Method method1 , Method method2) {
		long mod1 = value(method1) , mod2 = value(method2);
		if(method1.getName().compareTo(method2.getName()) > 0)
			mod1++;
		else
			mod2++;
		return mod1 > mod2;
	}
}
