package com.semicolon.africa.exception;

public class InvalidPolicyRequestException extends RuntimeException {
    public InvalidPolicyRequestException(String message){
        super(message);
    }
}
