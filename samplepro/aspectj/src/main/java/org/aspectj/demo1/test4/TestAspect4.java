package org.aspectj.demo1.test4;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class TestAspect4 {
	@Before("target(org.aspectj.demo1.NaiveWaiter) && args(name,num,..)")
	public void bindJoinPointParams(String name, int num) {
		System.out.println("---bindJoinPointParams---");
		System.out.println("name:" + name);
		System.out.println("num:" + num);
		System.out.println("---bindJoinPointParams---");
	}
}
