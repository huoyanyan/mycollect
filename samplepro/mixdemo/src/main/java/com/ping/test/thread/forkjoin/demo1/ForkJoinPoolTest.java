package com.ping.test.thread.forkjoin.demo1;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;

public class ForkJoinPoolTest {

	public static void main(String[] args) throws Exception {
//		test1UseInvoke();
		
		test2UseSubmit();
	}

	public static void test1UseInvoke() {
		ForkJoinPool forkJoinPool = new ForkJoinPool(4);

		// 计算 10 亿项，分割任务的临界值为 1 千万
		PiEstimateTask task = new PiEstimateTask(0, 1_000_000_000, 10_000_000);

		double pi = forkJoinPool.invoke(task); // 阻塞，直到任务执行完毕返回结果

		System.out.println("π 的值：" + pi);

		forkJoinPool.shutdown(); // 向线程池发送关闭的指令
	}

	/**
	 * 使用 submit 方法异步的执行任务（此处 submit 方法返回的 future 指向的对象即提交任务时的 task）
	 * 
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public static void test2UseSubmit() throws InterruptedException, ExecutionException {
		ForkJoinPool forkJoinPool = new ForkJoinPool(4);

		PiEstimateTask task = new PiEstimateTask(0, 1_000_000_000, 10_000_000);
		Future<Double> future = forkJoinPool.submit(task); // 不阻塞

		double pi = future.get();
		System.out.println("π 的值：" + pi);
		System.out.println("future 指向的对象是 task 吗：" + (future == task));

		forkJoinPool.shutdown(); // 向线程池发送关闭的指令
	}
}
