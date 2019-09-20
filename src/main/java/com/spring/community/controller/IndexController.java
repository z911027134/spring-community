package com.spring.community.controller;

import com.spring.community.dto.PaginationDTO;
import com.spring.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class IndexController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String greeting(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "5") Integer size,
            Model model
    ) {
        PaginationDTO pagination = questionService.list(page, size);
        model.addAttribute("pagination", pagination);
        return "index";
    }
}
