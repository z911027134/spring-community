package com.spring.community.community.service;

import com.spring.community.community.mapper.UserMapper;
import com.spring.community.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public void createOrUpdate(User user) {
        User existsUser = userMapper.findByAccountId(user.getAccountId());
        if (existsUser != null) {
            existsUser.setGmtModified(System.currentTimeMillis());
            existsUser.setToken(user.getToken());
            existsUser.setAvatarUrl(user.getAvatarUrl());
            existsUser.setName(user.getName());
            userMapper.update(existsUser);
        } else {
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
        }
    }
}
