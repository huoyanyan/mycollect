package org.aspectj.demo1.test;

import org.aspectj.demo1.Waiter;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@Aspect
public class TestAspect {
	// 与非运算
	@Before("!target(com.aspectj.demo1.NaiveWaiter) && execution(* serveTo(..))")
	public void notServeInNaiveWaiter() {
		System.out.println("--notServeInNaiveWaiter() executed!--");
	}

	// 与运算
	@After("within(com.aspectj.demo1.*) && execution(* greetTo(..))")
	public void greetToFun() {
		System.out.println("--greetToFun() executed!--");
	}

	// 或运算
	@AfterReturning("target(com.aspectj.demo1.Waiter) || target(com.aspectj.demo1.Seller)")
	public void waiterOrSeller() {
		System.out.println("--waiterOrSeller() executed!--");
	}
	
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
