package com.climbup.climbup.user.exception;

public class UserOnboardingAlreadyCompleteException extends RuntimeException {
    public UserOnboardingAlreadyCompleteException(String message) {
        super(message);
    }
}
