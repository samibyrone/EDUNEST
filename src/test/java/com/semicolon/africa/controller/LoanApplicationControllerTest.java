//package com.semicolon.africa.controller;
//
//import com.semicolon.africa.configuration.JwtService;
//import com.semicolon.africa.configuration.SecurityConfigurationTest;
//import com.semicolon.africa.data.model.LoanApplication;
//import com.semicolon.africa.data.model.LoanPolicy;
//import com.semicolon.africa.data.model.STUDENT_TYPE;
//import com.semicolon.africa.data.model.Student;
//import com.semicolon.africa.data.repositories.LoanApplicationRepository;
//import com.semicolon.africa.data.repositories.LoanPolicyRepository;
//import com.semicolon.africa.data.repositories.StudentRepository;
//import com.semicolon.africa.dtos.Request.LoanApplicationRequest;
//import com.semicolon.africa.dtos.Response.LoanApplicationResponse;
//import com.semicolon.africa.service.LoanApplicationService;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.context.TestConfiguration;
//import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Import;
//import org.springframework.http.*;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.TestPropertySource;
//
//import java.math.BigDecimal;
//import java.time.LocalDateTime;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@ActiveProfiles("test")
//@Import(SecurityConfigurationTest.class)
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@TestPropertySource(properties = "spring.main.allow-bean-definition-overriding=true")
//public class LoanApplicationControllerTest {
//
//    @Autowired
//    private LoanPolicyRepository loanPolicyRepository;
//
//    @Autowired
//    private LoanApplicationRepository loanApplicationRepository;
//
//    @Autowired
//    private TestRestTemplate restTemplate;
//
//    @Autowired
//    private StudentRepository studentRepository;
//
//    @Autowired
//    private JwtService jwtService;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    private Student student;
//    private LoanPolicy policy;
//
//    @AfterEach
//    public void resetRepository() {
//        studentRepository.deleteAll();
//        loanPolicyRepository.deleteAll();
//        loanApplicationRepository.deleteAll();
//    }
//
//    @BeforeEach
//    public void setUp() {
//        student = new Student();
//        student.setFirstName("sammy");
//        student.setLastName("kent");
//        student.setUserName("sam");
//        student.setEmail("sammy@email.com");
//        student.setPhoneNumber("08100059657");
//        student.setAddress("sabo yaba bus-stop");
//        student.setPassword(passwordEncoder.encode("sammy@12345"));
//        student.setLoggedIn(true);
//        student.setRole(STUDENT_TYPE.TERTIARY_SCHOOL_STUDENT);
//        studentRepository.save(student);
//
//        policy = LoanPolicy.builder()
//                .minAmount(BigDecimal.valueOf(50000))
//                .maxAmount(BigDecimal.valueOf(200000))
//                .maxMonthlyUpkeep(BigDecimal.valueOf(20000))
//                .isActive(true)
//                .policyName("Education Loan Policy")
//                .policyDescription("School Fees")
//                .build();
//        loanPolicyRepository.save(policy);
//    }
//
//    @TestConfiguration
//    static class TestSecurityBeans {
//
//        @Bean
//        PasswordEncoder passwordEncoder() {
//            return new BCryptPasswordEncoder();
//        }
//    }
//
//    @Test
//    public void testThatReturnCreatedWhenApplicationIsCreated() throws Exception {
//
//        Map<String, Object> claims = new HashMap<>();
//        claims.put("sub", student.getUsername());
//        claims.put("authorities", List.of("ROLE_STUDENT"));
//
//        String token = jwtService.generateToken(claims, student);
//        HttpHeaders headers = new HttpHeaders();
//        headers.setBearerAuth(token);
//        headers.setContentType(MediaType.APPLICATION_JSON);
//
//        LoanApplicationRequest loanRequest = new LoanApplicationRequest();
//            loanRequest.setStudentId(student.getId());
//            loanRequest.setLoanAmount(BigDecimal.valueOf(120000));
//            loanRequest.setMonthlyUpkeep(BigDecimal.valueOf(20000));
//            loanRequest.setLoanDurationMonths(48);
//            loanRequest.setApplicationDate(LocalDateTime.now());
//
////        HttpEntity<LoanApplicationRequest> requestEntity = new HttpEntity<>(loanRequest, headers);
//
//        ResponseEntity<LoanApplicationResponse> loanResponse = restTemplate.exchange("/api/v1/loans/apply4Loan", HttpMethod.POST, new HttpEntity<>(loanRequest, headers), LoanApplicationResponse.class);
//
//        assertEquals(HttpStatus.CREATED, loanResponse.getStatusCode());
//        assertNotNull(loanResponse.getBody());
//        assertNotNull(loanResponse.getBody().getLoanApplicationId());
//        assertThat(loanResponse.getBody().getMessage()).contains("Loan Application Submitted Successfully");
//
//        List<LoanApplication> applications = loanApplicationRepository.findAll();
//        assertThat(applications.size()).isEqualTo(1);
//        LoanApplication application = applications.get(0);
//        assertThat(application.getStudent().getId()).isEqualTo(student.getId());
//        assertThat(application.getLoanAmount()).isEqualTo(BigDecimal.valueOf(120000));
//        assertThat(application.getMonthlyUpkeep()).isEqualTo(BigDecimal.valueOf(20000));
//        assertThat(application.getLoanDurationMonths()).isEqualTo(48);
//    }
//}