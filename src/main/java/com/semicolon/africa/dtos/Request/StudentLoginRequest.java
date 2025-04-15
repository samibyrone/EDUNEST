package com.semicolon.africa.dtos.Request;

import lombok.Data;

@Data
public class StudentLoginRequest {

    private String email;
    private String password;
}
