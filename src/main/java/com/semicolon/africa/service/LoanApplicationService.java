package com.semicolon.africa.service;

import com.semicolon.africa.data.model.LoanApplication;
import com.semicolon.africa.data.model.Verification;
import com.semicolon.africa.dtos.Request.LoanApplicationRequest;
import com.semicolon.africa.dtos.Request.LoanApplicationUpdateRequest;
import com.semicolon.africa.dtos.Response.LoanApplicationResponse;
import com.semicolon.africa.dtos.Response.LoanApplicationUpdateResponse;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public interface LoanApplicationService {

    List<LoanApplication> getAllLoanApplication();

    List<LoanApplication> getStudentApplications(Long studentId);

    Optional<LoanApplication> getLoanApplicationById(Long loanApplicationId);

    LoanApplicationResponse applyForLoan(LoanApplicationRequest loanApplicationRequest);

    BigDecimal calculateLoanAmount(LoanApplication application, Verification verify );

    LoanApplicationUpdateResponse updateLoanStatus(Long loanApplicationId, LoanApplicationUpdateRequest loanApplicationUpdateRequest);
}
