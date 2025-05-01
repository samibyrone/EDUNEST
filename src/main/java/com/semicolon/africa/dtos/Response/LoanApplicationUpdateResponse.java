package com.semicolon.africa.dtos.Response;

import com.semicolon.africa.data.model.LOAN_STATUS;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class LoanApplicationUpdateResponse {

    @Id
    private Long studentId;
    private Long loanApplicationId;
    private LOAN_STATUS status;
    private String message;
}
