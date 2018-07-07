package org.aspectj.demo1.test4;

import org.aspectj.demo1.NaiveWaiter;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestAspecctUnit {

	@Test
	public void pointAspectJTest4() {
		String configPath = "beans.xml";
		ApplicationContext ctx = new ClassPathXmlApplicationContext(configPath);
		NaiveWaiter naiveWaiter = (NaiveWaiter) ctx.getBean("naiveWaiter");
		naiveWaiter.smile("Andy", 3);
	}
}
