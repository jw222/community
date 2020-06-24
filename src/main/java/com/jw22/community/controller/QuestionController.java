package com.jw22.community.controller;

import com.jw22.community.dto.CommentDTO;
import com.jw22.community.dto.PaginationDTO;
import com.jw22.community.dto.QuestionDTO;
import com.jw22.community.mapper.UserMapper;
import com.jw22.community.model.User;
import com.jw22.community.service.CommentService;
import com.jw22.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Long id,
                           @RequestParam(name = "page", defaultValue = "1") Integer page,
                           @RequestParam(name = "size", defaultValue = "5") Integer size,
                           Model model) {
        // set question
        questionService.incrementView(id);
        QuestionDTO questionDTO = questionService.getById(id);
        User user = userMapper.selectByPrimaryKey(questionDTO.getCreatorId());
        questionDTO.setUser(user);

        // set replies
        PaginationDTO paginationDTO = commentService.list(questionDTO.getId(), page, size);
        model.addAttribute("paginationDTO", paginationDTO);
        model.addAttribute("question", questionDTO);
        return "question";
    }
}
