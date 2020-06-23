package com.jw22.community.dto;

import com.jw22.community.model.User;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class QuestionDTO {
    private Long id;
    private String title;
    private String description;
    private Long createTime;
    private Long modifyTime;
    private Long creatorId;
    private Integer commentCount;
    private Integer viewCount;
    private Integer upVote;
    private Integer downVote;
    private String tag;
    private User user;
}
