package com.jw22.community.model;

import java.util.Map;

public class UserModel {
    private int id;
    private String name;
    private String accountId;
    private String token;
    private long createTime;
    private long modifyTime;

    public void setModel(Map<String, Object> userMap) {
        this.id = Integer.parseInt(userMap.get("id").toString());
        this.name = userMap.get("name").toString();
        this.accountId = userMap.get("account_id").toString();
        this.token = userMap.get("token").toString();
        this.createTime = Long.parseLong(userMap.get("create_time").toString());
        this.modifyTime = Long.parseLong(userMap.get("modify_time").toString());
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(long modifyTime) {
        this.modifyTime = modifyTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
