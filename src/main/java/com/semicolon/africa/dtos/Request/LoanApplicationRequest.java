package com.semicolon.africa.dtos.Request;

import com.semicolon.africa.data.model.LOAN_STATUS;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class LoanApplicationRequest {

    @Id
    private Long studentId;
    private Long LoanApplicationId;
    private BigDecimal loanAmount;
    private BigDecimal monthlyUpkeep;
    private int loanDurationMonths;
    private LocalDateTime applicationDate;
}
