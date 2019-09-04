package com.spring.community.community.controller;

import com.spring.community.community.dto.PaginationDTO;
import com.spring.community.community.model.User;
import com.spring.community.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/profile/{action}")
    public String profile(
            HttpServletRequest request,
            @PathVariable(name = "action") String action,
            Model model,
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "size", defaultValue = "5") Integer size
    ) {
        User user = (User)request.getSession().getAttribute("user");
        if (user == null) {
            model.addAttribute("error", "用户未登录");
            return "publish";
        }

        if (action.equals("replies")) {
            model.addAttribute("section", "replies");
            model.addAttribute("sectionName", "最新回复");
        } else {
            model.addAttribute("section", "questions");
            model.addAttribute("sectionName", "我的提问");
        }

        PaginationDTO pagination = questionService.list(user.getId(), page, size);
        model.addAttribute("pagination", pagination);
        return "profile";
    }
}
