package com.semicolon.africa.utils;

import com.semicolon.africa.data.model.LoanApplication;
import com.semicolon.africa.data.model.Student;
import com.semicolon.africa.dtos.Request.LoanApplicationRequest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

//@SpringBootTest
public class MapperLoanRequestTest {

    private static LoanApplicationRequest loanRequest;

    private static LoanApplication loanApplication;

    private static Student student;

    @BeforeAll
    public static void setUp() {

        student = new Student();
        student.setId(1234L);

        loanRequest = new LoanApplicationRequest();
        loanRequest.setLoanAmount(BigDecimal.valueOf(150_000));
        loanRequest.setMonthlyUpkeep(BigDecimal.valueOf(20_000));
        loanRequest.setLoanDurationMonths(48);

        loanApplication = new LoanApplication();
    }

    @Test
    public void mapStudentRequestToLoanApplicationTest() {
        Mapper.mapLoanApplication(loanRequest, loanApplication, student);

        assertEquals(loanRequest.getStudentId(), student.getId());
        assertEquals(loanRequest.getLoanAmount(), loanApplication.getLoanAmount());
        assertEquals(loanRequest.getMonthlyUpkeep(), loanApplication.getMonthlyUpkeep());
        assertEquals(loanRequest.getLoanDurationMonths(), loanApplication.getLoanDurationMonths());
    }
}