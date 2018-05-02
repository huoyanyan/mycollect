package com.ping.test.designpattern.singleton;
/**
 * 使用ThreadLocal修复双重检测
 * @author zhangfei
 *
 */
public class SingletonT4 {
	private static final ThreadLocal perThreadInstance = new ThreadLocal();
	private static SingletonT4 singleton;

	private SingletonT4() {
	}

	public static SingletonT4 getInstance() {
		if (perThreadInstance.get() == null) {
			// 每个线程第一次都会调用
			createInstance();
		}
		return singleton;
	}

	private static final void createInstance() {
		synchronized (SingletonT4.class) {
			if (singleton == null) {
				singleton = new SingletonT4();
			}
		}
		perThreadInstance.set(perThreadInstance);
	}
}