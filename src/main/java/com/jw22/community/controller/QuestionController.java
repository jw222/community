package com.jw22.community.controller;

import com.jw22.community.dto.QuestionDTO;
import com.jw22.community.mapper.UserMapper;
import com.jw22.community.model.User;
import com.jw22.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Integer id,
                           Model model) {
        questionService.incrementView(id);
        QuestionDTO questionDTO = questionService.getById(id);
        User user = userMapper.selectByPrimaryKey(questionDTO.getCreatorId());
        questionDTO.setUser(user);
        model.addAttribute("question", questionDTO);
        return "question";
    }
}
