package com.prince.princerpc.proxy;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.prince.princerpc.model.RpcRequest;
import com.prince.princerpc.model.RpcResponse;
import com.prince.princerpc.serializer.JdkSerializer;
import com.prince.princerpc.serializer.Serializer;


import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @program: prince-rpc
 * @description: 服务代理（JDK动态代理）
 * @author: clf
 * @create: 2024-08-23 11:48
 **/
public class ServiceProxy implements InvocationHandler {
    /**
     *调用代理
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //指定序列化器
        Serializer serializer = new JdkSerializer();
        //构造请求
        RpcRequest rpcRequest = RpcRequest.builder().serviceName(method.getDeclaringClass().getName()).methodName(method.getName())
                .parameters(args).parameterTypes(method.getParameterTypes()).build();
        try{
            //序列化
            byte[] bytes = serializer.serialize(rpcRequest);
            //发送请求
            try(HttpResponse httpResponse = HttpRequest.post("http://localhost:8080").body(bytes).execute()){
                byte[] result = httpResponse.bodyBytes();
                //反序列化
                RpcResponse rpcResponse = serializer.deserialize(result, RpcResponse.class);
                return rpcResponse.getDate();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }
}
