package com.prince.example.consumer;

import com.prince.princerpc.config.RpcConfig;
import com.prince.princerpc.utils.ConfigUtils;

/**
 * @program: prince-rpc
 * @description: 简易服务消费者示例
 * @author: clf
 * @create: 2024-08-24 10:44
 **/
public class ConsumerExample {
    public static void main(String[] args) {
        RpcConfig rpc = ConfigUtils.loadConfig(RpcConfig.class, "rpc");
        System.out.println(rpc);
    }
}
