package com.spring.community.community.model;

import lombok.Data;

@Data
public class Question {

    private Integer id;
    private String title;
    private String description;
    private String tag;
    private Integer creator;
    private Integer comment_count;
    private Integer view_count;
    private Integer like_count;
    private Long gmtCreate;
    private Long gmtModified;
}
