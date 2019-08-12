package com.spring.community.community.controller;

import com.spring.community.community.dto.GitHubAccessTokenDTO;
import com.spring.community.community.dto.GitHubUser;
import com.spring.community.community.mapper.UserMapper;
import com.spring.community.community.model.User;
import com.spring.community.community.provider.GitHubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    private UserMapper userMapper;

    @GetMapping("githubCallback")
    public String githubCallback(
            @RequestParam(name="code", required = true, defaultValue = "") String code,
            @RequestParam(name="state", required = true, defaultValue = "") String state,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        GitHubAccessTokenDTO gitHubAccessTokenDTO = new GitHubAccessTokenDTO();
        gitHubAccessTokenDTO.setCode(code);
        gitHubAccessTokenDTO.setState(state);
        gitHubAccessTokenDTO.setClient_id(clientId);
        gitHubAccessTokenDTO.setClient_secret(secret);
        String githubToken = gitHubProvider.getGitHubAccessToken(gitHubAccessTokenDTO);
        GitHubUser gitHubProviderUser = gitHubProvider.getUser(githubToken);
        if (gitHubProviderUser != null && gitHubProviderUser.getId() != null){
            User user = new User();
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setAccountId(gitHubProviderUser.getId().toString());
            String name = gitHubProviderUser.getName() == null ? gitHubProviderUser.getLogin() : gitHubProviderUser.getName();
            user.setName(name);
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            //user.setToken(token);
            userMapper.insert(user);
            //request.getSession().setAttribute("user", gitHubProviderUser);
            response.addCookie(new Cookie("token", token));
        }
        return "redirect:/";
    }
}
