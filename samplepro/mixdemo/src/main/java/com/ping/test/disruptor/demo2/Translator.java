package com.ping.test.disruptor.demo2;

import com.lmax.disruptor.EventTranslatorOneArg;

import java.nio.ByteBuffer;

/**
 *把输入对象翻译成消息消息对象
 * Created by teeyoung on 17/10/27.
 */
class Translator implements EventTranslatorOneArg<LongEvent, ByteBuffer> {
	@Override
	public void translateTo(LongEvent event, long sequence, ByteBuffer data) {
		event.set(data.getLong(0));
	}
}