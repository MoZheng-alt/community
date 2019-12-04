package com.yj.community.controller;

import com.yj.community.DtO.pageDTO;
import com.yj.community.mapper.UserMapper;
import com.yj.community.model.User;
import com.yj.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by 22154 on 2019/12/3.
 */
@Controller
public class loginController {

    @Autowired(required = false)
    private UserMapper userMapper;

    @Autowired(required = false)
    private QuestionService questionService;

    @GetMapping("/login")
    public String login() {

        return "login";
    }

    @PostMapping("/login")
    public String dologin(
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "size", defaultValue = "5") Integer size,
            HttpServletRequest requst,
            HttpServletResponse response,
            Model model) {
        User user = userMapper.findByAccountMail(email);

        if (user == null) {
            model.addAttribute("error", "邮箱未被注册！");
            return "login";
        } else {
            user.setLogin(user.getName());
            if (!user.getPassword().equals(password)) {
                model.addAttribute("error", "密码不正确！");
                return "login";
            } else {
                response.addCookie(new Cookie("token", user.getToken()));
                requst.getSession().setAttribute("user", user);

                pageDTO pagination = questionService.List(page, size);
                model.addAttribute("pagination", pagination);
                return "index";
            }

        }
    }
}

