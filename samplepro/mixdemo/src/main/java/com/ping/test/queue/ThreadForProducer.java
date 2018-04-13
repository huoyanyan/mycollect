package com.ping.test.queue;

import java.util.concurrent.BlockingQueue;

public class ThreadForProducer extends Thread {

	private BlockingQueue<String> blockingQueue;

	public ThreadForProducer(BlockingQueue<String> blockingQueue) {
		this.blockingQueue = blockingQueue;
	}

	@Override
	public void run() {
		try {
			for (int i = 0; i <= 1000000; i++) {
				blockingQueue.put(i + "");
			}
			System.out.println("put orver");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}