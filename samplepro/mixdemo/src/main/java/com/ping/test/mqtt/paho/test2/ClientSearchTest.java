package com.ping.test.mqtt.paho.test2;

import org.eclipse.paho.client.mqttv3.MqttException;

public class ClientSearchTest {

	public static void main(String[] args) {
		runTask();

	}

	public static void runTask() {
		final long timeInterval = 2000;// 两秒运行一次
		Runnable runnable = new Runnable() {
			public void run() {
				while (true) {
					// ------- code for task to run
					try { // 你要运行的程序
						ClientSearch clientSearch = new ClientSearch();
						try {
							clientSearch.start();
						} catch (MqttException e) {
							e.printStackTrace();
						}
						Thread.sleep(1000); // 给一秒时间接收服务器消息
						Integer num = Integer.valueOf(clientSearch.resc());
						System.out.println("当前客户端连接数：" + num);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					// ------- ends here
					try {
						Thread.sleep(timeInterval);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		Thread thread = new Thread(runnable);
		thread.start();
	}
}
