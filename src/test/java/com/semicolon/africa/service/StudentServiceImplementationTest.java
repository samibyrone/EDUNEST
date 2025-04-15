package com.semicolon.africa.service;

import com.semicolon.africa.data.model.Student;
import com.semicolon.africa.data.repositories.StudentRepository;
import com.semicolon.africa.dtos.Request.StudentLoginRequest;
import com.semicolon.africa.dtos.Request.StudentRegisterRequest;
import com.semicolon.africa.dtos.Response.StudentLoginResponse;
import com.semicolon.africa.dtos.Response.StudentRegisterResponse;
import com.semicolon.africa.exception.EmailAlreadyExist;
import com.semicolon.africa.exception.WrongEmailOrPassword;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class StudentServiceImplementationTest {

    @Autowired
    private StudentServiceImplementation studentServiceImplementation;

    @Autowired
    private StudentRepository studentRepository;

    @BeforeEach
    public void setup() {
        studentRepository.deleteAll();
    }

    @Test
    public void testThatUserCanGetRegistered() {
        StudentRegisterResponse response = Registration();
        System.out.println("Updating contact with ID: " + response.getId());
        System.out.println("Updating contact with ID: " + response);
        assertThat(response).isNotNull();
        assertThat(studentServiceImplementation.getAllUsers().size()).isEqualTo(1L);
        assertThat(response.getMessage()).contains("Registered Successfully");
    }

    private StudentRegisterResponse Registration() {
        StudentRegisterRequest request = new StudentRegisterRequest();
        request.setFirstName("samson");
        request.setLastName("kizito");
        request.setUserName("sammy");
        request.setPhoneNumber("08100059657");
        request.setAddress("302, Sabo bus-stop yaba, Lagos");
        request.setEmail("sammy@gmail.com");
        request.setPassword("password");
        StudentRegisterResponse response = studentServiceImplementation.registerStudent(request);
        return response;
    }

    @Test
    public void testThatTwo_usersCanBeRegisteredSuccessfully() {
        Registration();
        StudentRegisterRequest studentRequest = new StudentRegisterRequest();
        studentRequest.setFirstName("Martins");
        studentRequest.setLastName("Shola");
        studentRequest.setUserName("Oluwanishola");
        studentRequest.setPhoneNumber("08187606899");
        studentRequest.setAddress("sabo bus-stop yaba, Lagos");
        studentRequest.setEmail("martins@gmail.com");
        studentRequest.setPassword("password12345");
        StudentRegisterResponse response = studentServiceImplementation.registerStudent(studentRequest);
        assertThat(response).isNotNull();
        assertThat(response.getMessage()).contains("Registered Successfully");
        assertThat(studentServiceImplementation.getAllUsers().size()).isEqualTo(2L);
    }

    @Test
    public void testThatUserCanLoginSuccessfully() {
        Registration();
        StudentLoginRequest studentRequest = new StudentLoginRequest();
        studentRequest.setEmail("sammy@gmail.com");
        studentRequest.setPassword("password");
        StudentLoginResponse loginResponse = studentServiceImplementation.loginStudent(studentRequest);
        assertThat(loginResponse).isNotNull();
        assertThat(loginResponse.getMessage()).contains("Logged In Successfully");
        assertThat(loginResponse.isLoggedIn()).isTrue();
        assertThat(studentServiceImplementation.getAllUsers().size()).isEqualTo(1L);
    }

    @Test
    public void testThatOneUser_cannotRegisterWith_twiceWithTheSameEmail() {
        Registration();
        StudentRegisterRequest studentRequest = new StudentRegisterRequest();
        studentRequest.setFirstName("2Being");
        studentRequest.setLastName("tobi");
        studentRequest.setUserName("2Being");
        studentRequest.setPhoneNumber("08187606898");
        studentRequest.setAddress("23th Aveanue festac town");
        studentRequest.setEmail("sammy@gmail.com");
        studentRequest.setPassword("12345");
        assertThrows(EmailAlreadyExist.class, () -> studentServiceImplementation.registerStudent(studentRequest));
        assertThat(studentServiceImplementation.getAllUsers().size()).isEqualTo(1L);
    }

    @Test
    public void testThatOneUser_cannotLoginWith_TheWrongPassword() {
        Registration();
        StudentLoginRequest loginRequest = new StudentLoginRequest();
        loginRequest.setEmail("sammy@gmail.com");
        loginRequest.setPassword("password12345");
        assertThrows(WrongEmailOrPassword.class, () -> studentServiceImplementation.loginStudent(loginRequest));
        Student student = studentRepository.findByEmail("sammy@gmail.com").orElseThrow( () -> new AssertionError("User not found"));
        assertThat(student.isLoggedIn()).isFalse();
    }
}