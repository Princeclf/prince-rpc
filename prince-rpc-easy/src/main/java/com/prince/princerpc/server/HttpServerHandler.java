package com.prince.princerpc.server;

import com.prince.princerpc.model.RpcRequest;
import com.prince.princerpc.model.RpcResponse;
import com.prince.princerpc.registry.LocalRegistry;
import com.prince.princerpc.serializer.JdkSerializer;
import com.prince.princerpc.serializer.Serializer;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;

import java.io.IOException;
import java.lang.reflect.Method;

/**
 * @program: prince-rpc
 * @description: http请求处理器
 * @author: clf
 * @create: 2024-08-23 09:53
 **/
public class HttpServerHandler implements Handler<HttpServerRequest> {


    @Override
    public void handle(HttpServerRequest httpServerRequest) {
        //指定序列化
        final Serializer serializer = new JdkSerializer();
        System.out.println("Receive request: " + httpServerRequest.method() + " " + httpServerRequest.uri());
        //异步处理http请求
        httpServerRequest.bodyHandler(buffer -> {
            byte[] bytes = buffer.getBytes();
            RpcRequest rpcRequest = null;
            try {
                rpcRequest = serializer.deserialize(bytes, RpcRequest.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
            //构建响应结果对象
            RpcResponse rpcResponse = new RpcResponse();
            //如果请求为null，直接返回
            if(rpcRequest == null) {
                rpcResponse.setMessage("rpcRequest is null");
                doResponse(httpServerRequest,rpcResponse,serializer);
                return;
            }

            try {
                //获取调用的服务实现类，通过反射
                Class<?> aClass = LocalRegistry.get(rpcRequest.getServiceName());
                Method method = aClass.getMethod(rpcRequest.getMethodName(), rpcRequest.getParameterTypes());
                Object result = method.invoke(aClass.newInstance(), rpcRequest.getParameters());
                //封装结果
                rpcResponse.setDate(result);
                rpcResponse.setDateType(method.getReturnType());
                rpcResponse.setMessage("OK");
            } catch (Exception e) {
                e.printStackTrace();
                rpcResponse.setMessage(e.getMessage());
                rpcResponse.setException(e);
            }
            //响应
            doResponse(httpServerRequest,rpcResponse,serializer);
        });


    }

    /**
     * 响应
     * @param httpServerRequest
     * @param rpcResponse
     * @param serializer
     */
    private void doResponse(HttpServerRequest httpServerRequest, RpcResponse rpcResponse,Serializer serializer) {
        HttpServerResponse header = httpServerRequest.response().putHeader("Content-Type", "application/json");
        try {
            byte[] serialize = serializer.serialize(rpcResponse);
            header.end(Buffer.buffer(serialize));
        } catch (IOException e) {
            e.printStackTrace();
            header.end(Buffer.buffer());
        }
    }
}
