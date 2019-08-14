package com.spring.community.community.dto;

import lombok.Data;

@Data
public class GitHubUser {
    private String login;

    private String name;

    private Long id;

    private String bio;

    private String avatarUrl;

}
