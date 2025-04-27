package com.semicolon.africa.exception;

public class NoActivePolicyException extends RuntimeException {
    public NoActivePolicyException(String message) {
        super(message);
    }
}
