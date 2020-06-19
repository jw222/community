package com.jw22.community.controller;

import com.jw22.community.mapper.UserMapper;
import com.jw22.community.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
public class IndexController {
    @Autowired
    private UserMapper userMapper;

    @GetMapping("/")
    public String index(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    Map<String, Object> userMap = userMapper.findByToken(token);
                    if (userMap != null) {
                        UserModel userModel = new UserModel();
                        userModel.setModel(userMap);
                        request.getSession().setAttribute("user", userModel);
                    }
                    break;
                }
            }
        }
        return "index";
    }
}
