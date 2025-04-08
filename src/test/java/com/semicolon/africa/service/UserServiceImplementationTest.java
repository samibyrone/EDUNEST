package com.semicolon.africa.service;

import com.semicolon.africa.data.repositories.UserRepository;
import com.semicolon.africa.dtos.Request.UserRegisterRequest;
import com.semicolon.africa.dtos.Response.UserRegisterResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class UserServiceImplementationTest {

    @Autowired
    private UserServiceImplementation userServiceImplementation;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setup() {
        userRepository.deleteAll();
    }

    @Test
    public void testThatUserCanGetRegistered() {
        UserRegisterResponse response = Registration();
        System.out.println("Updating contact with ID: " + response.getId());
        System.out.println("Updating contact with ID: " + response);
        assertThat(response).isNotNull();
        assertThat(userServiceImplementation.getAllUsers().size()).isEqualTo(1L);
        assertThat(response.getMessage()).contains("Registered Successfully");
    }

    private UserRegisterResponse Registration() {
        UserRegisterRequest request = new UserRegisterRequest();
        request.setFirstName("samson");
        request.setLastName("kizito");
        request.setUserName("sammy");
        request.setPhoneNumber("08100059657");
        request.setAddress("302, Sabo bus-stop yaba, Lagos");
        request.setEmail("sammy@gmail.com");
        request.setPassword("password");
        UserRegisterResponse response = userServiceImplementation.registerUser(request);
        return response;
    }
//
//    @Test
//    void getAllUsers() {
//    }
//
//    @Test
//    void getUserById() {
//    }
//
//    @Test
//    void loginUser() {
//    }
}