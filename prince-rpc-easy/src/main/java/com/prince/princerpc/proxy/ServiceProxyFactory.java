package com.prince.princerpc.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * @program: prince-rpc
 * @description: 代理工厂类
 * @author: clf
 * @create: 2024-08-23 15:02
 **/
public class ServiceProxyFactory {

    public static <T> T getProxy(Class<T> clazz) {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[] { clazz }, new ServiceProxy());
    }
}
