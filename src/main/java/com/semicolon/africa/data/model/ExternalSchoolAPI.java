package com.semicolon.africa.data.model;

import org.springframework.stereotype.Component;

@Component
public class ExternalSchoolAPI {

    public boolean verifyStudentEnrollment(Long studentId, String schoolName){
        return studentId != null && schoolName != null && studentId > 0 && schoolName.length() > 3;
    }

    public Verification callSchoolEnrollmentAPI(Long id) {
        return null;
    }
}
