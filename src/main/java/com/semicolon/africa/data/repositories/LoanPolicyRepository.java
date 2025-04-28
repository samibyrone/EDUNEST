package com.semicolon.africa.data.repositories;

import com.semicolon.africa.data.model.LoanPolicy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoanPolicyRepository extends JpaRepository<LoanPolicy, String> {
//     findActivePolicy();

     Optional<LoanPolicy> findById(Long id);

     @Query("SELECT p FROM LoanPolicy p WHERE p.isActive = true")
     Optional<LoanPolicy> findActivePolicy();


}
