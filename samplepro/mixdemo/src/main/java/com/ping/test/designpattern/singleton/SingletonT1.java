package com.ping.test.designpattern.singleton;
/**
 * 非延迟加载单例类
 * @author zhangfei
 *
 */
public class SingletonT1 {
	private SingletonT1() {
	}

	private static final SingletonT1 instance = new SingletonT1();

	public static SingletonT1 getInstance() {
		return instance;
	}
}
