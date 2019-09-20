package com.spring.community.mapper;

import com.spring.community.model.Question;

import java.util.List;

public interface QuestionExtMapper {
    int incrViewCount(Question record);

    int incrCommentCount(Question record);

    List<Question> selectRelation(Question question);
}
