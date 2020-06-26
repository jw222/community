package com.jw22.community.mapper;

import com.jw22.community.model.Question;

import java.util.List;

public interface QuestionMapperExt {
    int incrementView(Question question);

    int incrementCommentCount(Question question);

    List<Question> selectRelated(Question question);

    int updateActivity(Question question);
}
