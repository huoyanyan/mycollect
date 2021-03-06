package org.aspectj.demo1.test5;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestAspecctUnit {
	@Test
	public void pointAspectJTest5() {
		String configPath = "beans5.xml";
		ApplicationContext ctx = new ClassPathXmlApplicationContext(configPath);
		Waiter waiter = (Waiter) ctx.getBean("naiveWaiter");
		waiter.greetTo("Yang");
	}
}
