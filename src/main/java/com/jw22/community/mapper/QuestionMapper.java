package com.jw22.community.mapper;

import com.jw22.community.dto.QuestionDTO;
import com.jw22.community.model.QuestionModel;
import org.apache.ibatis.annotations.*;
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

    @Select("select * from question where creatorId = #{userId} limit #{offset}, #{size}")
    List<QuestionModel> listByUserId(@Param("userId") Integer userId, @Param("offset") Integer offset, @Param("size") Integer size);

    @Select("select count(1) from question where creatorId = #{userId}")
    Integer countByUserId(@Param("userId") Integer userId);

    @Select("select * from question where id = #{questionId}")
    QuestionModel getById(@Param("questionId") Integer questionId);

    @Update("update question set viewCount = viewCount + 1 where id = #{questionId}")
    void incrementView(@Param("questionId") Integer questionId);

    @Update("update question set title = #{title}, description = #{description}, modifyTime = #{modifyTime}, tag = #{tag} where id = #{id}")
    void update(QuestionModel questionModel);
}
