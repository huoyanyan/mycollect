package com.ping.test.thread.forkjoin.demo1;

import java.util.concurrent.RecursiveTask;

/**
 * 计算 π 的值。计算 π 的值有一个通过多项式方法，即：π = 4 * (1 - 1/3 + 1/5 - 1/7 + 1/9 -
 * ……)，而且多项式的项数越多，计算出的 π 的值越精确
 * 
 * @author zhangfei
 *
 */
class PiEstimateTask extends RecursiveTask<Double> {

	private final long begin;
	private final long end;
	private final long threshold; // 分割任务的临界值

	public PiEstimateTask(long begin, long end, long threshold) {
		this.begin = begin;
		this.end = end;
		this.threshold = threshold;
	}

	@Override
	protected Double compute() { // 实现 compute 方法
		if (end - begin <= threshold) { // 临界值之下，不再分割，直接计算

			int sign = 1; // 符号，取 1 或者 -1
			double result = 0.0;
			for (long i = begin; i < end; i++) {
				result += sign / (i * 2.0 + 1);
				sign = -sign;
			}

			return result * 4;
		}

		// 分割任务
		long middle = (begin + end) / 2;
		PiEstimateTask leftTask = new PiEstimateTask(begin, middle, threshold);
		PiEstimateTask rightTask = new PiEstimateTask(middle, end, threshold);

		leftTask.fork(); // 异步执行 leftTask
		rightTask.fork(); // 异步执行 rightTask

		double leftResult = leftTask.join(); // 阻塞，直到 leftTask 执行完毕返回结果
		double rightResult = rightTask.join(); // 阻塞，直到 rightTask 执行完毕返回结果
		System.out.println(begin+ "-----------" + end + "-----------" + leftResult + "---------------" + rightResult);

		return leftResult + rightResult; // 合并结果
	}

}
