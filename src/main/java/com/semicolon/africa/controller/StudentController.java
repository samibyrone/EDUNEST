package com.semicolon.africa.controller;

import com.semicolon.africa.dtos.Request.StudentRegisterRequest;
import com.semicolon.africa.dtos.Response.StudentRegisterResponse;
import com.semicolon.africa.service.StudentServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudentController {

    @Autowired
    private StudentServiceImplementation studentServiceImplementation;

    @PostMapping("/register")
    public StudentRegisterResponse registerStudent(@RequestBody StudentRegisterRequest studentRegister) {
        return studentServiceImplementation.registerStudent(studentRegister);
    }
}
