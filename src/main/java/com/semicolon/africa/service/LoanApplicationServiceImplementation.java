package com.semicolon.africa.service;

import com.semicolon.africa.data.model.*;
import com.semicolon.africa.data.repositories.LoanApplicationRepository;
import com.semicolon.africa.data.repositories.LoanPolicyRepository;
import com.semicolon.africa.data.repositories.StudentRepository;
import com.semicolon.africa.dtos.Request.LoanApplicationRequest;
import com.semicolon.africa.dtos.Response.LoanApplicationResponse;
import com.semicolon.africa.exception.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static com.semicolon.africa.utils.Mapper.mapLoanApplication;

@Service
@RequiredArgsConstructor
public class LoanApplicationServiceImplementation implements LoanApplicationService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private LoanApplicationRepository loanApplicationRepository;

    @Autowired
    private LoanPolicyRepository loanPolicyRepository;

    public List<LoanApplication> getStudentApplications(Long studentId) {
        return loanApplicationRepository.findByStudentId(studentId);
    }

    public Optional<LoanApplication> getLoanApplicationById(Long loanApplicationId) {
        return loanApplicationRepository.findById(loanApplicationId);
    }

    @Override
    public LoanApplicationResponse applyForLoan(LoanApplicationRequest loanRequest) {
        Student student = studentRepository.findById(loanRequest.getStudentId())
                .orElseThrow(() -> new StudentNotFoundException("Student not found"));

        if(loanApplicationRepository.existsByStudentAndStatus(student, LOAN_STATUS.PENDING)) {
            throw new DuplicateApplicationException("Pending Application Already Exists!");
        }

        LoanApplication application = new LoanApplication();
            mapLoanApplication(loanRequest, application);
            application.setStudent(student);
            application.setStatus(LOAN_STATUS.PENDING);
            application.setMonthlyUpkeep(BigDecimal.ZERO);
            application.setLoanAmount(BigDecimal.ZERO);

        LoanApplication savedApplication = loanApplicationRepository.save(application);
        return mapLoanApplication(savedApplication);
    }

    @Override
    @Transactional
    public LoanApplicationResponse updateLoanStatus(Long loanApplicationId, Verification verification) {
        LoanApplication application = loanApplicationRepository.findById(loanApplicationId)
                .orElseThrow( () -> new LoanApplicationNotFoundException("Loan Application Not Found"));

        double totalAmount = calculateLoanAmount(application, verification);

            if (verification.getStatus() == VERIFICATION_STATUS.VERIFIED) {
                application.setStatus(LOAN_STATUS.APPROVED);
                application.setLoanAmount(BigDecimal.valueOf(totalAmount));
                application.setMonthlyUpkeep(BigDecimal.valueOf(verification.getVerifiedMonthlyUpkeep()));
            } else  {
                application.setStatus(LOAN_STATUS.REJECTED);
            }
        LoanApplication updatedApplication = loanApplicationRepository.save(application);
        return mapLoanApplication(updatedApplication);
    }


    @Override
    public double calculateLoanAmount(LoanApplication application, Verification verify) {
       double schoolFees = verify.getVerifiedSchoolFees();
       double monthlyUpkeep = verify.getVerifiedMonthlyUpkeep();
       int monthDurations = application.getLoanDurationMonths();

       double total = schoolFees + monthlyUpkeep * monthDurations;

       LoanPolicy policy = loanPolicyRepository.findActivePolicy()
               .orElseThrow( () -> new PolicyNotFoundException("Active Loan Policy Not Found"));

        if(total.compareTo(policy.getMinAmount() < 0)) {
            throw new LoanAmountException("Total amount (" + total + ") is below the minimum allowed (" + policy.getMinAmount() + ")");
        }

        if (total > policy.getMaxAmount()) {
            throw new LoanAmountException("Total amount (" + total + ") is below the minimum allowed (" + policy.getMaxAmount() + ")");
        }
        return total;
    }
}

