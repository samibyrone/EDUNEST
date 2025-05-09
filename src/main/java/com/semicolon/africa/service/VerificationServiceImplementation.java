package com.semicolon.africa.service;

import com.semicolon.africa.data.model.*;
import com.semicolon.africa.data.repositories.DocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VerificationServiceImplementation implements VerificationService {

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private ExternalSchoolAPI externalSchoolAPI;
    @Autowired
    private DocumentServiceImplementation documentServiceImplementation;

    @Override
    public Verification performFullverification(LoanApplication loanApplication, List<Document> documents) {
        return null;
    }

    @Override
    public Verification verifyEnrolment(Student student) {
        return externalSchoolAPI.callSchoolEnrollmentAPI(student.getId());
    }

    @Override
    public Boolean validateDocument(LoanApplication loanApplication) {
        return loanApplication.getDocuments().stream()
                .allMatch(document -> {
                    boolean isDocValid = document.validateFormat();
                            document.setIsDocumentValid(isDocValid);
                            documentRepository.save(document);
                            return isDocValid;
                });
    }

    private boolean verifyStudent(Student student, Document document) {
        boolean isDocumentValid = documentServiceImplementation.validateFormat(document);
        boolean isEnrollmentValid = externalSchoolAPI.verifyStudentEnrollment(student.getId(), student.getSchool());
        return isDocumentValid && isEnrollmentValid;
    }

    @Override
    public Verification callExternalSchoolAPI(Student student, Document document) {
        return null;
    }
}
