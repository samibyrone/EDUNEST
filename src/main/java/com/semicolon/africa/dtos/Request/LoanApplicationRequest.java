package com.semicolon.africa.dtos.Request;

import com.semicolon.africa.data.model.Student;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class LoanApplicationRequest {
    private Long LoanApplicationId;
    private Long studentId;
    private BigDecimal loanAmount;
    private BigDecimal monthlyUpkeep;
    private int loanDurationMonths;
    private LocalDateTime applicationDate;
}
