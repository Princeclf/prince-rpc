package com.prince.princerpc.config;

import lombok.Data;

/**
 * @program: prince-rpc
 * @description: RPC框架配置类
 * @author: clf
 * @create: 2024-08-23 17:47
 **/
@Data
public class RpcConfig {
    /**
     *名称
     */
    private String name = "prince";
    /**
     *版本号
     */
    private String version = "1.0";
    /**
     *服务器主机名
     */
    private String serverHost = "localhost";
    /**
     *服务器端口号
     */
    private int serverPort = 8080;
}
