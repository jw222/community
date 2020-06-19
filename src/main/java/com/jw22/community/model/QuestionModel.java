package com.jw22.community.model;

import lombok.Data;

@Data
public class QuestionModel {
    private Integer id;
    private String title;
    private String description;
    private Long createTime;
    private Long modifyTime;
    private Integer creatorId;
    private Integer commentCount;
    private Integer viewCount;
    private Integer upVote;
    private Integer downVote;
    private String tag;
}
