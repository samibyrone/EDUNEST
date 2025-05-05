package com.semicolon.africa.service;

import com.semicolon.africa.configuration.JwtService;
import com.semicolon.africa.data.model.Student;
import com.semicolon.africa.data.repositories.StudentRepository;
import com.semicolon.africa.dtos.Request.StudentLoginRequest;
import com.semicolon.africa.dtos.Request.StudentRegisterRequest;
import com.semicolon.africa.dtos.Response.StudentLoginResponse;
import com.semicolon.africa.dtos.Response.StudentRegisterResponse;
import com.semicolon.africa.exception.EmailAlreadyExist;
import com.semicolon.africa.exception.WrongEmailOrPassword;
import com.semicolon.africa.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.semicolon.africa.utils.Mapper.mapStudentLogin;
import static com.semicolon.africa.utils.Mapper.mapStudentRegister;

@Service
@RequiredArgsConstructor
public class StudentServiceImplementation implements StudentService {

    private final StudentRepository studentRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    AuthenticationManager authManager;

    @Autowired
    public StudentServiceImplementation(PasswordEncoder passwordEncoder, StudentRepository studentRepository) {
        this.passwordEncoder = passwordEncoder;
        this.studentRepository = studentRepository;
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
    public Optional<Student> getStudentById(Long id) {
        return studentRepository.findById(id);
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

    public String verifyStudent(StudentRegisterRequest userRegister) {
        Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(userRegister.getUserName(), userRegister.getPassword()));
        if(authentication.isAuthenticated())
            return jwtService.generateToken((UserDetails) studentRepository);
        return "Failed";
    }

    @Override
    public Optional<Student> findByUserName(String userName) {
        return studentRepository.findByUserName(userName);
    }

    private Student findByEmail (String email) {
        return studentRepository.findByEmail(email)
                .orElseThrow( () -> new UserNotFoundException("User Email does not exist"));
    }
}
