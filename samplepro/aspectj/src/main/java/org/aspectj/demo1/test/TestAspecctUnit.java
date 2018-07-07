package org.aspectj.demo1.test;

import org.aspectj.demo1.Waiter;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestAspecctUnit {
	@Test
	public void pointAspectJTest() {
		String configPath = "beans.xml";
		ApplicationContext ctx = new ClassPathXmlApplicationContext(configPath);
		Waiter naiveWaiter = (Waiter) ctx.getBean("naiveWaiter");
		Waiter naughtyWaiter = (Waiter) ctx.getBean("naughtyWaiter");
		naiveWaiter.greetTo("John");
		naiveWaiter.serveTo("John");
		naughtyWaiter.greetTo("Tom");
		naughtyWaiter.serveTo("Tom");
	}

}
