package com.semicolon.africa.service;

import com.semicolon.africa.data.model.LOAN_STATUS;
import com.semicolon.africa.data.model.LoanApplication;
import com.semicolon.africa.data.model.LoanPolicy;
import com.semicolon.africa.data.model.Student;
import com.semicolon.africa.data.repositories.LoanApplicationRepository;
import com.semicolon.africa.data.repositories.LoanPolicyRepository;
import com.semicolon.africa.data.repositories.StudentRepository;
import com.semicolon.africa.exception.LoanApplicationNotFoundException;
import com.semicolon.africa.exception.PolicyNotFoundException;
import com.semicolon.africa.exception.StudentNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoanApplicationServiceImplementation implements LoanApplicationService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private LoanApplicationRepository loanApplicationRepository;

    @Autowired
    private LoanPolicyRepository loanPolicyRepository;

    public LoanApplicationRepository applyForLoan(Long studentId, LoanApplication application, Long policyId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow( () -> new StudentNotFoundException("Student can not found"));

        LoanPolicy policy = loanPolicyRepository.findById(policyId)
                .orElseThrow( () -> new PolicyNotFoundException(" Student Policy can not be found"));

        application.setStudent(student);
        application.setLoanPolicy(policy);
        application.setStatus(LOAN_STATUS.PENDING);
        application.setLoanAmount(BigDecimal.ZERO);

        return (LoanApplicationRepository) loanApplicationRepository.save(application);
    }

    public List<LoanApplication> getStudentApplications(Long studentId) {
        return loanApplicationRepository.findByStudentId(studentId);
    }

    public Optional<LoanApplication> getLoanApplicationById(Long loanApplicationId) {
        return loanApplicationRepository.findById(loanApplicationId);
    }

    public LoanApplication updateLoanStatus(Long loanApplicationId, LOAN_STATUS status) {
        LoanApplication application = loanApplicationRepository.findById(loanApplicationId)
                .orElseThrow( () -> new LoanApplicationNotFoundException("Loan Application Not Found"));
        application.setStatus(status);
        return loanApplicationRepository.save(application);
    }

    public BigDecimal LoanAmount(LoanPolicy policy) {
        BigDecimal schoolFees = policy.getBaseAmount();
        BigDecimal monthlyUpkeep = schoolFees.multiply(policy.);
        return monthlyUpkeep.add(monthlyUpkeep);
    }
}

