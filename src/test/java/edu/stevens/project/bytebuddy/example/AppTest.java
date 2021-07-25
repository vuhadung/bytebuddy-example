package edu.stevens.project.bytebuddy.example;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.agent.ByteBuddyAgent;
import net.bytebuddy.dynamic.loading.ClassReloadingStrategy;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.matcher.ElementMatchers;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AppTest {

	public static class Mock {
		public String sayHello() {
			return "Hello from Dummy Redefined";
		}
	}

	@Test
	public void Test1_shouldReturnRedefined() {

		// Option 1
		ByteBuddyAgent.install();
		new ByteBuddy().redefine(Dummy.class).method(ElementMatchers.named("sayHello"))
				.intercept(FixedValue.value("Hello from Dummy Redefined")).make()
				.load(Dummy.class.getClassLoader(), ClassReloadingStrategy.fromInstalledAgent());

		// if using ClassLoadingStrategy --> can't inject already loaded type
		assertEquals("Hello from Dummy Redefined", new Dummy().sayHello());

		try {
			ClassReloadingStrategy.fromInstalledAgent().reset(Dummy.class);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Option 2
//		try {
//			Dummy dummy = new ByteBuddy().subclass(Dummy.class).method(named("sayHello"))
//					.intercept(FixedValue.value("Hello from Dummy Redefined")).make().load(getClass().getClassLoader())
//					.getLoaded().newInstance();
//			assertEquals("Hello from Dummy Redefined", dummy.sayHello());
//		} catch (InstantiationException | IllegalAccessException e) {
//			e.printStackTrace();
//		}

		// Option 3 - write a new AgentBuilder?

	}

	@Test
	public void Test2_shouldReturnNormal() {
		Dummy dummy = new Dummy();
		assertEquals("Hello from Dummy", dummy.sayHello());

	}

}
