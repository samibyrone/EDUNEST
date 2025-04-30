package com.semicolon.africa.exception;

public class InvalidStudentLoginInRequestException extends RuntimeException {
    public InvalidStudentLoginInRequestException(String message) {
        super(message);
    }
}
