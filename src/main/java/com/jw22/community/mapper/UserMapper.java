package com.jw22.community.mapper;

import com.jw22.community.model.UserModel;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    @Insert("insert into user (account_id, name, token, create_time, modify_time) values " +
            "(#{accountId}, #{name}, #{token}, #{createTime}, #{modifyTime})")
    void insert(UserModel userModel);
}
