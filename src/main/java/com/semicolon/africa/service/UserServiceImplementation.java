package com.semicolon.africa.service;

import com.semicolon.africa.data.model.User;
import com.semicolon.africa.dtos.Request.UserLoginRequest;
import com.semicolon.africa.dtos.Request.UserRegisterRequest;
import com.semicolon.africa.dtos.Response.UserLoginResponse;
import com.semicolon.africa.dtos.Response.UserRegisterResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImplementation implements UserService {


    @Override
    public UserRegisterResponse registerUser(UserRegisterRequest userRegisterRequest) {
        return null;
    }

    @Override
    public List<User> getAllUsers() {
        return List.of();
    }

    @Override
    public Optional<User> getUserById(String id) {
        return Optional.empty();
    }

    @Override
    public UserLoginResponse loginUser(UserLoginRequest userLoginRequest) {
        return null;
    }
}
