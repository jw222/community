package com.jw22.community.advice;

import com.alibaba.fastjson.JSON;
import com.jw22.community.dto.ResultDTO;
import com.jw22.community.exception.CustomizedErrorCode;
import com.jw22.community.exception.CustomizedException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@ControllerAdvice
public class CustomizedExceptionHandler {
    @ExceptionHandler(Exception.class)
    ModelAndView handle(HttpServletRequest request,
                        HttpServletResponse response,
                        Throwable ex,
                        Model model) {
        String type = request.getContentType();
        if (type.equals("application/json")) {
            ResultDTO resultDTO;
            if (ex instanceof CustomizedException) {
                resultDTO = ResultDTO.errorOf((CustomizedException) ex);
            } else {
                resultDTO = ResultDTO.errorOf(CustomizedErrorCode.SYSTEM_ERROR);
            }
            try {
                response.setContentType("application/json");
                response.setStatus(200);
                response.setCharacterEncoding("utf-8");
                PrintWriter writer = response.getWriter();
                writer.write(JSON.toJSONString(resultDTO));
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        } else {
            if (ex instanceof CustomizedException) {
                model.addAttribute("message", ex.getMessage());
            } else {
                model.addAttribute("message", "server error");
            }
            return new ModelAndView("error");
        }
    }
}
