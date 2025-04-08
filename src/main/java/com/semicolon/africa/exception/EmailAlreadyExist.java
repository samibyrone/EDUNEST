package com.semicolon.africa.exception;

public class EmailAlreadyExist extends RuntimeException {
        public EmailAlreadyExist(String message) {
            super(message);
    }
}
