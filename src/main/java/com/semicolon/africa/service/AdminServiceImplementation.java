package com.semicolon.africa.service;

import com.semicolon.africa.data.model.LOAN_STATUS;
import com.semicolon.africa.data.model.LoanApplication;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImplementation implements AdminService {

    public void approveLoan(LoanApplication loanApplication) {
        loanApplication.setStatus(LOAN_STATUS.APPROVED);
    }

    public void rejectLoan(LoanApplication loanApplication) {
        loanApplication.setStatus(LOAN_STATUS.REJECTED);
    }
}
