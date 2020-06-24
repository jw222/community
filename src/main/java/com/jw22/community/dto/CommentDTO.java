package com.jw22.community.dto;

import com.jw22.community.model.User;
import lombok.Data;

import java.util.List;

@Data
public class CommentDTO {
    private Long id;
    private Long replyTo;
    private Long parentId;
    private Integer parentType;
    private Long creatorId;
    private Long createTime;
    private Long modifyTime;
    private Integer upVote;
    private Integer downVote;
    private String description;
    private List<CommentDTO> subComments;
    private User user;
    private User replyToUser;
}
