package com.semicolon.africa.utils;

import com.semicolon.africa.data.model.User;
import com.semicolon.africa.dtos.Request.UserLoginRequest;
import com.semicolon.africa.dtos.Request.UserRegisterRequest;
import com.semicolon.africa.dtos.Response.UserLoginResponse;
import com.semicolon.africa.dtos.Response.UserRegisterResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class Mapper {

    private static PasswordEncoder passwordEncoder;

    @Autowired
    public Mapper(PasswordEncoder passwordEncoder) {
        Mapper.passwordEncoder = passwordEncoder;
    }

    public static void mapUserRegister(UserRegisterRequest userRegister, User user) {
        user.setFirstName(userRegister.getFirstName());
        user.setLastName(userRegister.getLastName());
        user.setUserName(userRegister.getUserName());
        user.setAddress(userRegister.getAddress());
        user.setPhoneNumber(userRegister.getPhoneNumber());
        user.setEmail(userRegister.getEmail());
        user.setPassword(passwordEncoder.encode(userRegister.getPassword()));
    }

    public static UserRegisterResponse mapUserRegister(User user) {
        UserRegisterResponse userRegister = new UserRegisterResponse();
        userRegister.setMessage("Registered Successfully");
        user.setEmail(user.getEmail());
        return userRegister;
    }

    public static void mapUserLogin(UserLoginRequest userLogin, User user) {
        user.setEmail(userLogin.getEmail());
        user.setPassword(passwordEncoder.encode(userLogin.getPassword()));
    }

    public static UserLoginResponse mapUserLogin(User user) {
        UserLoginResponse userLogin = new UserLoginResponse();
        userLogin.setId(user.getId());
        userLogin.setLoggedIn(true);
        userLogin.setMessage("Logged In Successfully");
        return userLogin;
    }
}