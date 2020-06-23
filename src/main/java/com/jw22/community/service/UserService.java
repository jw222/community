package com.jw22.community.service;

import com.jw22.community.mapper.UserMapper;
import com.jw22.community.model.User;
import com.jw22.community.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public void createOrUpdate(User user) {
        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andAccountIdEqualTo(user.getAccountId());
        List<User> dbUsers = userMapper.selectByExample(userExample);
        if (dbUsers.size() == 0) {
            user.setCreateTime(System.currentTimeMillis());
            user.setModifyTime(user.getCreateTime());
            userMapper.insert(user);
        } else {
            User updateUser = new User();
            updateUser.setModifyTime(System.currentTimeMillis());
            updateUser.setName(user.getName());
            updateUser.setToken(user.getToken());
            updateUser.setProfilePath(user.getProfilePath());
            UserExample userExample1 = new UserExample();
            userExample1.createCriteria()
                    .andIdEqualTo(dbUsers.get(0).getId());
            userMapper.updateByExampleSelective(updateUser, userExample1);
        }
    }
}
