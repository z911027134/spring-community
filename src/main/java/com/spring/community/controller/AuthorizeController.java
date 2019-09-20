package com.spring.community.controller;

import com.spring.community.dto.GitHubAccessTokenDTO;
import com.spring.community.dto.GitHubUserDTO;
import com.spring.community.model.User;
import com.spring.community.provider.GitHubProvider;
import com.spring.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class AuthorizeController {

    @Autowired
    private GitHubProvider gitHubProvider;

    @Value("${github.client.id}")
    private String clientId;

    @Value("${github.client.secret}")
    private String secret;

    @Autowired
    private UserService userService;

    @GetMapping("githubCallback")
    public String githubCallback(
            @RequestParam(name="code", required = true, defaultValue = "") String code,
            @RequestParam(name="state", required = true, defaultValue = "") String state,
            HttpServletResponse response
    ) {
        GitHubAccessTokenDTO gitHubAccessTokenDTO = new GitHubAccessTokenDTO();
        gitHubAccessTokenDTO.setCode(code);
        gitHubAccessTokenDTO.setState(state);
        gitHubAccessTokenDTO.setClient_id(clientId);
        gitHubAccessTokenDTO.setClient_secret(secret);
        String githubToken = gitHubProvider.getGitHubAccessToken(gitHubAccessTokenDTO);
        GitHubUserDTO gitHubProviderUser = gitHubProvider.getUser(githubToken);
        if (gitHubProviderUser != null && gitHubProviderUser.getId() != null){
            // 判断用户是否第一次登陆，查询user 是否存在
            String accountId = gitHubProviderUser.getId().toString();
            String token = UUID.randomUUID().toString();
            User user = new User();
            user.setToken(token);
            user.setAccountId(accountId);
            String name = gitHubProviderUser.getName() == null ? gitHubProviderUser.getLogin() : gitHubProviderUser.getName();
            user.setName(name);
            user.setAvatarUrl(gitHubProviderUser.getAvatarUrl());
            userService.createOrUpdate(user);
            response.addCookie(new Cookie("token", token));
        }
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request,
                         HttpServletResponse response) {
        request.getSession().removeAttribute("user");
        Cookie cookie = new Cookie("token", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";
    }
}
