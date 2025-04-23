package com.semicolon.africa.data.repositories;

import com.semicolon.africa.data.model.LoanApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LoanApplicationRepository extends JpaRepository<LoanApplication, String> {

    List<LoanApplication> findByStudentId(Long studentId);

    Optional<LoanApplication> findById(Long loanId);
}
