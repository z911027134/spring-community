package com.spring.community.dto;

import com.spring.community.model.User;
import lombok.Data;

@Data
public class QuestionDTO {
    private Long id;
    private String title;
    private String description;
    private String tag;
    private Long creator;
    private Integer commentCount;
    private Integer viewCount;
    private Integer likeCount;
    private Long gmtCreate;
    private Long gmtModified;
    private User user;
}
