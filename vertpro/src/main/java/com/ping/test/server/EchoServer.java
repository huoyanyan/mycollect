package com.ping.test.server;

import io.vertx.core.AbstractVerticle;
/**
 * Vertx-Java命令行运行
 * vertx run EchoServer.java 
 * @author zhangfei
 *
 */
public class EchoServer extends AbstractVerticle {
	public void start() {
		vertx.createHttpServer().requestHandler(req -> {
			req.response().putHeader("content-type", "text/plain").end("Hello from Vert.x!");
		}).listen(8080);
	}
}
