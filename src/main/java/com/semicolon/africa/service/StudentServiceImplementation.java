package com.semicolon.africa.service;

import com.semicolon.africa.data.model.Student;
import com.semicolon.africa.data.repositories.StudentRepository;
import com.semicolon.africa.dtos.Request.StudentLoginRequest;
import com.semicolon.africa.dtos.Request.StudentRegisterRequest;
import com.semicolon.africa.dtos.Response.StudentLoginResponse;
import com.semicolon.africa.dtos.Response.StudentRegisterResponse;
import com.semicolon.africa.exception.EmailAlreadyExist;
import com.semicolon.africa.exception.WrongEmailOrPassword;
import com.semicolon.africa.exception.userNotFoundException;
import com.semicolon.africa.utils.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.semicolon.africa.utils.Mapper.mapStudentLogin;
import static com.semicolon.africa.utils.Mapper.mapStudentRegister;

@Service
public class StudentServiceImplementation implements StudentService {

    private final StudentRepository studentRepository;

    private final PasswordEncoder passwordEncoder;

    private final Mapper mapper;

    @Autowired
    public StudentServiceImplementation(PasswordEncoder passwordEncoder, StudentRepository studentRepository, Mapper mapper) {
        this.passwordEncoder = passwordEncoder;
        this.studentRepository = studentRepository;
        this.mapper = mapper;
    }

    @Override
    public StudentRegisterResponse registerStudent(StudentRegisterRequest userRegister) {
        emailValidation(userRegister.getEmail());
        Student student = new Student();
        mapStudentRegister(userRegister, student);
        studentRepository.save(student);
        return mapStudentRegister(student);
    }

    private void emailValidation(String email) {
        boolean existsByEmail = studentRepository.existsByEmail(email);
        if (existsByEmail) throw new EmailAlreadyExist("User Already Exist With This Email");
    }

    @Override
    public List<Student> getAllUsers() {
        return studentRepository.findAll();
    }

    @Override
    public Optional<Student> getUserById(String id) {
        return Optional.empty();
    }

    @Override
    public StudentLoginResponse loginStudent(StudentLoginRequest userLogin) {
        Student student = findByEmail(userLogin.getEmail());
        if (!passwordEncoder.matches(userLogin.getPassword(), student.getPassword())) {
            throw new WrongEmailOrPassword("Invalid Credentials"); }
        student.setLoggedIn(true);
        studentRepository.save(student);
        return mapStudentLogin(student);
    }

    private Student findByEmail (String email) {
        return studentRepository.findByEmail(email)
                .orElseThrow( () -> new userNotFoundException("User Email does not exist"));
    }
}
