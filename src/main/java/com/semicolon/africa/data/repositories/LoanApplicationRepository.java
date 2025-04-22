package com.semicolon.africa.data.repositories;

import com.semicolon.africa.data.model.LoanApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanApplicationRepository extends JpaRepository<LoanApplication, String> {
}
