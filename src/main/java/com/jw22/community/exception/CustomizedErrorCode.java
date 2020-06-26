package com.jw22.community.exception;

public enum CustomizedErrorCode implements CustomizedErrorCodeInterface {
    OK(200, "Success"),
    QUESTION_NOT_FOUND(201, "Question does not exist!"),
    COMMENT_NOT_FOUND(202, "Comment does not exist!"),
    COMMENT_NO_PARENT(203, "Comment parent not selected or original parent is deleted."),
    NOT_LOGGED_IN(204, "Please log in first"),
    SYSTEM_ERROR(205, "System error"),
    COMMENT_TYPE_INVALID(206, "Comment type not valid"),
    NO_REPLY_DESC(207, "Description is null or empty");

    private final Integer code;
    private final String message;

    CustomizedErrorCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }
}
