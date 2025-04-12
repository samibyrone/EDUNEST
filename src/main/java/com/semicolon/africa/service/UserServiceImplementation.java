package com.semicolon.africa.service;

import com.semicolon.africa.data.model.User;
import com.semicolon.africa.data.repositories.UserRepository;
import com.semicolon.africa.dtos.Request.UserLoginRequest;
import com.semicolon.africa.dtos.Request.UserRegisterRequest;
import com.semicolon.africa.dtos.Response.UserLoginResponse;
import com.semicolon.africa.dtos.Response.UserRegisterResponse;
import com.semicolon.africa.exception.EmailAlreadyExist;
import com.semicolon.africa.exception.WrongEmailOrPassword;
import com.semicolon.africa.exception.userNotFoundException;
import com.semicolon.africa.utils.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.semicolon.africa.utils.Mapper.mapUserLogin;
import static com.semicolon.africa.utils.Mapper.mapUserRegister;

@Service
public class UserServiceImplementation implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final Mapper mapper;

    @Autowired
    public UserServiceImplementation(PasswordEncoder passwordEncoder, UserRepository userRepository, Mapper mapper) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

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
        if (existsByEmail) throw new EmailAlreadyExist("User Already Exist With This Email");
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
        if (!passwordEncoder.matches(userLogin.getPassword(), user.getPassword())) {
            throw new WrongEmailOrPassword("Invalid Credentials"); }
        user.setLoggedIn(true);
        userRepository.save(user);
        return mapUserLogin(user);
    }

    private User findByEmail (String email) {
        return userRepository.findByEmail(email)
                .orElseThrow( () -> new userNotFoundException("User Email does not exist"));
    }
}
