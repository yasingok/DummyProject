package com.example.order_adapter.factory;

import com.example.order_adapter.constant.TestConstant;
import com.example.order_adapter.model.User;

public class UserFactory {
    public static User createUser(){
        User user = new User();
        user.setIdentityNumber(TestConstant.IDENTITY_NUMBER);
        return user;
    }
}
