package com.semicolon.africa.service;

import com.semicolon.africa.data.model.LoanApplication;
import com.semicolon.africa.data.model.Student;
import com.semicolon.africa.service.NotifyService.EmailService;
import com.semicolon.africa.service.NotifyService.SMSService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationServiceImplementation implements NotificationService{

    @Autowired
    private EmailService emailService;

    @Autowired
    private SMSService smsService;

    public void sendLoanApprovalNotification(Student student, LoanApplication loanApplication) {
        String message = String.format("Hi %s, Your loan of $%.2f Has Been Approved",
                student.getFirstName(), loanApplication.getLoanAmount());
        emailService.send(student.getEmail(), "Your Loan Application Request Has Been Approved", message);
        smsService.send(student.getPhoneNumber(), "Your Loan Application Request Has Been Approved", message);
    }

    public void sendLoanRejectionNotification(Student student, String rejectionReason) {
        String message = String.format("Hi %s, Your loan Was Been Rejected. Reasons: %s",
                student.getFirstName(), rejectionReason);
        emailService.send(student.getEmail(), "Your Loan Application Was Rejected. ", message);
    }
}