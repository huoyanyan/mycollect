package com.ping.test.disruptor.demo1;

import com.lmax.disruptor.WorkHandler;

/** 
 * 消费者 
 * @author daojia 
 * 
 */  
public class MsgDataHandler implements WorkHandler<MsgData> {  
  
    @Override  
    public void onEvent(MsgData event) throws Exception {  
        // TODO Auto-generated method stub  
        String msg = event.getMsg();  
        //模拟业务调用          
        System.out.println(msg+"over");  
        Thread.sleep(10);  
    }     
}  