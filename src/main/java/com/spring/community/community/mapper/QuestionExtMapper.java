package com.spring.community.community.mapper;

import com.spring.community.community.model.Question;

public interface QuestionExtMapper {
    int incrViewCount(Question record);

    int incrCommentCount(Question record);
}
