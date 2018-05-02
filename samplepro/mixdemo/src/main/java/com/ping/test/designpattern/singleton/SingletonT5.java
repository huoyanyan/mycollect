package com.ping.test.designpattern.singleton;

/**
 * 使用内部类实现延迟加载 ---为了做到真真的延迟加载，双重检测在Java中是行不通的，所以只能借助于另一类的类加载加延迟加载：
 * 
 * @author zhangfei
 *
 */
public class SingletonT5 {
	private SingletonT5() {
	}

	public static class Holder {
		// 这里的私有没有什么意义
		/* private */static SingletonT5 instance = new SingletonT5();
	}

	public static SingletonT5 getInstance() {
		// 外围类能直接访问内部类（不管是否是静态的）的私有变量
		return Holder.instance;
	}
}
