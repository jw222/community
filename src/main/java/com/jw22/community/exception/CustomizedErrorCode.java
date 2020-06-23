package com.jw22.community.exception;

public enum CustomizedErrorCode implements CustomizedErrorCodeInterface {
    QUESTION_NOT_FOUND("Question Does Not Exist!");

    private final String message;

    CustomizedErrorCode(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
