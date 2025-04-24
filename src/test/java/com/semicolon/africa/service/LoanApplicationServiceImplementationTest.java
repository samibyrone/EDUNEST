package com.semicolon.africa.service;

import com.semicolon.africa.data.model.LOAN_STATUS;
import com.semicolon.africa.data.model.LoanPolicy;
import com.semicolon.africa.data.model.Student;
import com.semicolon.africa.data.repositories.LoanApplicationRepository;
import com.semicolon.africa.data.repositories.LoanPolicyRepository;
import com.semicolon.africa.data.repositories.StudentRepository;
import com.semicolon.africa.dtos.Request.LoanApplicationRequest;
import com.semicolon.africa.dtos.Response.LoanApplicationResponse;
import com.semicolon.africa.exception.DuplicateApplicationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class LoanApplicationServiceImplementationTest {

    @Mock
    private LoanApplicationRepository loanApplicationRepository;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private LoanPolicyRepository loanPolicyRepository;

    @InjectMocks
    private LoanApplicationServiceImplementation loanApplicationService;

    private Student student;
    private LoanApplicationRequest loanRequest;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        student = new Student();
        student.setId(1L);

        loanRequest = new LoanApplicationRequest();
        loanRequest.setStudentId(1L);
        loanRequest.setLoanAmount(BigDecimal.valueOf(150000));
        loanRequest.setMonthlyUpkeep(BigDecimal.valueOf(20000));
        loanRequest.setLoanDurationMonths(48);
    }

    @Test
    public void testThatStudentCanApplyForLoanSuccessfully() {
        LoanPolicy policy = LoanPolicy.builder()
                .minAmount(BigDecimal.valueOf(50000))
                .maxAmount(BigDecimal.valueOf(200000))
                .build();

//        LoanApplicationRequest request = new LoanApplicationRequest();
//        request.setLoanApplicationId(111L);
//        request.setLoanAmount(BigDecimal.valueOf(150000));
//        request.setMonthlyUpkeep(BigDecimal.valueOf(20000));
//        request.setApplicationDate(LocalDateTime.parse("2025-04-22T07:00"));

        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(loanPolicyRepository.findActivePolicy()).thenReturn(Optional.of(policy));
        when(loanApplicationRepository.existsByStudentAndStatus(student, LOAN_STATUS.PENDING)).thenReturn(true);
        assertThrows(DuplicateApplicationException.class, () -> loanApplicationService.applyForLoan(loanRequest));
        verify(loanApplicationRepository, never()).save(any());
    }

//    private LoanApplicationResponse LoanApplications() {

//        LoanApplicationResponse response = LoanApplicationService.applyForLoan(request);
//        return response;
//    }

}