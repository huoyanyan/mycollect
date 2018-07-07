package org.aspectj.demo1.test3;

import org.aspectj.demo1.Waiter;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestAspecctUnit {

	@Test
	public void pointAspectJTest3() {
		String configPath = "beans.xml";
		ApplicationContext ctx = new ClassPathXmlApplicationContext(configPath);
		Waiter naiveWaiter = (Waiter) ctx.getBean("naiveWaiter");
		naiveWaiter.greetTo("Andy");
	}
}
