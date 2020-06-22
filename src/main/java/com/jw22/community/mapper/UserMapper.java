package com.jw22.community.mapper;

import com.jw22.community.model.UserModel;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface UserMapper {
    @Insert("insert into user (accountId, name, token, createTime, modifyTime, profilePath) values " +
            "(#{accountId}, #{name}, #{token}, #{createTime}, #{modifyTime}, #{profilePath})")
    void insert(UserModel userModel);

    @Select("select * from user where token = #{token}")
    UserModel findByToken(@Param("token") String token);

    @Select("select * from user where id = #{creatorId}")
    UserModel findById(@Param("creatorId") Integer creatorId);

    @Select("select * from user where accountId = #{accountId}")
    UserModel findByAccountId(@Param("accountId") String accountId);

    @Update("update user set name=#{name}, token=#{token}, modifyTime=#{modifyTime}, profilePath=#{profilePath} where id=#{id}")
    void update(UserModel user);
}
