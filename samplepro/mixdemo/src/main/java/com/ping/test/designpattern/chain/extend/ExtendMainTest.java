package com.ping.test.designpattern.chain.extend;

import com.ping.test.designpattern.chain.ChainOfResponsibilityClient;
import com.ping.test.designpattern.chain.Request;
import com.ping.test.designpattern.chain.Result;

public class ExtendMainTest {

	public static void main(String[] args) {
		Request request = new Request.Builder().setName("张三").setDays(5).setReason("事假").build();
		ChainOfResponsibilityClient client = new ChainOfResponsibilityClient();
		client.addRatifys(new CustomInterceptor());
		Result result = client.execute(request);

		System.out.println("结果：" + result.toString());

	}

}
