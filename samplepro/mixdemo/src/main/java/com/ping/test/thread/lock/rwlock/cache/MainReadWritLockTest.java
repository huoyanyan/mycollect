package com.ping.test.thread.lock.rwlock.cache;

import java.util.HashMap;
import java.util.Map;

public class MainReadWritLockTest {

	Map<String, Object> cache = new HashMap<String, Object>();

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		final ReadWriteLockTest rwlt = new ReadWriteLockTest();
		for (int i = 0; i < 10; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					Object value = rwlt.get("id");
					System.out.println(Thread.currentThread().getName() + "--" + value);
				}
			}).start();
		}

	}

}
