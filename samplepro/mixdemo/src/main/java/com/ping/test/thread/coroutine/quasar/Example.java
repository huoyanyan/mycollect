package com.ping.test.thread.coroutine.quasar;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;

import co.paralleluniverse.fibers.Fiber;
import co.paralleluniverse.fibers.SuspendExecution;
import co.paralleluniverse.strands.Strand;
import co.paralleluniverse.strands.channels.Channel;
import co.paralleluniverse.strands.channels.Channels;

/**
 * https://blog.csdn.net/zdy0_2004/article/details/51323583 协程
 * 
 * @author zhangfei
 *
 */
public class Example {
	private static void printer(Channel<Integer> in) throws SuspendExecution, InterruptedException {
		Integer v;
		while ((v = in.receive()) != null) {
			System.out.println(v);
		}
	}

	/**
	 * 实现一个对于10以内自然数分别求平方的例子
	 * 
	 * @throws InterruptedException
	 * @throws SuspendExecution
	 */
	public static void test1() throws SuspendExecution, InterruptedException {
		// 定义两个Channel
		Channel<Integer> naturals = Channels.newChannel(-1);
		Channel<Integer> squares = Channels.newChannel(-1);

		// 运行两个Fiber实现.
		new Fiber(() -> {
			for (int i = 0; i < 10; i++)
				naturals.send(i);
			naturals.close();
		}).start();

		new Fiber(() -> {
			Integer v;
			while ((v = naturals.receive()) != null)
				squares.send(v * v);
			squares.close();
		}).start();

		printer(squares);
	}

	/**
	 * 通过Quasar建立百万个Fiber
	 * 
	 * @throws SuspendExecution
	 * @throws InterruptedException
	 */
	public static void test2() throws SuspendExecution, InterruptedException {
		int FiberNumber = 1_000_000;
		CountDownLatch latch = new CountDownLatch(1);
		AtomicInteger counter = new AtomicInteger(0);

		for (int i = 0; i < FiberNumber; i++) {
			new Fiber(() -> {
				counter.incrementAndGet();
				if (counter.get() == FiberNumber) {
					System.out.println("done");
				}
				Strand.sleep(1000000);
			}).start();
		}
		latch.await();
	}

	public static void main(String[] args) throws ExecutionException, InterruptedException, SuspendExecution {
		test2();
	}
}
