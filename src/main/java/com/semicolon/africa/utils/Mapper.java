package com.semicolon.africa.utils;

import com.semicolon.africa.data.model.User;
import com.semicolon.africa.dtos.Request.UserLoginRequest;
import com.semicolon.africa.dtos.Request.UserRegisterRequest;
import com.semicolon.africa.dtos.Response.UserLoginResponse;
import com.semicolon.africa.dtos.Response.UserRegisterResponse;

public class Mapper {

    public static void mapUserRegister(UserRegisterRequest userRegisterRequest, User user) {
        user.setFirstName(userRegisterRequest.getFirstName());
        user.setLastName(userRegisterRequest.getLastName());
        user.setUserName(userRegisterRequest.getUserName());
        user.setAddress(userRegisterRequest.getAddress());
        user.setPhoneNumber(userRegisterRequest.getPhoneNumber());
        user.setEmail(userRegisterRequest.getEmail());
        user.setPassword(userRegisterRequest.getPassword());
    }

    public static UserRegisterResponse mapUserRegister(User user) {
        UserRegisterResponse userRegister = new UserRegisterResponse();
        userRegister.setMessage("Registered Successfully");
        user.setEmail(user.getEmail());
        return userRegister;
    }

   public static void mapUserLogin(UserLoginRequest userLoginRequest, User user) {
        user.setEmail(userLoginRequest.getEmail());
        user.setPassword(userLoginRequest.getPassword());
   }

   public static UserLoginResponse mapUserLogin(User user) {
        UserLoginResponse userLogin = new UserLoginResponse();
        userLogin.setId(user.getId());
        userLogin.setLoggedIn(true);
        userLogin.setMessage("Logged In Successfully");
        return userLogin;
   }
}
