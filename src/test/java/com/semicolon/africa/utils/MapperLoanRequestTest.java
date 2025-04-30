package com.semicolon.africa.utils;

import com.semicolon.africa.data.model.LOAN_STATUS;
import com.semicolon.africa.data.model.LoanApplication;
import com.semicolon.africa.data.model.Student;
import com.semicolon.africa.dtos.Request.LoanApplicationRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class MapperLoanRequestTest {

    @Autowired
    private Mapper mapper;

    private static LoanApplicationRequest loanRequest;

    private static LoanApplication loanApplication;

    private static Student student;

    @BeforeEach
    public void setUp() {

        student = new Student();
        student.setId(1234L);

        loanRequest = new LoanApplicationRequest();
        loanRequest.setLoanAmount(BigDecimal.valueOf(150_000));
        loanRequest.setMonthlyUpkeep(BigDecimal.valueOf(20_000));
        loanRequest.setLoanDurationMonths(48);
        loanRequest.setApplicationDate(LocalDateTime.now());
        loanRequest.setStudentId(student.getId());

        loanApplication = new LoanApplication();
    }

    @Test
    public void mapStudentRequestToLoanApplicationTest() {
        Mapper.mapLoanApplication(loanRequest, loanApplication, student);

        assertEquals(student, loanApplication.getStudent());
        assertEquals(loanRequest.getLoanAmount(), loanApplication.getLoanAmount());
        assertEquals(loanRequest.getMonthlyUpkeep(), loanApplication.getMonthlyUpkeep());
        assertEquals(loanRequest.getLoanDurationMonths(), loanApplication.getLoanDurationMonths());
        assertNotNull(loanApplication.getApplicationDate());
        assertEquals(LOAN_STATUS.PENDING, loanApplication.getStatus());
        assertTrue(loanApplication.getApplicationDate().isBefore(LocalDateTime.now().plusMinutes(1)));
        assertTrue(loanApplication.getApplicationDate().isAfter(LocalDateTime.now().minusMinutes(1)));
    }
}