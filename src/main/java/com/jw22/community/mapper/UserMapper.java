package com.jw22.community.mapper;

import com.jw22.community.model.UserModel;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

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
}
