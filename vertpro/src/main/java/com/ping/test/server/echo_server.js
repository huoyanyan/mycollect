/**
 * vertx run echo_server.js
 */
//echo_server.js
vertx.createHttpServer().requestHandler(function (req) {
    req.response().putHeader("content-type", "text/html").end("<html><body><h1>
    Hello from vert.x!</h1></body></html>");
}).listen(8080); 