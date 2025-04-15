package com.semicolon.africa.service;

import com.semicolon.africa.data.model.Student;
import com.semicolon.africa.dtos.Request.StudentLoginRequest;
import com.semicolon.africa.dtos.Request.StudentRegisterRequest;
import com.semicolon.africa.dtos.Response.StudentLoginResponse;
import com.semicolon.africa.dtos.Response.StudentRegisterResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface StudentService {

    StudentRegisterResponse registerStudent(StudentRegisterRequest studentRegisterRequest);

    List<Student> getAllUsers();

    Optional<Student> getUserById(String id);

    StudentLoginResponse loginStudent(StudentLoginRequest studentLoginRequest);

}
