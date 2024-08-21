package com.prince.example.common.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: prince-rpc
 * @description: 用户实体类
 * @author: clf
 * @create: 2024-08-21 10:50
 **/
@Data
public class User implements Serializable {
    private String name;
}
