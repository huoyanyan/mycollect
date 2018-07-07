package org.aspectj.demo1.test2;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestAspecctUnit {

	@Test
    public void pointAspectJTest2() {
        String configPath = "beans2.xml";
        ApplicationContext ctx = new ClassPathXmlApplicationContext(configPath);
        NaiveWaiter naiveWaiter = (NaiveWaiter) ctx.getBean("naiveWaiter");
        naiveWaiter.smile("Andy", 2);
    }
}
