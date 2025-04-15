package com.semicolon.africa.dtos.Response;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class StudentLoginResponse {

    @Id
    private Long id;
    private String email;
    private String role;
    private String token;
    private String message;
    private boolean isLoggedIn;
}
