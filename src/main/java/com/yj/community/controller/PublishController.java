package com.yj.community.controller;

import com.yj.community.mapper.QuestionMapper;
import com.yj.community.mapper.UserMapper;
import com.yj.community.model.Question;
import com.yj.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by 22154 on 2019/11/30.
 */
@Controller
public class PublishController {

    @Autowired(required = false)
    private QuestionMapper questionMapper;

    @Autowired(required = false)
    private UserMapper userMapper;

    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }
    @PostMapping("/publish")
    public String doPublish(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("tag") String tag,
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

        User user=null;
        Cookie[] cookies = request.getCookies();
        if(cookies!=null){
            for (Cookie cookie : cookies) {
                if(cookie.getName().equals("token")){
                    String token =cookie.getValue();
                    user=userMapper.findByToken(token);
                    user.setLogin(user.getName());
                    if(user!=null){
                        System.out.println(user.toString());

                        request.getSession().setAttribute("user",user);
                    }
                    break;
                }
            }
        }

        if(user==null){
            model.addAttribute("error","用户未登录");
            return "publish";
        }
        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());
        question.setGmt_create(System.currentTimeMillis());
        question.setGmt_modified(question.getGmt_create());

        questionMapper.create(question);

        return "redirect:/";
    }
}
