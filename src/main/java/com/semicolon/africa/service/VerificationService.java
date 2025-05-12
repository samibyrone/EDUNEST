package com.semicolon.africa.service;

import com.semicolon.africa.data.model.Document;
import com.semicolon.africa.data.model.LoanApplication;
import com.semicolon.africa.data.model.Student;
import com.semicolon.africa.data.model.Verification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface VerificationService {

    Verification performFullverification(LoanApplication loanApplication, List<Document> documents);

    Optional<Verification> verifyEnrolment(Student student);

    Boolean validateDocument(LoanApplication loanApplication);

    Verification callExternalSchoolAPI(Student student, Document document);
}
