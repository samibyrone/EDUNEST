package com.semicolon.africa.dtos.Response;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class UserLoginResponse {

    @Id
    private Long id;
    private String message;
    private String email;
    private boolean isLoggedIn;
}
