package com.semicolon.africa.data.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
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
public class LoanPolicy {

    @Id
    @GeneratedValue
    private Long id;
    private String policyName;
    private String policyDescription;
    private BigDecimal maxAmount;
    private String eligibilityLevel;

    @ManyToMany
    private List<LoanApplication> loanApplications;

}
