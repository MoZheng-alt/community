package com.yj.community.controller;

import com.yj.community.DtO.pageDTO;
import com.yj.community.mapper.UserMapper;
import com.yj.community.model.User;
import com.yj.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by 22154 on 2019/12/2.
 */
@Controller
public class profileController {

    @Autowired(required = false)
    private UserMapper userMapper;

    @Autowired(required = false)
    private QuestionService questionService;

    @GetMapping("/profile/{action}")
    public String profile(HttpServletRequest request,
                          @PathVariable(name = "action") String action,
                          Model model,
                          @RequestParam(name = "page", defaultValue = "1") Integer page,
                          @RequestParam(name = "size", defaultValue = "5") Integer size
    ) {

       /*User user = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    user = userMapper.findByToken(token);
                    if (user != null) {
                        user.setLogin(user.getName());
                        System.out.println(user.toString());
                        request.getSession().setAttribute("user", user);
                    }
                    break;
                }
            }
        } else {
            request.getSession().setAttribute("user", null);
        }*/
        User user=(User)request.getSession().getAttribute("user");

        if(user==null) return "redirect:/";

        if ("questions".equals(action)) {
            model.addAttribute("section", "questions");
            model.addAttribute("sectionName", "我的帖子");
        } else if ("replies".equals(action)) {
            model.addAttribute("section", "replies");
            model.addAttribute("sectionName", "我的回复");
        }

        pageDTO pagination = questionService.List(user.getId(), page, size);
        model.addAttribute("pagination",pagination);
        return "profile";
    }
}
