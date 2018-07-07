package org.aspectj.demo1.test5;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class TestAspect5 {
	@Before("this(waiter)")
    public void bindProxyObj(Waiter waiter){
        System.out.println("---bindProxyObj---");
        System.out.println(waiter.getClass().getName());
        System.out.println("---bindProxyObj---");
    }
}
