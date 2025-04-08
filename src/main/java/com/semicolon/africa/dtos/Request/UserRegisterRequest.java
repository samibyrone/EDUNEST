package com.semicolon.africa.dtos.Request;

import lombok.Data;

@Data
public class UserRegisterRequest {

    private String firstName;
    private String lastName;
    private String userName;
    private String address;
    private String phoneNumber;
    private String email;
    private String password;
}
