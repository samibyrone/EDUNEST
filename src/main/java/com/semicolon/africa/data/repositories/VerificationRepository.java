package com.semicolon.africa.data.repositories;

import com.semicolon.africa.data.model.Document;
import com.semicolon.africa.data.model.Student;
import com.semicolon.africa.data.model.Verification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationRepository extends JpaRepository<Verification, String> {

    boolean verifyEnrolment(Student student);

    boolean validateStudentDocument(Document document);

    boolean callExternalSchoolAPI(Long studentId);
}
