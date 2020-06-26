package com.jw22.community.service;

import com.jw22.community.dto.CommentDTO;
import com.jw22.community.dto.PaginationDTO;
import com.jw22.community.dto.QuestionDTO;
import com.jw22.community.enums.CommentTypeEnum;
import com.jw22.community.exception.CustomizedErrorCode;
import com.jw22.community.exception.CustomizedException;
import com.jw22.community.mapper.CommentMapper;
import com.jw22.community.mapper.QuestionMapper;
import com.jw22.community.mapper.QuestionMapperExt;
import com.jw22.community.mapper.UserMapper;
import com.jw22.community.model.*;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class CommentService {
    @Autowired
    private UserMapper userMapper;

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
            Comment dbComment = commentMapper.selectByPrimaryKey(comment.getParentId());
            if (dbComment == null) {
                throw new CustomizedException(CustomizedErrorCode.COMMENT_NOT_FOUND);
            }
            commentMapper.insertSelective(comment);
            Question dbQuestion = questionMapper.selectByPrimaryKey(dbComment.getParentId());
            if (dbQuestion == null) {
                throw new CustomizedException(CustomizedErrorCode.QUESTION_NOT_FOUND);
            }
            dbQuestion.setCommentCount(1);
            questionMapperExt.incrementCommentCount(dbQuestion);
        }
    }

    public PaginationDTO list(Long questionId, Integer page, Integer size) {
        // get comments under question
        int offset = size * (page - 1);
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria()
                .andParentIdEqualTo(questionId)
                .andParentTypeEqualTo(CommentTypeEnum.QUESTION.getType());
        commentExample.setOrderByClause("create_time desc");
        int totalQuestions = (int) commentMapper.countByExample(commentExample);
        int totalPages = (int) Math.ceil((double) totalQuestions / (double) size);

        // get all comments
        List<Comment> comments = commentMapper.selectByExampleWithRowbounds(commentExample, new RowBounds(offset, size));
        HashMap<Long, User> users = new HashMap<>();
        List<CommentDTO> commentDTOs = new ArrayList<>();
        for (Comment comment: comments) {
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment, commentDTO);
            Long creatorId = comment.getCreatorId();
            if (!users.containsKey(creatorId)) {
                users.put(creatorId, userMapper.selectByPrimaryKey(creatorId));
            }
            commentDTO.setUser(users.get(creatorId));

            // get all subComments
            CommentExample subCommentExample = new CommentExample();
            subCommentExample.createCriteria()
                    .andParentIdEqualTo(comment.getId())
                    .andParentTypeEqualTo(CommentTypeEnum.COMMENT.getType());
            List<Comment> subComments = commentMapper.selectByExample(subCommentExample);
            if (subComments != null) {
                List<CommentDTO> subCommentDTOs = new ArrayList<>();
                for (Comment subComment : subComments) {
                    CommentDTO subCommentDTO = new CommentDTO();
                    BeanUtils.copyProperties(subComment, subCommentDTO);
                    Long subCreatorId = subComment.getCreatorId();
                    if (!users.containsKey(subCreatorId)) {
                        users.put(subCreatorId, userMapper.selectByPrimaryKey(subCreatorId));
                    }
                    Long replyToId = commentMapper.selectByPrimaryKey(subCommentDTO.getReplyTo()).getCreatorId();
                    if (!users.containsKey(replyToId)) {
                        users.put(replyToId, userMapper.selectByPrimaryKey(replyToId));
                    }
                    subCommentDTO.setUser(users.get(subCreatorId));
                    subCommentDTO.setReplyToUser(users.get(replyToId));
                    subCommentDTOs.add(subCommentDTO);
                }
                commentDTO.setSubComments(subCommentDTOs);
            }
            commentDTOs.add(commentDTO);
        }

        PaginationDTO paginationDTO = new PaginationDTO();
        paginationDTO.setComments(commentDTOs);

        return PaginationDTO.setProperties(paginationDTO, page, totalPages);
    }
}
