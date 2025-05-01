package com.semicolon.africa.controller;


import com.semicolon.africa.data.model.LoanApplication;
import com.semicolon.africa.dtos.Request.LoanApplicationRequest;
import com.semicolon.africa.dtos.Response.LoanApplicationResponse;
import com.semicolon.africa.exception.LoanApplicationNotFoundException;
import com.semicolon.africa.service.LoanApplicationService;
import com.semicolon.africa.utils.Mapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/loans")
@AllArgsConstructor
public class LoanApplicationController {

    @Autowired
    private LoanApplicationService loanApplicationService;

    @Autowired
    private Mapper mapper;

    @Autowired
    public LoanApplicationController(LoanApplicationService loanApplicationService) {
        this.loanApplicationService = loanApplicationService;
    }

    @PostMapping("/apply4Loan")
    @ManagedOperation(description = "Submit a New Loan Application")
    public ResponseEntity<LoanApplicationResponse> applyForLoan(@RequestBody LoanApplicationRequest loanApplicationRequest) {
        LoanApplicationResponse loanResponse = loanApplicationService.applyForLoan(loanApplicationRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(loanResponse);
    }

    @GetMapping("/{loanApplicationId}")
    @ManagedOperation(description = "Get Detailed Information About A Loan Application")
    public ResponseEntity<LoanApplicationResponse> getLoanApplicationById(@PathVariable Long loanApplicationId) {
        return ResponseEntity.ok(loanApplicationService.getLoanApplicationById(loanApplicationId)
                .map(mapper::mapLoanApplication)
                .orElseThrow( () -> new LoanApplicationNotFoundException("")));
    }

    @GetMapping("/student/{studentId}")
    @ManagedOperation(description = "Get All Loan Application For A Student")
    public ResponseEntity<List<LoanApplicationResponse>> getStudentLoans(@PathVariable Long studentId) {
        List<LoanApplication> applications = loanApplicationService.getStudentApplications(studentId);
        return ResponseEntity.ok(applications.stream()
                .map(mapper::mapLoanApplication)
                .toList());
    }
}
