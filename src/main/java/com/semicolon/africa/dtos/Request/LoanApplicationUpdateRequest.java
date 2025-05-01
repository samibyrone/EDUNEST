package com.semicolon.africa.dtos.Request;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class LoanApplicationUpdateRequest {

    @Id
    private Long loanApplicationId;
    private Long studentId;
    private BigDecimal loanAmount;
    private BigDecimal monthlyUpkeep;
    private int loanDurationMonths;
    private LocalDateTime applicationDate;
}
