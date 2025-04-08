package com.semicolon.africa.exception;

public class WrongEmailOrPassword extends RuntimeException {
    public WrongEmailOrPassword(String message) {
        super(message);
    }
}
