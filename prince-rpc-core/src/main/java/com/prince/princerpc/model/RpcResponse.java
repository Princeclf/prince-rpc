package com.prince.princerpc.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @program: prince-rpc
 * @description: RPC响应
 * @author: clf
 * @create: 2024-08-22 16:28
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RpcResponse implements Serializable {
    /**
     * 响应数据
     */
    private Object date;
    /**
     * 响应数据类型（预留）
     */
    private Class<?> dateType;
    /**
     * 响应信息
     */
    private String message;
    /**
     * 异常信息
     */
    private Exception exception;
}
