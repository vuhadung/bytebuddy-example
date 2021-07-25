package edu.stevens.project.bytebuddy.example;

import static org.junit.Assert.assertNotEquals;

public class MyClassLoader extends ClassLoader {

	public MyClassLoader(ClassLoader parent) {
		super(parent);
	}

	public static void load(ClassLoader classLoader) throws ClassNotFoundException {
		classLoader = new MyClassLoader(classLoader);
		System.out.println("New custom ClassLoader is: " + classLoader);
		Class bar = Class.forName("edu.stevens.project.bytebuddy.example.Bar", false, classLoader);
//		Firstly, MyClassLoader will ask its parent, i.e, AppClassLoader to find class Bar
//		AppClassLoader found Bar in the classpath and load it
//		MyClassLoader will only load Bar if its parent can not found Bar
		System.out.println("Classloader of Bar is: " + bar.getClassLoader());
		assertNotEquals(bar.getClassLoader(), classLoader);
	}
}
