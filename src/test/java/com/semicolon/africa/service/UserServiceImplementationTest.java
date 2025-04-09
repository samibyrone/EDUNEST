package com.semicolon.africa.service;

import com.semicolon.africa.data.repositories.UserRepository;
import com.semicolon.africa.dtos.Request.UserLoginRequest;
import com.semicolon.africa.dtos.Request.UserRegisterRequest;
import com.semicolon.africa.dtos.Response.UserLoginResponse;
import com.semicolon.africa.dtos.Response.UserRegisterResponse;
import com.semicolon.africa.exception.EmailAlreadyExist;
import com.semicolon.africa.exception.WrongEmailOrPassword;
import com.semicolon.africa.utils.Mapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class UserServiceImplementationTest {

    @Autowired
    private UserServiceImplementation userServiceImplementation;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Mapper mapper;

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

    @Test
    public void testThatTwo_usersCanBeRegisteredSuccessfully() {
        Registration();
        UserRegisterRequest userRequest = new UserRegisterRequest();
        userRequest.setFirstName("Martins");
        userRequest.setLastName("Shola");
        userRequest.setUserName("Oluwanishola");
        userRequest.setPhoneNumber("08187606899");
        userRequest.setAddress("sabo bus-stop yaba, Lagos");
        userRequest.setEmail("martins@gmail.com");
        userRequest.setPassword("password12345");
        UserRegisterResponse response = userServiceImplementation.registerUser(userRequest);
        assertThat(response).isNotNull();
        assertThat(response.getMessage()).contains("Registered Successfully");
        assertThat(userServiceImplementation.getAllUsers().size()).isEqualTo(2L);
    }

    @Test
    public void testThatUserCanLoginSuccessfully() {
        Registration();
        UserLoginRequest loginRequest = new UserLoginRequest();
        loginRequest.setEmail("sammy@gmail.com");
        loginRequest.setPassword("password");
        UserLoginResponse loginResponse = userServiceImplementation.loginUser(loginRequest);
        assertThat(loginResponse).isNotNull();
        assertThat(loginResponse.getMessage()).contains("Logged In Successfully");
        assertThat(loginResponse.isLoggedIn()).isTrue();
        assertThat(userServiceImplementation.getAllUsers().size()).isEqualTo(1L);
    }

    @Test
    public void testThatOneUser_cannotRegisterWith_twiceWithTheSameEmail() {
        Registration();
        UserRegisterRequest userRequest = new UserRegisterRequest();
        userRequest.setFirstName("2Being");
        userRequest.setLastName("tobi");
        userRequest.setUserName("2Being");
        userRequest.setPhoneNumber("08187606898");
        userRequest.setAddress("23th Aveanue festac town");
        userRequest.setEmail("sammy@gmail.com");
        userRequest.setPassword("12345");
        assertThrows(EmailAlreadyExist.class, () -> userServiceImplementation.registerUser(userRequest));
        assertThat(userServiceImplementation.getAllUsers().size()).isEqualTo(1L);
    }

    @Test
    public void testThatOneUser_cannotLoginWith_TheWrongPassword() {
        Registration();
        UserLoginRequest loginRequest = new UserLoginRequest();
        loginRequest.setEmail("sammy@gmail.com");
        loginRequest.setPassword("password12345");
        UserLoginResponse loginResponse = userServiceImplementation.loginUser(loginRequest);
        assertThat(loginResponse).isNotNull();
        assertThat(loginResponse.getMessage().contains(WrongEmailOrPassword.class.getSimpleName())).isTrue();
        assertThrows(WrongEmailOrPassword.class, () -> userServiceImplementation.loginUser(loginRequest));
        assertThat(loginResponse.isLoggedIn()).isFalse();
    }
}