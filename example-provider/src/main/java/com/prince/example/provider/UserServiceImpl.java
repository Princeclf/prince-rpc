package com.prince.example.provider;

import com.prince.example.common.model.User;
import com.prince.example.common.service.UserService;

/**
 * @program: prince-rpc
 * @description: 用户实现类
 * @author: clf
 * @create: 2024-08-21 11:10
 **/
public class UserServiceImpl implements UserService {
    @Override
    public User getUser(User user) {
        System.out.println("名字：" + user.getName());
        return user;
    }
}
