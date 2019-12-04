package com.yj.community.controller;

import com.yj.community.DtO.QuestionDTO;
import com.yj.community.mapper.QuestionMapper;
import com.yj.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Created by 22154 on 2019/12/2.
 */
@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Integer questionId,Model model) {
        QuestionDTO questionDTO = questionService.findByQuestionId(questionId);
        model.addAttribute("question",questionDTO);
        return "question";
    }
}
