package com.jw22.community.service;

import com.jw22.community.enums.CommentTypeEnum;
import com.jw22.community.exception.CustomizedErrorCode;
import com.jw22.community.exception.CustomizedException;
import com.jw22.community.mapper.CommentMapper;
import com.jw22.community.mapper.QuestionMapper;
import com.jw22.community.mapper.QuestionMapperExt;
import com.jw22.community.model.Comment;
import com.jw22.community.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentService {
    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionMapperExt questionMapperExt;

    @Transactional
    public void insert(Comment comment) {
        if (comment.getParentId() == null) {
            throw new CustomizedException(CustomizedErrorCode.COMMENT_NO_PARENT);
        }
        if (comment.getParentType() == null || !CommentTypeEnum.exist(comment.getParentType())) {
            throw new CustomizedException(CustomizedErrorCode.COMMENT_TYPE_INVALID);
        }
        if (comment.getParentType().equals(CommentTypeEnum.QUESTION.getType())) {
            // comment under question
            Question dbQuestion = questionMapper.selectByPrimaryKey(comment.getParentId());
            if (dbQuestion == null) {
                throw new CustomizedException(CustomizedErrorCode.QUESTION_NOT_FOUND);
            }
            commentMapper.insertSelective(comment);
            dbQuestion.setCommentCount(1);
            questionMapperExt.incrementCommentCount(dbQuestion);
        } else {
            // comment under comment
            Comment dbComent = commentMapper.selectByPrimaryKey(comment.getParentId());
            if (dbComent == null) {
                throw new CustomizedException(CustomizedErrorCode.COMMENT_NOT_FOUND);
            }
            commentMapper.insertSelective(comment);
            Question dbQuestion = questionMapper.selectByPrimaryKey(dbComent.getParentId());
            if (dbQuestion == null) {
                throw new CustomizedException(CustomizedErrorCode.QUESTION_NOT_FOUND);
            }
            dbQuestion.setCommentCount(1);
            questionMapperExt.incrementCommentCount(dbQuestion);
        }
    }
}
