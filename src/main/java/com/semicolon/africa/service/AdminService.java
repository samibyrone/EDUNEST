package com.semicolon.africa.service;

import com.semicolon.africa.data.model.Admin;
import com.semicolon.africa.data.model.LoanApplication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface AdminService {

    List<Admin> getAllUsers();

    Optional<Admin> getAdminById(Long id);

    Optional<Admin> findByUserName(String userName);

    LoanApplication approveLoan(Long LoanId, Admin admin);
}
