package com.semicolon.africa.exception;

public class InvalidLoanAmountException extends RuntimeException {
    public InvalidLoanAmountException(String massage) {
        super(massage);
    }
}
