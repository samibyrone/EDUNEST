package com.semicolon.africa.controller;


import com.semicolon.africa.dtos.Request.LoanApplicationRequest;
import com.semicolon.africa.dtos.Response.LoanApplicationResponse;
import com.semicolon.africa.service.LoanApplicationService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/map")
@AllArgsConstructor
public class LoanApplicationController {

    @Autowired
    private LoanApplicationService loanApplicationService;

    @PostMapping("/apply4Loan")
    public ResponseEntity<?> applyForLoan(@RequestBody LoanApplicationRequest loanApplicationRequest) {
        LoanApplicationResponse loanResponse = loanApplicationService.applyForLoan(loanApplicationRequest);
        return ResponseEntity.ok(loanResponse);
    }

}
