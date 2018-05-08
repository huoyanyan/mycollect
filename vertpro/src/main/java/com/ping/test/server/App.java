package com.ping.test.server;

import io.vertx.core.Vertx;

/**
 * Java main方法方式运行
 * 
 * @author zhangfei
 *
 */
public class App {
	public static void main(String[] args) {
		Vertx.vertx().createHttpServer().requestHandler(req -> req.response().end("Hello World!")).listen(8080);
	}
}
