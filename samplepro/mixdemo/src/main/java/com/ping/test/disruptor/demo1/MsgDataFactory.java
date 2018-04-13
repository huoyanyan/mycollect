package com.ping.test.disruptor.demo1;

import com.lmax.disruptor.EventFactory;

/**
 * 工厂类:构造缓冲区对象实例
 * 
 * @author daojia
 * 
 */
public class MsgDataFactory implements EventFactory<MsgData> {

	@Override
	public MsgData newInstance() {
		// TODO Auto-generated method stub
		return new MsgData();
	}

}