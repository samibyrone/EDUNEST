package com.semicolon.africa.controller;

import com.semicolon.africa.data.model.LoanApplication;
import com.semicolon.africa.data.model.STUDENT_TYPE;
import com.semicolon.africa.data.model.Student;
import com.semicolon.africa.data.repositories.LoanApplicationRepository;
import com.semicolon.africa.data.repositories.StudentRepository;
import com.semicolon.africa.dtos.Request.LoanApplicationRequest;
import com.semicolon.africa.dtos.Response.LoanApplicationResponse;
import com.semicolon.africa.service.LoanApplicationService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class LoanApplicationControllerTest {

    @Autowired
    private LoanApplicationController loanController;

    @Autowired
    private LoanApplicationRepository loanApplicationRepository;

   @Autowired
   private TestRestTemplate restTemplate;

   private Student student;

    @Autowired
    private StudentRepository studentRepository;

    @AfterEach
    public void resetRepository() {
        studentRepository.deleteAll();
        loanApplicationRepository.deleteAll();
    }

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

    }

    @Test
    public void testThatReturnCreatedWhenApplicationIsCreated() throws Exception {
        LoanApplicationRequest loanRequest = new LoanApplicationRequest();

            loanRequest.setStudentId(12345L);
            loanRequest.setLoanAmount(BigDecimal.valueOf(120000));
            loanRequest.setMonthlyUpkeep(BigDecimal.valueOf(20000));
            loanRequest.setLoanDurationMonths(48);
            loanRequest.setApplicationDate(LocalDateTime.now());

        ResponseEntity<LoanApplicationResponse> loanResponse = restTemplate.postForEntity("/api/v1/loans/apply4Loan", loanRequest, LoanApplicationResponse.class);

        assertEquals(HttpStatus.CREATED, loanResponse.getStatusCode());
        assertThat(loanResponse.getBody()).isNotNull();
        assertThat(loanResponse.getBody().getLoanApplicationId()).isNotNull();
        assertThat(loanResponse.getBody().getMessage()).contains("Loan Application Submitted Successfully");

        List<LoanApplication> applications = loanApplicationRepository.findAll();
        assertThat(applications.size()).isEqualTo(1);
        assertThat(applications.get(0).getStudent().getId()).isEqualTo(12345L);
    }
}