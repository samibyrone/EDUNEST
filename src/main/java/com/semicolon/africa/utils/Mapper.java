package com.semicolon.africa.utils;

import com.semicolon.africa.data.model.LOAN_STATUS;
import com.semicolon.africa.data.model.LoanApplication;
import com.semicolon.africa.data.model.Student;
import com.semicolon.africa.data.model.Verification;
import com.semicolon.africa.dtos.Request.LoanApplicationRequest;
import com.semicolon.africa.dtos.Request.LoanApplicationUpdateRequest;
import com.semicolon.africa.dtos.Request.StudentLoginRequest;
import com.semicolon.africa.dtos.Request.StudentRegisterRequest;
import com.semicolon.africa.dtos.Response.LoanApplicationResponse;
import com.semicolon.africa.dtos.Response.LoanApplicationUpdateResponse;
import com.semicolon.africa.dtos.Response.StudentLoginResponse;
import com.semicolon.africa.dtos.Response.StudentRegisterResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

//i
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
        studentRegister.setId(student.getId());
        studentRegister.setEmail(student.getEmail());
        studentRegister.setMessage("Registered Successfully");
        return studentRegister;
    }

    public static void mapStudentLogin(StudentLoginRequest studentLogin, Student student) {
        student.setEmail(studentLogin.getEmail());
        student.setPassword(studentLogin.getPassword());
    }

    public static StudentLoginResponse mapStudentLogin(Student student) {
        StudentLoginResponse studentLogin = new StudentLoginResponse();
        studentLogin.setId(student.getId());
        studentLogin.setLoggedIn(true);
        studentLogin.setMessage("Logged In Successfully");
        return studentLogin;
    }

    public static void mapLoanApplication(LoanApplicationRequest loanRequest, LoanApplication loanApplication, Student student) {
        loanApplication.setLoanAmount(loanRequest.getLoanAmount());
        loanApplication.setMonthlyUpkeep(loanRequest.getMonthlyUpkeep());
        loanApplication.setLoanDurationMonths(loanRequest.getLoanDurationMonths());
        loanApplication.setApplicationDate(loanRequest.getApplicationDate());
        loanApplication.setApplicationDate(LocalDateTime.now());
        loanApplication.setStatus(LOAN_STATUS.PENDING);
        loanApplication.setStudent(student);
    }

    public LoanApplicationResponse mapLoanApplication(LoanApplication loanApplication) {
        LoanApplicationResponse loanResponse = new LoanApplicationResponse();
        loanResponse.setLoanApplicationId(loanApplication.getId());
        loanResponse.setStudentId(loanApplication.getStudent().getId());
        loanResponse.setStatus(LOAN_STATUS.PENDING);
        loanResponse.setMessage("Loan Application Submitted Successfully");
        return loanResponse;
    }

    public static void mapLoanApplicationUpdate(LoanApplicationUpdateRequest loanApplicationUpdate, LoanApplication loanApplication, Verification verification) {
        loanApplication.setLoanAmount(loanApplicationUpdate.getLoanAmount());
        loanApplication.setMonthlyUpkeep(loanApplicationUpdate.getMonthlyUpkeep());
        loanApplication.setLoanDurationMonths(loanApplicationUpdate.getLoanDurationMonths());
        loanApplication.setApplicationDate(loanApplicationUpdate.getApplicationDate());
        loanApplication.setApplicationDate(LocalDateTime.now());
        loanApplication.setStatus(LOAN_STATUS.APPROVED);
        loanApplication.setVerification(verification);
    }

    public static LoanApplicationUpdateResponse mapLoanApplicationUpdate(LoanApplication loanApplication) {
        LoanApplicationUpdateResponse loanAppUpdate = new LoanApplicationUpdateResponse();
        loanAppUpdate.setLoanApplicationId(loanApplication.getId());
        loanAppUpdate.setStudentId(loanApplication.getStudent().getId());
        loanAppUpdate.setStatus(LOAN_STATUS.APPROVED);
        loanAppUpdate.setMessage("Loan Application Approved Successfully");
        return loanAppUpdate;
    }
}