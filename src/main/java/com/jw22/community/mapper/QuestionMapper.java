package com.jw22.community.mapper;

import com.jw22.community.model.QuestionModel;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface QuestionMapper {
    @Insert("insert into question (title, description, createTime, modifyTime, creatorId, tag) values " +
            "(#{title}, #{description}, #{createTime}, #{modifyTime}, #{creatorId}, #{tag})")
    void insert(QuestionModel question);

    @Select("select * from question limit #{offset}, #{size}")
    List<QuestionModel> list(@Param("offset") Integer offset, @Param("size") Integer size);

    @Select("select count(1) from question")
    Integer count();
}
