package com.semicolon.africa.service;

import com.semicolon.africa.data.model.Admin;
import com.semicolon.africa.data.model.LOAN_STATUS;
import com.semicolon.africa.data.model.LoanApplication;
import com.semicolon.africa.data.repositories.AdminRepository;
import com.semicolon.africa.data.repositories.LoanApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminServiceImplementation implements AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private LoanApplicationRepository loanApplicationRepository;

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
    public LoanApplication approveLoan(Long LoanId, Admin admin) {
        LoanApplication loanApplication = loanApplicationRepository.findById(LoanId)
                .orElseThrow( () -> new RuntimeException("LoanApplication not found"));

        admin
        loanApplication.setStatus(LOAN_STATUS.APPROVED);
    }

    @Override
    public void rejectLoan(LoanApplication loanApplication) {
        loanApplication.setStatus(LOAN_STATUS.REJECTED);
    }
}
