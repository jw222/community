package com.jw22.community.service;

import com.jw22.community.mapper.UserMapper;
import com.jw22.community.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public void createOrUpdate(UserModel userModel) {
        UserModel user = userMapper.findByAccountId(userModel.getAccountId());
        if (user == null) {
            userModel.setCreateTime(System.currentTimeMillis());
            userModel.setModifyTime(userModel.getCreateTime());
            userMapper.insert(userModel);
        } else {
            user.setModifyTime(System.currentTimeMillis());
            user.setName(userModel.getName());
            user.setToken(userModel.getToken());
            user.setProfilePath(userModel.getProfilePath());
            userMapper.update(user);
        }
    }
}
