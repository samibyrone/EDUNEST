package com.semicolon.africa.dtos.Response;

import com.semicolon.africa.data.model.LOAN_STATUS;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;

@Data
public class LoanApplicationResponse {

    @Id
    private long studentId;
    private long loanApplicationId;
    private LOAN_STATUS status;
    private BigDecimal loanAmount;
    private BigDecimal monthlyUpkeep;
    private String message;
}
