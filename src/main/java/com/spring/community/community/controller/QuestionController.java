package com.spring.community.community.controller;

import com.spring.community.community.dto.QuestionDTO;
import com.spring.community.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/question/{id}")
    public String question(
            @PathVariable(name = "") Long id,
            Model model
   ) {
        QuestionDTO question = questionService.getById(id);
        questionService.incrViewCount(id);
        model.addAttribute("question", question);
        return "question";
    }
}
