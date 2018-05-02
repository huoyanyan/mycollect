package com.ping.test.designpattern.singleton;
/**
 * 同步延迟加载
 * @author zhangfei
 *
 */
public class SingletonT2 {
	private static SingletonT2 instance = null;

	private SingletonT2() {
	}

	public static synchronized SingletonT2 getInstance() {
		if (instance == null) {
			instance = new SingletonT2();
		}
		return instance;
	}
}
