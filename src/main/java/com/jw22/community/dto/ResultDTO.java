package com.jw22.community.dto;

import com.jw22.community.exception.CustomizedErrorCode;
import com.jw22.community.exception.CustomizedException;
import lombok.Data;

@Data
public class ResultDTO {
    private Integer code;
    private String message;

    private static ResultDTO _errorOf(Integer code, String message) {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(code);
        resultDTO.setMessage(message);
        return resultDTO;
    }

    public static ResultDTO errorOf(CustomizedErrorCode errorCode) {
        return _errorOf(errorCode.getCode(), errorCode.getMessage());
    }

    public static ResultDTO errorOf(CustomizedException ex) {
        return _errorOf(ex.getCode(), ex.getMessage());
    }
}
