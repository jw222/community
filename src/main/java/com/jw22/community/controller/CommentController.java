package com.jw22.community.controller;

import com.jw22.community.dto.CommentDTO;
import com.jw22.community.dto.ResultDTO;
import com.jw22.community.exception.CustomizedErrorCode;
import com.jw22.community.model.Comment;
import com.jw22.community.model.User;
import com.jw22.community.service.CommentService;
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

    @ResponseBody
    @PostMapping("/comment")
    public Object post(@RequestBody CommentDTO commentDTO,
                       HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return ResultDTO.errorOf(CustomizedErrorCode.NOT_LOGGED_IN);
        }
        Comment comment = new Comment();
        comment.setParentId(commentDTO.getParentId());
        comment.setDescription(commentDTO.getDescription());
        comment.setParentType(commentDTO.getParentType());
        comment.setCreateTime(System.currentTimeMillis());
        comment.setModifyTime(comment.getCreateTime());
        comment.setCreatorId(user.getId());
        commentService.insert(comment);
        return ResultDTO.errorOf(CustomizedErrorCode.OK);
    }
}
