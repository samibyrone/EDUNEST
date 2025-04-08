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
public interface UserService {

    UserRegisterResponse registerUser(UserRegisterRequest userRegisterRequest);

    List<User> getAllUsers();

    Optional<User> getUserById(String id);

    UserLoginResponse loginUser(UserLoginRequest userLoginRequest);

}
