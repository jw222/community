package com.jw22.community.controller;

import com.jw22.community.dto.CommentCreateDTO;
import com.jw22.community.dto.ResultDTO;
import com.jw22.community.enums.CommentTypeEnum;
import com.jw22.community.exception.CustomizedErrorCode;
import com.jw22.community.model.Comment;
import com.jw22.community.model.User;
import com.jw22.community.service.CommentService;
import com.jw22.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class CommentController {
    @Autowired
    private CommentService commentService;

    @Autowired
    private QuestionService questionService;

    @ResponseBody
    @PostMapping("/comment")
    public Object post(@RequestBody CommentCreateDTO commentCreateDTO,
                       HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return ResultDTO.errorOf(CustomizedErrorCode.NOT_LOGGED_IN);
        }
        if (commentCreateDTO == null
                || commentCreateDTO.getDescription() == null
                || commentCreateDTO.getDescription().equals("")) {
            return ResultDTO.errorOf(CustomizedErrorCode.NO_REPLY_DESC);
        }
        Comment comment = new Comment();
        comment.setParentId(commentCreateDTO.getParentId());
        comment.setDescription(commentCreateDTO.getDescription());
        comment.setParentType(commentCreateDTO.getParentType());
        comment.setCreateTime(System.currentTimeMillis());
        comment.setModifyTime(comment.getCreateTime());
        comment.setCreatorId(user.getId());
        comment.setReplyTo(commentCreateDTO.getReplyTo());
        commentService.insert(comment);
        if (commentCreateDTO.getParentType().equals(CommentTypeEnum.QUESTION.getType())) {
            questionService.updateActivity(commentCreateDTO.getParentId());
        }
        return ResultDTO.errorOf(CustomizedErrorCode.OK);
    }
}
