package com.jw22.community.dto;

import com.jw22.community.model.UserModel;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class QuestionDTO {
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
    private UserModel userModel;
}
