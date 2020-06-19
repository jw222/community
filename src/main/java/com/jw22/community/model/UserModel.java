package com.jw22.community.model;

import lombok.Data;

import java.util.Map;

@Data
public class UserModel {
    private Integer id;
    private String name;
    private String accountId;
    private String token;
    private Long createTime;
    private Long modifyTime;
    private String profilePath;

    public void setModel(Map<String, Object> userMap) {
        this.id = Integer.parseInt(userMap.get("id").toString());
        this.name = userMap.get("name").toString();
        this.accountId = userMap.get("account_id").toString();
        this.token = userMap.get("token").toString();
        this.createTime = Long.parseLong(userMap.get("create_time").toString());
        this.modifyTime = Long.parseLong(userMap.get("modify_time").toString());
    }
}
