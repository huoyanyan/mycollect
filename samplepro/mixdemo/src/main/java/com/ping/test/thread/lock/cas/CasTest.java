package com.ping.test.thread.lock.cas;

import java.util.concurrent.atomic.AtomicLong;

public class CasTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		System.out.println("START -- ");
		calc();
		calcSynchro();
		calcAtomic();

		testThreadsSync();
		testThreadsAtomic();

		testThreadsSync2();
		testThreadsAtomic2();

		System.out.println("-- FINISHED ");
	}

	private static void calc() {
		Stopwatch sw = new Stopwatch();
		sw.start();

		long val = 0;
		while (val < 10000000L) {
			val++;
		}
		sw.stop();
		long milSecds = sw.getElapsedTime();

		System.out.println(" calc() elapsed (ms): " + milSecds);
	}

	private static void calcSynchro() {
		Stopwatch sw = new Stopwatch();
		sw.start();

		long val = 0;

		while (val < 10000000L) {
			synchronized (CasTest.class) {
				val++;
			}
		}

		sw.stop();
		long milSecds = sw.getElapsedTime();

		System.out.println(" calcSynchro() elapsed (ms): " + milSecds);
	}

	private static void calcAtomic() {
		Stopwatch sw = new Stopwatch();
		sw.start();

		AtomicLong val = new AtomicLong(0);
		while (val.incrementAndGet() < 10000000L) {

		}
		sw.stop();
		long milSecds = sw.getElapsedTime();

		System.out.println(" calcAtomic() elapsed (ms): " + milSecds);

	}

	private static void testThreadsSync() {

		Stopwatch sw = new Stopwatch();
		sw.start();

		Thread t1 = new Thread(new LoopSync());
		t1.start();

		Thread t2 = new Thread(new LoopSync());
		t2.start();

		while (t1.isAlive() || t2.isAlive()) {

		}

		sw.stop();
		long milSecds = sw.getElapsedTime();

		System.out.println(" testThreadsSync() 1 thread elapsed (ms): " + milSecds);

	}

	private static void testThreadsAtomic() {

		Stopwatch sw = new Stopwatch();
		sw.start();

		Thread t1 = new Thread(new LoopAtomic());
		t1.start();

		Thread t2 = new Thread(new LoopAtomic());
		t2.start();

		while (t1.isAlive() || t2.isAlive()) {

		}

		sw.stop();
		long milSecds = sw.getElapsedTime();

		System.out.println(" testThreadsAtomic() 1 thread elapsed (ms): " + milSecds);

	}

	private static void testThreadsSync2() {

		Stopwatch sw = new Stopwatch();
		sw.start();

		Thread t1 = new Thread(new LoopSync());
		t1.start();

		Thread t2 = new Thread(new LoopSync());
		t2.start();

		while (t1.isAlive() || t2.isAlive()) {

		}

		sw.stop();
		long milSecds = sw.getElapsedTime();

		System.out.println(" testThreadsSync() 2 threads elapsed (ms): " + milSecds);

	}

	private static void testThreadsAtomic2() {

		Stopwatch sw = new Stopwatch();
		sw.start();

		Thread t1 = new Thread(new LoopAtomic());
		t1.start();

		Thread t2 = new Thread(new LoopAtomic());
		t2.start();

		while (t1.isAlive() || t2.isAlive()) {

		}

		sw.stop();
		long milSecds = sw.getElapsedTime();

		System.out.println(" testThreadsAtomic() 2 threads elapsed (ms): " + milSecds);

	}

	private static class LoopAtomic implements Runnable {
		public void run() {
			AtomicLong val = new AtomicLong(0);
			while (val.incrementAndGet() < 10000000L) {

			}
		}
	}

	private static class LoopSync implements Runnable {
		public void run() {
			long val = 0;

			while (val < 10000000L) {
				synchronized (CasTest.class) {
					val++;
				}
			}
		}
	}

}
