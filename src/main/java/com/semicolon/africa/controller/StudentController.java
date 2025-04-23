package com.semicolon.africa.controller;

import com.semicolon.africa.dtos.Request.StudentLoginRequest;
import com.semicolon.africa.dtos.Request.StudentRegisterRequest;
import com.semicolon.africa.dtos.Response.StudentLoginResponse;
import com.semicolon.africa.dtos.Response.StudentRegisterResponse;
import com.semicolon.africa.service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("api/map")
@AllArgsConstructor
public class StudentController {

    @Autowired
    private StudentService studentService;
//    @Qualifier("studentService")

    @PostMapping("/register")
    public ResponseEntity<?> registerStudent(@RequestBody StudentRegisterRequest studentRegister) {
        StudentRegisterResponse registerResponse = studentService.registerStudent(studentRegister);
        return ResponseEntity.status(OK).body(registerResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginStudent(@RequestBody StudentLoginRequest studentLogin) {
        StudentLoginResponse loginResponse = studentService.loginStudent(studentLogin);
        return ResponseEntity.status(OK).body(loginResponse);
    }
}

