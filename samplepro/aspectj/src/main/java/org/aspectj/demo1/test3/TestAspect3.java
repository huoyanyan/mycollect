package org.aspectj.demo1.test3;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class TestAspect3 {
	@Around("execution(* greetTo(..)) && target(org.aspectj.demo1.test3.NaiveWaiter)")
	public void joinPointAccess(ProceedingJoinPoint pjp) throws Throwable {
		System.out.println("---joinPointAccess---");
		System.out.println("args[0]:" + pjp.getArgs()[0]);
		System.out.println("signature:" + pjp.getTarget().getClass());
		pjp.proceed();
		System.out.println("---joinPointAccess---");
	}

}
