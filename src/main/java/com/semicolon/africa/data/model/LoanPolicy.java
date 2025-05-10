package com.semicolon.africa.data.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "loan_policy")
public class LoanPolicy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String policyName;
    private String policyDescription;
    private BigDecimal maxAmount;
    private BigDecimal minAmount;
    private BigDecimal maxMonthlyUpkeep;
    private String eligibilityLevel;
    private boolean isActive;

    @ManyToMany
    private List<LoanApplication> loanApplications;

}
