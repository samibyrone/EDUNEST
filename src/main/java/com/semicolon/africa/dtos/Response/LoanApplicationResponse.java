package com.semicolon.africa.dtos.Response;

import com.semicolon.africa.data.model.LOAN_STATUS;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class LoanApplicationResponse {

    @Id
    private long loanApplicationId;
    private LOAN_STATUS status;
    private String email;
    private String message;
}
