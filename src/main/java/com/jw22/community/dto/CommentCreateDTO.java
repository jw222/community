package com.jw22.community.dto;

import lombok.Data;

@Data
public class CommentCreateDTO {
    private Long parentId;
    private String description;
    private Integer parentType;
    private Long replyTo;
}
