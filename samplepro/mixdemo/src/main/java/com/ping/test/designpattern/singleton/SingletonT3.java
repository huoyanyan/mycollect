package com.ping.test.designpattern.singleton;

/**
 * 双重检测同步延迟加载
 * 
 * @author zhangfei
 *
 */
public class SingletonT3 {
	private volatile static SingletonT3 instance = null;

	private SingletonT3() {
	}

	public static SingletonT3 getInstance() {
		if (instance == null) {
			synchronized (SingletonT3.class) {// 1
				if (instance == null) {// 2
					instance = new SingletonT3();// 3
				}
			}
		}
		return instance;
	}
	
	public static void main(String[] args) {
		String str = "轻轻的一声问候，不想惊扰您，只想知道您一切安好！您的健康是我们最大的心愿。如您还有不适，可别大意拖延，以防错过最佳治疗时机，快快打开「七乐康用户端」公众号找医生聊聊近况吧~~~";
		System.out.println(str.length());
	}
	
}

//轻轻的一声问候，不想惊扰，只想知道您一切安好！您的健康是我们最大的心愿。如您还有不适，可别大意拖延，以防错过最佳治疗时机，快快打开「七乐康用户端」公众号找医生聊聊近况吧~~~
//轻轻的一声问候，不想惊扰您，只想知道您一切安好！您的健康是我们最大的心愿。如您还有不适，可别大意拖延，以防错过最佳治疗时机，快快打开「七乐康用户端」公众号找医生聊聊近况吧~~~