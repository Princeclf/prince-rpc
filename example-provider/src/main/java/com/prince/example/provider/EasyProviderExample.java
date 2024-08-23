package com.prince.example.provider;

import com.prince.example.common.service.UserService;
import com.prince.princerpc.registry.LocalRegistry;
import com.prince.princerpc.server.HttpServer;
import com.prince.princerpc.server.VertxHttpServer;

/**
 * @program: prince-rpc
 * @description: 简易服务提供者
 * @author: clf
 * @create: 2024-08-21 11:11
 **/
public class EasyProviderExample {

    public static void main(String[] args) {
        LocalRegistry.register(UserService.class.getName(),UserServiceImpl.class);
        HttpServer vertxHttpServer = new VertxHttpServer();
        vertxHttpServer.doStart(8080);
    }
}
