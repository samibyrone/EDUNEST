package com.semicolon.africa.service;

import com.semicolon.africa.data.model.LoanApplication;
import com.semicolon.africa.data.model.Verification;
import com.semicolon.africa.dtos.Request.LoanApplicationRequest;
import com.semicolon.africa.dtos.Response.LoanApplicationResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface LoanApplicationService {

    LoanApplicationResponse applyForLoan(LoanApplicationRequest loanApplicationRequest);

    List<LoanApplication> getStudentApplications(Long studentId);

    Optional<LoanApplication> getLoanApplicationById(Long loanApplicationId);

    LoanApplicationResponse updateLoanStatus(Long loanApplicationId, Verification verification);
//    LoanApplicationUpdateResponse updateLoanStatus(Long loanApplicationId, LoanApplicationUpdateRequest loanApplicationUpdateRequest);

    double calculateLoanAmount(LoanApplication application, Verification verify );

    List<LoanApplication> getAllLoanApplication();
}
