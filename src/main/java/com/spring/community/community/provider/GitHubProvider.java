package com.spring.community.community.provider;

import com.alibaba.fastjson.JSON;
import com.spring.community.community.dto.GitHubAccessTokenDTO;
import com.spring.community.community.dto.GitHubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GitHubProvider {
    public String getGitHubAccessToken(GitHubAccessTokenDTO accessTokenDTO) {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        String json = JSON.toJSONString(accessTokenDTO);
        RequestBody body = RequestBody.create(mediaType, json);
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            String token = string.split("&")[0].split("=")[1];
            return token;
        } catch (IOException e) {}
        return null;
    }

    public GitHubUser getUser(String accessToekn) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token="+accessToekn)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            System.out.println(string);
            return JSON.parseObject(string, GitHubUser.class);
        }catch (IOException e) {}catch (NullPointerException e){
            e.printStackTrace();
        }
        return null;
    }

}
