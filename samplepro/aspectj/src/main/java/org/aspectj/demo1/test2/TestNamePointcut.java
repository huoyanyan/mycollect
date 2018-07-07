package org.aspectj.demo1.test2;

import org.aspectj.lang.annotation.Pointcut;
public class TestNamePointcut {
    //通过注解方法inPackage()对该切点进行命名，方法可视域修饰符为private，表明该命名切点只能在本切面类中使用
    @Pointcut("within(org.aspectj.demo1.*)")
    private void inPackage(){}
    @Pointcut("execution(* greetTo(..))")
    protected void greetTo(){}
    @Pointcut("inPackage() and greetTo()")
    public void inPkgGreetTo(){}
}
