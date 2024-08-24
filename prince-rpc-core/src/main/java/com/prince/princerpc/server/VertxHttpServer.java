package com.prince.princerpc.server;

import io.vertx.core.Vertx;

/**
 * @program: prince-rpc
 * @description: web服务器
 * @author: clf
 * @create: 2024-08-21 17:43
 **/
public class VertxHttpServer implements HttpServer{
    @Override
    public void doStart(int port) {
        //创建Vertx.x 实例
        Vertx vertx = Vertx.vertx();
        //创建http服务器
        io.vertx.core.http.HttpServer httpServer = vertx.createHttpServer();
        //监听端口并处理请求
//        httpServer.requestHandler(req -> {
//            //处理http请求
//            System.out.println("Received request" + req.method() + " " + req.uri());
//            //发送http响应
//            req.response().putHeader("content-type","text/plain").end("Hello Http Vert.x");
//        });
        httpServer.requestHandler(new HttpServerHandler());
        //启动http服务器并监听指定端口
        httpServer.listen(port,result -> {
            if (result.succeeded()) {
                System.out.println("Server started on port " + port);
            }else{
                System.out.println("Failed to start server" + result.cause());
            }
        });
    }
}
