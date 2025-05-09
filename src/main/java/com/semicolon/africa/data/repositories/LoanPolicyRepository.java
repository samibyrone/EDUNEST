package com.semicolon.africa.data.repositories;

import com.semicolon.africa.data.model.LoanPolicy;
import com.semicolon.africa.data.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface LoanPolicyRepository extends JpaRepository<LoanPolicy, String> {
//     findActivePolicy();

     Optional<LoanPolicy> findById(Long id);

     Optional<LoanPolicy> findActivePolicy();

     boolean checkEligibility(Student student, BigDecimal amountRequested);

}
