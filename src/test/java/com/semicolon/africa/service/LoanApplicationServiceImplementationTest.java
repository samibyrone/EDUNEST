package com.semicolon.africa.service;

import com.semicolon.africa.data.model.LOAN_STATUS;
import com.semicolon.africa.data.model.LoanApplication;
import com.semicolon.africa.data.model.LoanPolicy;
import com.semicolon.africa.data.model.Student;
import com.semicolon.africa.data.repositories.LoanApplicationRepository;
import com.semicolon.africa.data.repositories.LoanPolicyRepository;
import com.semicolon.africa.data.repositories.StudentRepository;
import com.semicolon.africa.dtos.Request.LoanApplicationRequest;
import com.semicolon.africa.dtos.Response.LoanApplicationResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
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
        loanRequest.setMonthlyUpkeep(BigDecimal.valueOf(15000));
        loanRequest.setLoanDurationMonths(48);
    }

    @Test
    public void testThatStudentCanApplyForLoanSuccessfully() {
        LoanPolicy policy = LoanPolicy.builder()
                .minAmount(BigDecimal.valueOf(50000))
                .maxAmount(BigDecimal.valueOf(200000))
                .build();

        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(loanPolicyRepository.findActivePolicy()).thenReturn(Optional.of(policy));
        when(loanApplicationRepository.existsByStudentAndStatus(student, LOAN_STATUS.PENDING)).thenReturn(false);

        LocalDateTime beforeApplication = LocalDateTime.now();
        LoanApplicationResponse loanResponse = loanApplicationService.applyForLoan(loanRequest);
        LocalDateTime afterApplication = LocalDateTime.now();

        ArgumentCaptor<LoanApplication> loanCapture = ArgumentCaptor.forClass(LoanApplication.class);
        verify(loanApplicationRepository, times(1)).save(loanCapture.capture());
        LoanApplication savedApplication = loanCapture.getValue();
        LocalDateTime applicationDate = savedApplication.getApplicationDate();

        assertThat(applicationDate).isNotNull().isBetween(beforeApplication, afterApplication);
        assertTrue((applicationDate.isAfter(beforeApplication) || applicationDate.isEqual(beforeApplication)) &&
                (applicationDate.isBefore(afterApplication) || applicationDate.isEqual(afterApplication)), "Application Date Should Be Between BeforeApplication and AfterApplication");
        assertEquals(LOAN_STATUS.PENDING, loanResponse.getStatus());
        assertEquals("Loan Application Submitted Successfully", loanResponse.getMessage());
        assertEquals(BigDecimal.valueOf(150000), loanResponse.getLoanAmount());
        assertEquals(BigDecimal.valueOf(20000), loanResponse.getMonthlyUpkeep());
        assertEquals(1L, loanResponse.getStudentId());
    }

}