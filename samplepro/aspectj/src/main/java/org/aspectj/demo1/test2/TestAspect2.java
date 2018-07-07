package org.aspectj.demo1.test2;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class TestAspect2 {
    @Before("TestNamePointcut.inPkgGreetTo()")
    public void pkgGreetTo(){
        System.out.println("--pkgGreetTo() executed!--");
    }
    @Before("target(org.aspectj.demo1.NaiveWaiter) || TestNamePointcut.inPkgGreetTo()")
    public void pkgGreetToNotnaiveWaiter(){
        System.out.println("--pkgGreetToNotnaiveWaiter() executed!--");
    }
    
}
