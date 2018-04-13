package com.ping.test.disruptor.demo2;

import com.lmax.disruptor.EventHandler;

/**
 * 一个消息消费者 Created by teeyoung on 17/10/27.
 */
public class LongEventHandler implements EventHandler<LongEvent> {
	public void onEvent(LongEvent event, long sequence, boolean endOfBatch) {
		System.out.println("Event: " + event.getValue());
	}
}
