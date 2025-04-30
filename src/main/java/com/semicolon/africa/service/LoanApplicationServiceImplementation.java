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
import java.time.LocalDateTime;
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


    @Override
    public List<LoanApplication> getStudentApplications(Long studentId) {
        return loanApplicationRepository.findByStudentId(studentId);
    }

    @Override
    public Optional<LoanApplication> getLoanApplicationById(Long loanApplicationId) {
        return loanApplicationRepository.findById(loanApplicationId);
    }

    @Override
    public List<LoanApplication> getAllLoanApplication() {
        return loanApplicationRepository.findAll();
    }

    @Override
    public LoanApplicationResponse applyForLoan(LoanApplicationRequest loanRequest) {
        Student student = studentRepository.findById(loanRequest.getStudentId())
                .orElseThrow( () -> new StudentNotFoundException("Student not found"));

        if (!student.isLoggedIn()) {
            throw new InvalidStudentLoginInRequestException("Student In Not Logged In");
        }

        LoanPolicy activeLoanPolicy = loanPolicyRepository.findActivePolicy()
                .orElseThrow( () -> new NoActivePolicyException("No Active Loan Policy"));

        if(loanApplicationRepository.existsByStudentAndStatus(student, LOAN_STATUS.PENDING)) {
            throw new DuplicateApplicationException("Pending Application Already Exists!");
        }

        validateLoanAmount(loanRequest, activeLoanPolicy);

        LoanApplication application = new LoanApplication();
            mapLoanApplication(loanRequest, application, student);
            application.setLoanPolicy(activeLoanPolicy);
            application.setStatus(LOAN_STATUS.PENDING);
            application.setApplicationDate(LocalDateTime.now());

        LoanApplication savedApplication = loanApplicationRepository.save(application);
        return mapLoanApplication(savedApplication);
    }

    private void validateLoanAmount(LoanApplicationRequest loanRequest, LoanPolicy activeLoanPolicy) {
        if (loanRequest.getLoanAmount().compareTo(activeLoanPolicy.getMinAmount()) < 0  ||
                loanRequest.getLoanAmount().compareTo(activeLoanPolicy.getMaxAmount()) > 0) {
            throw new InvalidLoanAmountException("Loan Amount Exceeded The Loan Policy Limits");
        }
        if (loanRequest.getMonthlyUpkeep().compareTo(activeLoanPolicy.getMaxMonthlyUpkeep()) > 0) {
            throw new InvalidMonthlyUpkeepAmountException("Monthly Upkeep Amount Exceeded The Loan Policy Limits");
        }
    }
//
//    @Override
//    @Transactional
//    public LoanApplicationResponse updateLoanStatus(Long loanApplicationId, Verification verification) {
//        LoanApplication application = loanApplicationRepository.findById(loanApplicationId)
//                .orElseThrow( () -> new LoanApplicationNotFoundException("Loan Application Not Found"));
//
//
//        if (verification.getStatus() == VERIFICATION_STATUS.VERIFIED) {
//            BigDecimal totalAmount = calculateLoanAmount(application, verification);
//            application.setStatus(LOAN_STATUS.APPROVED);
//                application.setLoanAmount(BigDecimal.valueOf(totalAmount));
//                application.setMonthlyUpkeep(BigDecimal.valueOf(verification.getVerifiedMonthlyUpkeep()));
//            } else  {
//                application.setStatus(LOAN_STATUS.REJECTED);
//            }
//        LoanApplication updatedApplication = loanApplicationRepository.save(application);
//        return mapLoanApplication(updatedApplication);
//    }
//
////
////    @Override
////    public BigDecimal calculateLoanAmount(LoanApplication application, Verification verify) {
////       BigDecimal schoolFees = BigDecimal.valueOf(verify.getVerifiedSchoolFees());
////       BigDecimal monthlyUpkeep = BigDecimal.valueOf(verify.getVerifiedMonthlyUpkeep());
//       int monthDurations = application.getLoanDurationMonths();
//
//       BigDecimal total = schoolFees + monthlyUpkeep * monthDurations;
//
//       LoanPolicy policy = loanPolicyRepository.findActivePolicy()
//               .orElseThrow( () -> new PolicyNotFoundException("Active Loan Policy Not Found"));
//
//       BigDecimal totalLoan = BigDecimal.valueOf(total);
//       BigDecimal minAmount = policy.getMinAmount();
//       BigDecimal maxAmount = policy.getMaxAmount();
//
//        if(totalLoan.compareTo(minAmount) < 0) {
//            throw new LoanAmountException("Total amount (" + total + ") is below the minimum allowed (" + policy.getMinAmount() + ")");
//        }
//
//        if (totalLoan.compareTo(maxAmount) < 0) {
//            throw new LoanAmountException("Total amount (" + total + ") is below the minimum allowed (" + policy.getMaxAmount() + ")");
//        }
//        return total;
//    }

}

