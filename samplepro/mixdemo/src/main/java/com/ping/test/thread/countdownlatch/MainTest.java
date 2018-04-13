package com.ping.test.thread.countdownlatch;

/**
 * 这个例子中，我模拟了一个应用程序启动类，它开始时启动了n个线程类，这些线程将检查外部系统并通知闭锁，并且启动类一直在闭锁上等待着。
 * 一旦验证和检查了所有外部服务，那么启动类恢复执行
 * 
 * @author zhangfei
 *
 */
public class MainTest {

	public static void main(String[] args) {
		boolean result = false;
		try {
			result = ApplicationStartupUtil.checkExternalServices();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("External services validation completed !! Result was :: " + result);
	}

}
