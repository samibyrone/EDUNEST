package com.semicolon.africa.controller;

import com.semicolon.africa.data.model.Student;
import com.semicolon.africa.dtos.Request.StudentLoginRequest;
import com.semicolon.africa.dtos.Request.StudentRegisterRequest;
import com.semicolon.africa.dtos.Response.StudentLoginResponse;
import com.semicolon.africa.dtos.Response.StudentRegisterResponse;
import com.semicolon.africa.service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("api/map/student")
@AllArgsConstructor
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/getAllStudent")
    public List<Student> getAllStudents() {
        return studentService.getAllUsers();
    }

    @PostMapping("/register")
    public ResponseEntity<StudentRegisterResponse> registerStudent(@RequestBody StudentRegisterRequest studentRegister) {
        StudentRegisterResponse registerResponse = studentService.registerStudent(studentRegister);
        return ResponseEntity.status(OK).body(registerResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<StudentLoginResponse> loginStudent(@RequestBody StudentLoginRequest studentLogin) {
        StudentLoginResponse loginResponse = studentService.loginStudent(studentLogin);
        return ResponseEntity.status(OK).body(loginResponse);
    }

    @GetMapping("/getUserById{studentId}")
    public ResponseEntity<Optional<Student>> getStudentById(@PathVariable("studentId") String studentId) {
        Optional<Student> studentResponse = studentService.getStudentById(Long.valueOf(studentId));
        return ResponseEntity.status(OK).body(studentResponse);
    }

    @GetMapping("/findByUserName(studentName)")
    public ResponseEntity<?> findByUserName(@RequestParam("studentName") String userName) {
        Optional<Student> studentResponse = studentService.findByUserName(userName);
        return ResponseEntity.status(OK).body(studentResponse);
    }
}

