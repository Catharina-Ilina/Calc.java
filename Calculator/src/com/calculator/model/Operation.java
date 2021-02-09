package com.calculator.model;

public enum Operation {

    PLUS("+"), MINUS("-"), MULTIPLY("*"), DIVIDE("/");

    private final String value;

    Operation(String value) {
        this.value = value;
    }

    public static Operation fromValue(String v) {
        return valueOf(v);
    }
}