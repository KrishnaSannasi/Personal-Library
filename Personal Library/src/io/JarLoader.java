package io;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.function.Consumer;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import io.logger.Logger;
import io.logger.LoggerType;

public class JarLoader {
	public static ClassLoader					classLoader;
	private static Enumeration<JarEntry>	entries;
	private static String						path;
	private static JarFile						jarFile;
	
	private static void init(String path) throws IOException {
		Logger.LOGGER.log("LOADING \"" + path + "\"");
		JarLoader.path = path;
		File file = new File(path);
		jarFile = new JarFile(file);
		classLoader = new URLClassLoader(new URL[] {file.toURI().toURL()});
		entries = jarFile.entries();
	}
	
	private static void close() throws IOException {
		Logger.LOGGER.log("DONE LOADING \"" + path + "\"");
		jarFile.close();
	}
	
	@SuppressWarnings({"unchecked"})
	public static <T> void loadJar(String path , String[] classesToLoad , Consumer<Class<T>>... operations) throws IOException , ClassNotFoundException {
		init(path);
		List<String> names = Arrays.asList(classesToLoad);
		while(entries.hasMoreElements()) {
			JarEntry entry = entries.nextElement();
			String name = entry.getName().toString();
			if(names.contains(name)) {
				name = name.replace("/" , ".").replace(".class" , "");
				Class<T> clazz = (Class<T>) classLoader.loadClass(name);
				Arrays.stream(operations).forEach(e -> e.accept(clazz));
			}
		}
		close();
	}
	
	@SafeVarargs
	@SuppressWarnings({"unchecked"})
	public static <T> void loadJar(String path , Class<T> superClass , Consumer<Class<T>>... operations) throws ClassNotFoundException , IOException {
		init(path);
		while(entries.hasMoreElements()) {
			JarEntry entry = entries.nextElement();
			String name = entry.getName().toString();
			if(name.endsWith(".class")) {
				name = name.replace("/" , ".").replace(".class" , "");
				Class<?> clazz = classLoader.loadClass(name);
				try {
					Arrays.stream(operations).forEach(e -> e.accept((Class<T>) clazz));
				}
				catch(ClassCastException e) {
					Logger.LOGGER.log(LoggerType.ERROR , clazz.getName() + " does not subclass " + superClass.getName() + " and was not loaded.");
				}
			}
		}
		close();
	}
	
	@SafeVarargs
	public static void loadJar(String path , Consumer<Class<Object>>... operations) throws ClassNotFoundException , IOException {
		loadJar(path , Object.class , operations);
	}
	
	public static void loadJar(String path) throws ClassNotFoundException , IOException {
		loadJar(path , Object.class , Operation::none);
	}
	
	public static void loadJar(String path , Class<?> superClass) throws ClassNotFoundException , IOException {
		loadJar(path , superClass , Operation::none);
	}
	
	public static void loadJars(String[] paths) throws ClassNotFoundException , IOException {
		for(String path: paths) {
			loadJar(path , Object.class , Operation::none);
		}
	}
	
	public static void loadJars(String[] paths , Class<?> superClass) throws ClassNotFoundException , IOException {
		for(String path: paths) {
			loadJar(path , superClass , Operation::none);
		}
	}
	
	@SafeVarargs
	public static void loadJars(String[] paths , Consumer<Class<Object>>... operations) throws ClassNotFoundException , IOException {
		for(String path: paths) {
			loadJar(path , Object.class , operations);
		}
	}
	
	@SafeVarargs
	public static <T> void loadJars(String[] paths , Class<T> superClass , Consumer<Class<T>>... operations) throws ClassNotFoundException , IOException {
		for(String path: paths) {
			loadJar(path , superClass , operations);
		}
	}
	
	public static <T> Class<T> forceInit(Class<T> clazz) {
		try {
			Class.forName(clazz.getName() , true , clazz.getClassLoader());
		}
		catch(ClassNotFoundException e) {
			throw new AssertionError(e);
		}
		return clazz;
	}
}
