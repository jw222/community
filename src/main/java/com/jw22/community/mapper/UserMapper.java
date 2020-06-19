package com.jw22.community.mapper;

import com.jw22.community.model.UserModel;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Mapper
public interface UserMapper {
    @Insert("insert into user (account_id, name, token, create_time, modify_time, profile_path) values " +
            "(#{accountId}, #{name}, #{token}, #{createTime}, #{modifyTime}, #{profilePath})")
    void insert(UserModel userModel);

    @Select("select * from user where token = #{token}")
    Map<String, Object> findByToken(@Param("token") String token);
}
