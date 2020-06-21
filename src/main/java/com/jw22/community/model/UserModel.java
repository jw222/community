package com.jw22.community.model;

import lombok.Data;

@Data
public class UserModel {
    private Integer id;
    private String accountId;
    private String name;
    private String token;
    private Long createTime;
    private Long modifyTime;
    private String profilePath;
}
