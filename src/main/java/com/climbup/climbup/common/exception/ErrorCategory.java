package com.climbup.climbup.common.exception;

public enum ErrorCategory {
    COMMON("COMMON"),
    AUTH("AUTH"),
    USER("USER"),
    SESSION("SESSION"),
    GYM("GYM"),
    VALIDATION("VALIDATION"),
    REQUEST("REQUEST"),
    RESOURCE("RESOURCE"),
    BUSINESS("BUSINESS");

    private final String prefix;

    ErrorCategory(String prefix) {
        this.prefix = prefix;
    }

    public String getPrefix() {
        return prefix;
    }
}