package com.ping.test.disruptor.demo1;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

public class DisruptorMain {
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// 工厂
		MsgDataFactory factory = new MsgDataFactory();
		// 线程池
		ExecutorService executor = Executors.newCachedThreadPool();
		int bufferSize = 1024; // 必须为2的幂指数

		// 初始化Disruptor
		Disruptor<MsgData> disruptor = new Disruptor<>(factory, bufferSize, executor, ProducerType.SINGLE,
				new YieldingWaitStrategy());
		// 启动消费者
		disruptor.handleEventsWithWorkerPool(new MsgDataHandler(), new MsgDataHandler(), new MsgDataHandler());
		disruptor.start();
		// 获取ringBuffer
		RingBuffer<MsgData> ringBuffer = disruptor.getRingBuffer();
		long t1 = System.currentTimeMillis();
		System.out.println("begin=" + t1);
		// 启动生产者
		MsgDataProducer producer = new MsgDataProducer(ringBuffer);
		for (int i = 0; i <= 100000; i++) {
			// 模拟生成数据
			producer.pushData(i + "");
		}

		System.out.println("over ,user=" + (System.currentTimeMillis() - t1));

	}
}
