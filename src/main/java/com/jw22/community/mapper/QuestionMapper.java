package com.jw22.community.mapper;

import com.jw22.community.model.QuestionModel;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface QuestionMapper {
    @Insert("insert into question (title, description, create_time, modify_time, creator_id, tag) values " +
            "(#{title}, #{description}, #{createTime}, #{modifyTime}, #{creatorId}, #{tag})")
    void insert(QuestionModel question);
}
