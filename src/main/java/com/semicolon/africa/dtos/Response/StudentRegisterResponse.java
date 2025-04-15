package com.semicolon.africa.dtos.Response;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class StudentRegisterResponse {

    @Id
    private Long id;
    private String message;
    private String email;
}
