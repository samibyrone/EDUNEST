package com.semicolon.africa.data.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum LOAN_STATUS {

    PENDING(0),
    APPROVED(1),
    REJECTED(2);

    private final int value;

    LOAN_STATUS(int value) {
        this.value = value;
    }

    @JsonValue
    public int getValue() {
        return value;
    }

    @JsonCreator
    public static LOAN_STATUS fromValue(int value) {
        return Arrays.stream(values())
                .filter(status -> status.value == value)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid status value: "));
    }
}
