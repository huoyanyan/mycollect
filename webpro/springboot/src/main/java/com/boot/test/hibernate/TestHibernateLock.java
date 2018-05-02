package com.boot.test.hibernate;

import java.util.Timer;

public class TestHibernateLock {
	public static void main(String[] args) {
		new TestHibernateLock().testOptLocking();
	}

	private void testOptLocking() {
		Timer timer1 = new Timer();
		timer1.schedule(new TransactionC(), 0);
	}
}
