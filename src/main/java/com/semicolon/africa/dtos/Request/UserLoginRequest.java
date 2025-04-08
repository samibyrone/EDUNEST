package com.semicolon.africa.dtos.Request;

import lombok.Data;

@Data
public class UserLoginRequest {

    private String email;
    private String password;
}
