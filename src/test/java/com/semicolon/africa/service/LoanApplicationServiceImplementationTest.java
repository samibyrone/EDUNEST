package com.semicolon.africa.service;

import com.semicolon.africa.data.model.*;
import com.semicolon.africa.data.repositories.LoanApplicationRepository;
import com.semicolon.africa.data.repositories.LoanPolicyRepository;
import com.semicolon.africa.data.repositories.StudentRepository;
import com.semicolon.africa.dtos.Request.LoanApplicationRequest;
import com.semicolon.africa.dtos.Response.LoanApplicationResponse;
import com.semicolon.africa.exception.InvalidLoanAmountException;
import com.semicolon.africa.exception.InvalidPolicyRequestException;
import com.semicolon.africa.exception.InvalidStudentLoginInRequestException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class LoanApplicationServiceImplementationTest {

    @Autowired
    private LoanApplicationRepository loanApplicationRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private LoanPolicyRepository loanPolicyRepository;

    @Autowired
    private LoanApplicationServiceImplementation loanApplicationService;

    private Student student;
    private LoanPolicy policy;
    private LoanApplicationRequest loanRequest;

    @BeforeEach
    public void setUp() {
        student = new Student();
        student.setFirstName("sammy");
        student.setLastName("kent");
        student.setUserName("sam");
        student.setEmail("sammy@email.com");
        student.setPhoneNumber("08100059657");
        student.setAddress("sabo yaba bus-stop");
        student.setPassword("sammy@12345");
        student.setLoggedIn(true);
        student.setRole(STUDENT_TYPE.TERTIARY_SCHOOL_STUDENT);
        studentRepository.save(student);

        policy = LoanPolicy.builder()
            .minAmount(BigDecimal.valueOf(50000))
            .maxAmount(BigDecimal.valueOf(200000))
            .maxMonthlyUpkeep(BigDecimal.valueOf(25000))
            .isActive(true)
            .policyName("Education Loan Policy")
            .policyDescription("School Fees")
            .build();
        loanPolicyRepository.save(policy);

        loanRequest = new LoanApplicationRequest();
        loanRequest.setStudentId(student.getId());
        loanRequest.setLoanAmount(BigDecimal.valueOf(150000));
        loanRequest.setMonthlyUpkeep(BigDecimal.valueOf(20000));
        loanRequest.setLoanDurationMonths(48);
    }

    @AfterEach
    public void cleanUp() {
        loanApplicationRepository.deleteAll();
        loanPolicyRepository.deleteAll();
        studentRepository.deleteAll();
    }

    @Test
    public void testThatStudentCanApplyForLoanSuccessfully() {

        LocalDateTime beforeApplication = LocalDateTime.now();
        LoanApplicationResponse loanResponse = loanApplicationService.applyForLoan(loanRequest);
        LocalDateTime afterApplication = LocalDateTime.now();


        assertNotNull(loanResponse);
        assertNotNull(loanResponse.getLoanApplicationId());
        assertNotNull(loanResponse.getStudentId(), "Student ID Should Not Be Null");
        assertEquals(LOAN_STATUS.PENDING, loanResponse.getStatus());
        assertEquals(student.getId(), loanResponse.getStudentId());

        Optional<LoanApplication> savedApplication = loanApplicationRepository.findById(loanResponse.getLoanApplicationId());
        assertTrue(savedApplication.isPresent(), "Loan application should be saved in the repository");
        LoanApplication application = savedApplication.get();
        assertEquals(BigDecimal.valueOf(150000), application.getLoanAmount());
        assertEquals(BigDecimal.valueOf(20000), application.getMonthlyUpkeep());
        assertEquals(48, application.getLoanDurationMonths());
        LocalDateTime applicationDate = application.getApplicationDate();
        assertTrue(applicationDate.isAfter(beforeApplication) && applicationDate.isBefore(afterApplication),"Application date should be between beforeApplication and afterApplication");
        assertEquals("Loan Application Submitted Successfully", loanResponse.getMessage());
        assertThat(loanApplicationService.getAllLoanApplication().size()).isEqualTo(1L);
    }

    @Test
    public void testThatThrowsExceptions_whenStudentApplyForLoan_belowTheMinimumAmountLimit() {
        loanRequest.setLoanAmount(BigDecimal.valueOf(45000));
        Exception exception = assertThrows(InvalidLoanAmountException.class, () -> loanApplicationService.applyForLoan(loanRequest));
        assertTrue(exception.getMessage().contains("Loan Amount Must Not Be less That 50,000"));
        assertThat(loanApplicationService.getAllLoanApplication().size()).isEqualTo(0);
    }

    @Test
    public void testThatThrowsExceptions_whenStudentApplyForLoan_aboveMaximumAmountLimit() {
        loanRequest.setLoanAmount(BigDecimal.valueOf(210000));
        Exception exception = assertThrows(InvalidLoanAmountException.class, () -> loanApplicationService.applyForLoan(loanRequest));
        assertTrue(exception.getMessage().contains("Loan Amount Must Not Be Above 200,000"));
        assertThat(loanApplicationService.getAllLoanApplication().size()).isEqualTo(0);
    }

    @Test
    public void testThatThrowSExceptions_whenStudentMonthlyUpkeep_exceedPolicyLimit() {
        loanRequest.setMonthlyUpkeep(BigDecimal.valueOf(30000));
        Exception exception = assertThrows(InvalidLoanAmountException.class, () -> loanApplicationService.applyForLoan(loanRequest));
        assertTrue(exception.getMessage().contains("Monthly Upkeep Already Exceeded Maximum Amount Allowed"));
        assertThat(loanApplicationService.getAllLoanApplication().size()).isEqualTo(0);
    }

    @Test
    public void testThatThrowsExceptions_whenStudent_applyForLoan_with_InActivePolicy() {
        policy.setActive(false);
        loanPolicyRepository.save(policy);
        Exception exception = assertThrows(InvalidPolicyRequestException.class, () -> loanApplicationService.applyForLoan(loanRequest));
        assertTrue(exception.getMessage().contains("Loan Policy Is Not Active"));
        assertThat(loanApplicationService.getAllLoanApplication().size()).isEqualTo(0);
    }

    @Test
    public void testThatThrowsExceptions_whenStudentApplyForLoan_withoutLoggingIn() {
        student.setLoggedIn(false);
        studentRepository.save(student);
        Exception exception = assertThrows(InvalidStudentLoginInRequestException.class, () -> loanApplicationService.applyForLoan(loanRequest));
        assertTrue(exception.getMessage().contains("Student Is Not Logged In"));
        assertThat(loanApplicationService.getAllLoanApplication().size()).isEqualTo(0);
    }
}