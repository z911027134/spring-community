package com.spring.community.community.dto;

import com.spring.community.community.model.User;
import lombok.Data;

@Data
public class QuestionDTO {
    private Integer id;
    private String title;
    private String description;
    private String tag;
    private Integer creator;
    private Integer commentCount;
    private Integer viewCount;
    private Integer likeCount;
    private Long gmtCreate;
    private Long gmtModified;
    private User user;
}
