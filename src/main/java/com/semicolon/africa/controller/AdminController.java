//package com.semicolon.africa.controller;
//
//import com.semicolon.africa.data.model.LoanApplication;
//import com.semicolon.africa.data.model.Verification;
//import com.semicolon.africa.dtos.Request.LoanApplicationUpdateRequest;
//import com.semicolon.africa.dtos.Response.LoanApplicationResponse;
//import com.semicolon.africa.dtos.Response.LoanApplicationUpdateResponse;
//import com.semicolon.africa.service.AdminService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/v1/admin/loan")
//@RequiredArgsConstructor
//public class AdminController {
//
//    @Autowired
//    private AdminService adminService;
//
//    @PostMapping("/approve/{loanApplicationId}")
//    public ResponseEntity<LoanApplicationResponse> approveLoan (@PathVariable Long LoanApplicationId, @RequestBody Verification verification) {
//        LoanApplicationResponse loanResponse = adminService.approveLoan(LoanApplicationId, verification);
//                return ResponseEntity.status(HttpStatus.CREATED).body(loanResponse);
//    }
//
//    @PostMapping("/updateLoan/{}/status")
//    public ResponseEntity<LoanApplicationUpdateResponse> updateLoanApplication (@RequestBody LoanApplicationUpdateRequest updateRequest) {
//
//    }
//
//}
