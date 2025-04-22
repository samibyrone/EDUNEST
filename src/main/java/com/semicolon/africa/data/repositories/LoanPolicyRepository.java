package com.semicolon.africa.data.repositories;

import com.semicolon.africa.data.model.LoanPolicy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanPolicyRepository extends JpaRepository<LoanPolicy, String> {
}
