package edu.stevens.project.bytebuddy.example;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.ClassFileLocator;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.pool.TypePool;

public class App {
	public static void main(String[] args) throws Exception {

		ClassLoader loader = ClassLoader.getSystemClassLoader();
		System.out.println("ClassLoader of Main is: " + loader);

		/* either one of these followings will load Dummy to ClassLoader */
//		loader.loadClass("edu.stevens.project.bytebuddy.example.Dummy");
//      Class.forName("edu.stevens.project.bytebuddy.example.Dummy", false, ClassLoader.getSystemClassLoader());
//      use of Dummy.class

		TypePool typePool = TypePool.Default.ofSystemLoader();

		new ByteBuddy()
				.rebase(typePool.describe("edu.stevens.project.bytebuddy.example.Dummy").resolve(),
						ClassFileLocator.ForClassLoader.ofSystemLoader())
				.method(ElementMatchers.named("sayHello")).intercept(FixedValue.value("Hello from Dummy Redefined"))
				.make().load(loader, ClassLoadingStrategy.Default.INJECTION);

		/*
		 * if change to WRAPPER --> class already loaded, but if use
		 * ClassReloadingStrategy --> null pointer exception
		 */

		System.out.println(new Dummy().sayHello());

		// MyClassLoader.load(loader);

	}
}
