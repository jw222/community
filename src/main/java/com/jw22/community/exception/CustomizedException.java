package com.jw22.community.exception;

public class CustomizedException extends RuntimeException {
    private final String message;

    public CustomizedException(CustomizedErrorCode errorCode) {
        this.message = errorCode.getMessage();
    }

    public CustomizedException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
