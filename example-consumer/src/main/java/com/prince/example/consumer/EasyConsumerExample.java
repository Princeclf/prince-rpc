package com.prince.example.consumer;

import com.prince.example.common.model.User;
import com.prince.example.common.service.UserService;
import com.prince.princerpc.proxy.ServiceProxyFactory;

/**
 * @program: prince-rpc
 * @description: 简易消费者
 * @author: clf
 * @create: 2024-08-21 11:47
 **/
public class EasyConsumerExample {
    public static void main(String[] args) {
        UserService userService = ServiceProxyFactory.getProxy(UserService.class);
        User user = new User();
        user.setName("prince");
        User serviceUser = userService.getUser(user);
        if (serviceUser != null) {
            System.out.println(serviceUser.getName());
        }else {
            System.out.println("user is null");
        }
    }
}
