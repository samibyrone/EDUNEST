package com.semicolon.africa.service;

import com.semicolon.africa.data.model.Document;
import com.semicolon.africa.data.model.LoanApplication;
import com.semicolon.africa.data.model.Student;
import com.semicolon.africa.data.model.Verification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VerificationServiceImplementation implements VerificationService {


    @Override
    public Verification performFullverification(LoanApplication loanApplication, List<Document> documents) {
        return null;
    }

    @Override
    public Verification verifyEnrolment(Student student) {
        return null;
    }

    @Override
    public Verification validateDocument(Document document) {
        return null;
    }

    @Override
    public Verification callExternalSchoolAPI(Student student, Document document) {
        return null;
    }
}
