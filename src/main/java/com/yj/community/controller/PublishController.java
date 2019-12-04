package com.yj.community.controller;

import com.yj.community.DtO.QuestionDTO;
import com.yj.community.mapper.UserMapper;
import com.yj.community.model.Question;
import com.yj.community.model.User;
import com.yj.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by 22154 on 2019/11/30.
 */
@Controller
public class PublishController {

    @Autowired(required = false)
    private UserMapper userMapper;

    @Autowired
    private QuestionService questionService;

    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }

    @GetMapping("/publish/{id}")
    public String edit(@PathVariable(name = "id") Integer id,
                       Model model){
        QuestionDTO question = questionService.findByQuestionId(id);
        model.addAttribute("title",question.getTitle());
        model.addAttribute("description",question.getDescription());
        model.addAttribute("tag",question.getTag());
        model.addAttribute("questionId",question.getId());

        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("tag") String tag,
            @RequestParam("questionId") Integer questionId,
            HttpServletRequest request,
            Model model
            ){

        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);
        if(title==null||title==""){
            model.addAttribute("error","标题不可为空！");
            return "publish";
        }
        if(description==null||description==""){
            model.addAttribute("error","内容不可为空！");
            return "publish";
        }
        if(tag==null||tag==""){
            model.addAttribute("error","至少有一个标签！");
            return "publish";
        }

        User user=(User)request.getSession().getAttribute("user");
        if(user==null){
            model.addAttribute("error","用户未登录");
            return "publish";
        }
        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());
        question.setId(questionId);

        questionService.creatQuestionById(question);
        //questionMapper.create(question);

        return "redirect:/";
    }
}
