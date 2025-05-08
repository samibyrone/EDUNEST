package com.semicolon.africa.service;

import com.semicolon.africa.data.model.*;
import com.semicolon.africa.data.repositories.AdminRepository;
import com.semicolon.africa.data.repositories.LoanApplicationRepository;
import com.semicolon.africa.data.repositories.LoanPolicyRepository;
import com.semicolon.africa.dtos.Response.LoanApplicationResponse;
import com.semicolon.africa.dtos.Response.LoanApplicationUpdateResponse;
import com.semicolon.africa.exception.LoanAmountException;
import com.semicolon.africa.exception.PolicyNotFoundException;
import com.semicolon.africa.utils.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminServiceImplementation implements AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private Mapper mapper;

    @Autowired
    private LoanApplicationRepository loanApplicationRepository;

    @Autowired
    private LoanPolicyRepository loanPolicyRepository;

    @Autowired
    private VerificationService verification;

    @Override
    public List<Admin> getAllUsers() {
        return adminRepository.findAll();
    }

    @Override
    public Optional<Admin> getAdminById(Long id) {
        return adminRepository.findById(id);
    }

    @Override
    public Optional<Admin> findByUserName(String userName) {
        return adminRepository.findByUserName(userName);
    }

    @Override
    public LoanApplicationResponse approveLoan(Long LoanApplicationId, Verification verification) {
        LoanApplication loanApplication = loanApplicationRepository.findById(LoanApplicationId)
                .orElseThrow(() -> new RuntimeException("LoanApplication not found"));

        BigDecimal total = null;
        BigDecimal upkeep = null;
        if (verification.getStatus() != VERIFICATION_STATUS.VERIFIED) {
            loanApplication.setStatus(LOAN_STATUS.REJECTED);
        } else {
            BigDecimal schoolFees = BigDecimal.valueOf(verification.getVerifiedSchoolFees());
            upkeep = BigDecimal.valueOf(verification.getVerifiedMonthlyUpkeep());
            int duration = loanApplication.getLoanDurationMonths();

            total = schoolFees.add(upkeep.multiply(BigDecimal.valueOf(duration)));

            LoanPolicy policy = loanPolicyRepository.findActivePolicy()
                    .orElseThrow(() -> new PolicyNotFoundException("Loan Policy not found"));

            if (total.compareTo(policy.getMinAmount()) < 0 || total.compareTo(policy.getMaxAmount()) > 0) {
                throw new LoanAmountException("Loan");
            }
        }

        loanApplication.setLoanAmount(total);
        loanApplication.setMonthlyUpkeep(upkeep);
        loanApplication.setStatus(LOAN_STATUS.APPROVED);

        return mapper.mapLoanApplication(loanApplicationRepository.save(loanApplication));
    }

    @Override
    public LoanApplicationUpdateResponse rejectLoan(LoanApplication loanApplication) {
        loanApplication.setStatus(LOAN_STATUS.REJECTED);

        return null;
    }
}
