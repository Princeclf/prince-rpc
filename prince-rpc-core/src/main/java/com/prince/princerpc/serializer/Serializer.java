package com.prince.princerpc.serializer;

import java.io.IOException;

/**
 * @program: prince-rpc
 * @description: 序列化器接口
 * @author: clf
 * @create: 2024-08-22 16:17
 **/
public interface Serializer {
    /**
     * 序列化
     * @param obj
     * @return
     * @param <T>
     * @throws IOException
     */
    <T> byte[] serialize(T obj) throws IOException;

    /**
     * 反序列化
     * @param data
     * @param clazz
     * @return
     * @param <T>
     * @throws IOException
     */
    <T> T deserialize(byte[] data, Class<T> clazz) throws IOException;
}
