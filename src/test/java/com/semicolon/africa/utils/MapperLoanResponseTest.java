package com.semicolon.africa.utils;

import com.semicolon.africa.data.model.LOAN_STATUS;
import com.semicolon.africa.data.model.LoanApplication;
import com.semicolon.africa.data.model.Student;
import com.semicolon.africa.dtos.Response.LoanApplicationResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class MapperLoanResponseTest {

    private static LoanApplication loanApplication;
    private static Student student;

    @BeforeAll
    public static void setup() {
        student = new Student();
        student.setId(1234L);

        loanApplication = new LoanApplication();
        loanApplication.setStudent(student);
    }

    @Test
    public void mapLoanApplicationToStudentResponseTest() {
        LoanApplicationResponse loanResponse = Mapper.mapLoanApplication(loanApplication);

        assertNotNull(loanResponse);
        assertEquals(1234L, loanResponse.getStudentId());
        assertEquals(LOAN_STATUS.PENDING, loanResponse.getStatus());
        assertEquals(loanResponse.getMessage(), "Loan Application Submitted Successfully");
    }

}