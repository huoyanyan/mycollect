package com.ping.test.server;

import io.vertx.core.Context;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Verticle;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerRequest;

public class ServerExample implements Verticle {
	Vertx vertx;

	public void start() {
		vertx.createHttpServer().requestHandler(new Handler<HttpServerRequest>() {
			public void handle(HttpServerRequest req) {
				System.out.println("Got request: " + req.uri());
				req.response().headers().set("Content-Type", "text/html; charset=UTF-8");
				req.response().end("<html><body><h1>Hello from vert.x!</h1></body></html>");
			}
		}).listen(8080);
	}

	@Override
	public Vertx getVertx() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void init(Vertx vertx, Context context) {
		// TODO Auto-generated method stub

	}

	@Override
	public void start(Future<Void> startFuture) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void stop(Future<Void> stopFuture) throws Exception {
		// TODO Auto-generated method stub

	}
}
