package com.ping.test.disruptor.demo2;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;

import java.nio.ByteBuffer;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * 这张图的意思就是消费者1b消费时，必须保证消费者1a已经完成对该消息的消费；消费者2b消费时，必须保证消费者2a已经完成对该消息的消费；消费者c3消费时，必须保证消费者1b和2b已经完成对该消息的消费
 * Created by teeyoung on 17/10/28.
 */
public class DisruptorWizardMain2 {

	private static final Translator TRANSLATOR = new Translator();

	public static void main(String[] args) throws InterruptedException {
		Executor executor = Executors.newCachedThreadPool();
		int bufferSize = 1024;

		EventHandler h1a = new EventHandler<LongEvent>() {

			@Override
			public void onEvent(LongEvent event, long sequence, boolean endOfBatch) throws Exception {
				System.out.println("h1a : " + event.getValue());
			}
		};
		EventHandler h1b = new EventHandler<LongEvent>() {

			@Override
			public void onEvent(LongEvent event, long sequence, boolean endOfBatch) throws Exception {
				System.out.println("h1b : " + event.getValue() + " arrived. H1a should have completed. Completed.");
			}
		};

		EventHandler h2a = new EventHandler<LongEvent>() {

			@Override
			public void onEvent(LongEvent event, long sequence, boolean endOfBatch) throws Exception {
				System.out.println("h2a : " + event.getValue());
			}
		};
		EventHandler h2b = new EventHandler<LongEvent>() {

			@Override
			public void onEvent(LongEvent event, long sequence, boolean endOfBatch) throws Exception {
				System.out.println("h2b : " + event.getValue() + " arrived. H2a should have completed. Completed.");
			}
		};

		EventHandler lastHandler = new EventHandler<LongEvent>() {

			@Override
			public void onEvent(LongEvent event, long sequence, boolean endOfBatch) throws Exception {
				System.out.println("lastHandler : " + event.getValue()
						+ " arrived. H1a, h1b, h2a and h2b should have completed. Completed.\n");
			}
		};

		Disruptor<LongEvent> disruptor = new Disruptor<>(LongEvent::new, bufferSize, executor);

		disruptor.handleEventsWith(h1a, h2a);
		disruptor.after(h1a).then(h1b);
		disruptor.after(h2a).then(h2b);
		disruptor.after(h1b, h2b).then(lastHandler);

		disruptor.start();

		RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();

		ByteBuffer bb = ByteBuffer.allocate(8);
		for (long l = 0; true; l++) {
			bb.putLong(0, l);
			ringBuffer.publishEvent(TRANSLATOR, bb);
			Thread.sleep(1000);
		}
	}
}
