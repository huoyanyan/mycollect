package com.ping.test.thread.pool;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/*
 * 一、线程池：提供了一个线程队列，队列中保存着所有等待状态的线程。避免了创建与销毁额外开销，提高了响应的速度。
 * 
 * 二、线程池的体系结构：
 *     java.util.concurrent.Executor : 负责线程的使用与调度的根接口
 *         |--**ExecutorService 子接口: 线程池的主要接口
 *             |--ThreadPoolExecutor 线程池的实现类
 *             |--ScheduledExecutorService 子接口：负责线程的调度
 *                 |--ScheduledThreadPoolExecutor ：继承 ThreadPoolExecutor， 实现 ScheduledExecutorService
 * 
 * 三、工具类 : Executors 
 * ExecutorService newFixedThreadPool() : 创建固定大小的线程池
 * ExecutorService newCachedThreadPool() : 缓存线程池，线程池的数量不固定，可以根据需求自动的更改数量。
 * ExecutorService newSingleThreadExecutor() : 创建单个线程池。线程池中只有一个线程
 * 
 * ScheduledExecutorService newScheduledThreadPool() : 创建固定大小的线程，可以延迟或定时的执行任务。
 * https://www.cnblogs.com/zjfjava/p/8505606.html
 */
public class ThreadPoolTest {

	public static void main(String[] args) throws Exception {
		// testFixedPool();
		testFixedPoolWithFuture();
	}

	/**
	 * 创建包含5个线程的线程池，对变量进行增加操作
	 */
	public static void testFixedPool() {
		// 1. 创建线程池
		ExecutorService pool = Executors.newFixedThreadPool(5);

		ThreadPoolDemo tpd = new ThreadPoolDemo();

		// 2. 为线程池中的线程分配任务，>5，可将线程池里的五个线程都给调用
		for (int i = 0; i < 10; i++) {
			pool.submit(tpd);
		}

		// 3. 关闭线程池
		pool.shutdown();
	}

	/**
	 * 线程池结合Callable和Future创建线程
	 * 
	 * @throws ExecutionException
	 * @throws InterruptedException
	 */
	public static void testFixedPoolWithFuture() throws InterruptedException, ExecutionException {
		// 1. 创建线程池
		ExecutorService pool = Executors.newFixedThreadPool(5);

		List<Future<Integer>> list = new ArrayList<>();

		for (int i = 0; i < 10; i++) {
			// Future对象用于接收Callable线程的返回值
			Future<Integer> future = pool.submit(new Callable<Integer>() {
				// 线程调用方法，查询1-100之和
				@Override
				public Integer call() throws Exception {
					int sum = 0;
					for (int i = 0; i <= 100; i++) {
						sum += i;
					}
					return sum;
				}
			});
			list.add(future);
		}
		// 关闭线程池
		pool.shutdown();
		// 遍历结果集，会输出10次5050
		for (Future<Integer> future : list) {
			System.out.println(future.get());
		}
	}

	/**
	 * 创建固定大小的线程，可以延迟或定时的执行任务
	 * 
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public static void testSchedulePool() throws InterruptedException, ExecutionException {
		// 创建ScheduledExecutorService类型的线程池对象
		ScheduledExecutorService pool = Executors.newScheduledThreadPool(5);

		for (int i = 0; i < 5; i++) {
			Future<Integer> result = pool.schedule(new Callable<Integer>() {
				@Override
				public Integer call() throws Exception {
					int num = new Random().nextInt(100);// 生成随机数
					System.out.println(Thread.currentThread().getName() + " : " + num);
					return num;
				}
			}, 1, TimeUnit.SECONDS);

			System.out.println(result.get());
		}
		// 线程池关闭
		pool.shutdown();
	}
}

class ThreadPoolDemo implements Runnable {

	private int i = 0;

	@Override
	public void run() {
		while (i <= 100) {
			System.out.println(Thread.currentThread().getName() + " : " + i++);
		}
	}
}