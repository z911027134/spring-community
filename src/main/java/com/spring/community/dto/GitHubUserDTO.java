package com.spring.community.dto;

import lombok.Data;

@Data
public class GitHubUserDTO {
    private String login;

    private String name;

    private Long id;

    private String bio;

    private String avatarUrl;

}
