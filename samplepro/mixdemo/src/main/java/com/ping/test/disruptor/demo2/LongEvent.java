package com.ping.test.disruptor.demo2;

/**
 * 定义的消息格式
 * 
 * @author zhangfei
 *
 */
public class LongEvent {
	private long value;

	public void set(long value) {
		this.value = value;
	}

	public long getValue() {
		return value;
	}
}
