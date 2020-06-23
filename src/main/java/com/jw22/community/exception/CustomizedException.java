package com.jw22.community.exception;

public class CustomizedException extends RuntimeException {
    private final Integer code;
    private final String message;

    public CustomizedException(CustomizedErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }

    @Override
    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }
}
