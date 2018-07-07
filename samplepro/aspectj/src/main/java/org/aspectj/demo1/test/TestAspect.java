package org.aspectj.demo1.test;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
@Component
@Aspect
public class TestAspect {
	// 与非运算
	@Before("!target(org.aspectj.demo1.test.NaiveWaiter) && execution(* serveTo(..))")
	public void notServeInNaiveWaiter() {
		System.out.println("--notServeInNaiveWaiter() executed!--");
	}

	// 与运算
	@After("within(org.aspectj.demo1.test.*) && execution(* greetTo(..))")
	public void greetToFun() {
		System.out.println("--greetToFun() executed!--");
	}

	// 或运算
	@AfterReturning("target(org.aspectj.demo1.test.Waiter) || target(org.aspectj.demo1.test.Seller)")
	public void waiterOrSeller() {
		System.out.println("--waiterOrSeller() executed!--");
	}
}
