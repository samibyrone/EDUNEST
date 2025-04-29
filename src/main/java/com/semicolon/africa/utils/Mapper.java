package com.semicolon.africa.utils;

import com.semicolon.africa.data.model.LOAN_STATUS;
import com.semicolon.africa.data.model.LoanApplication;
import com.semicolon.africa.data.model.Student;
import com.semicolon.africa.dtos.Request.LoanApplicationRequest;
import com.semicolon.africa.dtos.Request.StudentLoginRequest;
import com.semicolon.africa.dtos.Request.StudentRegisterRequest;
import com.semicolon.africa.dtos.Response.LoanApplicationResponse;
import com.semicolon.africa.dtos.Response.StudentLoginResponse;
import com.semicolon.africa.dtos.Response.StudentRegisterResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

//import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
public class Mapper {

    private static PasswordEncoder passwordEncoder;

    @Autowired
    public Mapper(PasswordEncoder passwordEncoder) {
        Mapper.passwordEncoder = passwordEncoder;
    }

    public static void mapStudentRegister(StudentRegisterRequest studentRegister, Student student) {
        student.setFirstName(studentRegister.getFirstName());
        student.setLastName(studentRegister.getLastName());
        student.setUserName(studentRegister.getUserName());
        student.setAddress(studentRegister.getAddress());
        student.setPhoneNumber(studentRegister.getPhoneNumber());
        student.setEmail(studentRegister.getEmail());
        student.setPassword(passwordEncoder.encode(studentRegister.getPassword()));
    }

    public static StudentRegisterResponse mapStudentRegister(Student student) {
        StudentRegisterResponse studentRegister = new StudentRegisterResponse();
        studentRegister.setMessage("Registered Successfully");
        student.setEmail(student.getEmail());
        return studentRegister;
    }

    public static void mapStudentLogin(StudentLoginRequest studentLogin, Student student) {
        student.setEmail(studentLogin.getEmail());
        student.setPassword(passwordEncoder.encode(studentLogin.getPassword()));
    }

    public static StudentLoginResponse mapStudentLogin(Student student) {
        StudentLoginResponse studentLogin = new StudentLoginResponse();
        studentLogin.setId(student.getId());
        studentLogin.setLoggedIn(true);
        studentLogin.setMessage("Logged In Successfully");
        return studentLogin;
    }

    public static void mapLoanApplication(LoanApplicationRequest loanRequest, LoanApplication loanApplication) {
        loanApplication.setLoanAmount(loanRequest.getLoanAmount());
        loanApplication.setMonthlyUpkeep(loanRequest.getMonthlyUpkeep());
        loanApplication.setLoanDurationMonths(loanRequest.getLoanDurationMonths());
    }

    public static LoanApplicationResponse mapLoanApplication(LoanApplication loanApplication) {
        LoanApplicationResponse loanResponse = new LoanApplicationResponse();
        loanResponse.setStudentId(loanApplication.getStudent().getId());
        loanResponse.setStatus(LOAN_STATUS.PENDING);
        loanResponse.setMessage("Loan Application Submitted Successfully");
        return loanResponse;
    }
}