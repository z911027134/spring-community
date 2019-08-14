package com.spring.community.community.dto;

import lombok.Data;

@Data
public class GitHubAccessTokenDTO {
    private String client_id;

    private String client_secret;

    private String code;

    private String redirect_uri;

    private String state;

}
