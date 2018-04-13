package com.ping.test.queue;

import java.util.concurrent.BlockingQueue;

public class ThreadForConsumer extends Thread {

	private BlockingQueue<String> blockingQueue;

	public ThreadForConsumer(BlockingQueue<String> blockingQueue) {
		this.blockingQueue = blockingQueue;
	}

	@Override
	public void run() {
		String msg;
		try {
			while (true) {
				msg = blockingQueue.take();
				if (msg == null) {
					System.out.println("nodata");
					Thread.sleep(1);
				} else {
					// 消费
					System.out.println(msg + "over");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}