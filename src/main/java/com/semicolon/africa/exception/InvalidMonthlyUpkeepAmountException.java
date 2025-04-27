package com.semicolon.africa.exception;

public class InvalidMonthlyUpkeepAmountException extends RuntimeException {
    public InvalidMonthlyUpkeepAmountException(String message) {
        super(message);
    }
}
