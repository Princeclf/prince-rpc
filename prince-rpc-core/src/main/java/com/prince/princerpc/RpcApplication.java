package com.prince.princerpc;

import com.prince.princerpc.config.RpcConfig;
import com.prince.princerpc.constant.RpcConstant;
import com.prince.princerpc.utils.ConfigUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * @program: prince-rpc
 * @description: RPC框架应用 存放项目全局变量 双检验锁单例
 * @author: clf
 * @create: 2024-08-24 10:27
 **/
@Slf4j
public class RpcApplication {

    private static volatile RpcConfig rpcConfig;

    /**
     * 框架初始化 支持传入自定义配置
     * @param newRpcConfig
     */
    public static void init(RpcConfig newRpcConfig) {
        rpcConfig = newRpcConfig;
        log.info("Rpc init , config:{}", newRpcConfig.toString());
    }

    /**
     * 初始化
     */
    public static void init(){
        RpcConfig newRpcConfig;
        try{
            newRpcConfig = ConfigUtils.loadConfig(RpcConfig.class, RpcConstant.DEFAULT_CONFIG_PREFIX);
        }catch(Exception e){
            newRpcConfig = new RpcConfig();
        }
        init(newRpcConfig);
    }

    /**
     * 获取配置
     * @return
     */
    public static RpcConfig getRpcConfig() {
        if (rpcConfig == null){
            synchronized (RpcApplication.class){
                if (rpcConfig == null){
                    init();
                }
            }
        }
        return rpcConfig;
    }
}
