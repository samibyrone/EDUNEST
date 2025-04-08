package com.semicolon.africa.service;

import com.semicolon.africa.data.model.User;
import com.semicolon.africa.data.repositories.UserRepository;
import com.semicolon.africa.dtos.Request.UserLoginRequest;
import com.semicolon.africa.dtos.Request.UserRegisterRequest;
import com.semicolon.africa.dtos.Response.UserLoginResponse;
import com.semicolon.africa.dtos.Response.UserRegisterResponse;
import com.semicolon.africa.exception.EmailAlreadyExist;
import com.semicolon.africa.exception.userNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.semicolon.africa.utils.Mapper.mapUserLogin;
import static com.semicolon.africa.utils.Mapper.mapUserRegister;

@Service
public class UserServiceImplementation implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserRegisterResponse registerUser(UserRegisterRequest userRegister) {
        emailValidation(userRegister.getEmail());
        User user = new User();
        mapUserRegister(userRegister, user);
        userRepository.save(user);
        return mapUserRegister(user);
    }

    private void emailValidation(String email) {
        boolean existsByEmail = userRepository.existsByEmail(email);
        if (existsByEmail) throw new EmailAlreadyExist("Email Already Exist");
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserById(String id) {
        return Optional.empty();
    }

    @Override
    public UserLoginResponse loginUser(UserLoginRequest userLogin) {
        User user = findByEmail(userLogin.getEmail());
        mapUserLogin(user);
        user.setLoggedIn(true);
        userRepository.save(user);
        return mapUserLogin(user);
    }

    private User findByEmail (String email) {
        return userRepository.findByEmail(email)
                .orElseThrow( () -> new userNotFoundException("User does not exist"));
    }
}
