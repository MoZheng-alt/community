package com.yj.community.controller;

import com.yj.community.mapper.UserMapper;
import com.yj.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by 22154 on 2019/12/3.
 */
@Controller
public class RegisterController {

    @Autowired(required = false)
    private UserMapper userMapper;

    @GetMapping("/register")
    public String register() {

        return "register";
    }

    @PostMapping("/register")
    public String doregister(
            @RequestParam("nickname") String nickname,
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam("password2") String password2,
            Model model
    ) {

        model.addAttribute("nickname", nickname);
        model.addAttribute("email", email);
        model.addAttribute("password", password);
        model.addAttribute("password2", password2);

        if (nickname == null || nickname == ""||nickname.length()>20) {
            model.addAttribute("error", "昵称不可为空且不可大于20个字符！");
            return "register";
        }
        if (email == null || email == "") {
            model.addAttribute("error", "邮箱不可为空！");
            return "register";
        }
        if (password == null ||password.length()>20) {
            model.addAttribute("error", "密码不可大于20个字符！");
            return "register";
        }
        if (password.length()<6) {
            model.addAttribute("error", "密码不可小于6个字符！");
            return "register";
        }
        if (!password2.equals(password)) {
            model.addAttribute("error", "密码不一致！");
            return "register";
        }

        /*User user=(User)request.getSession().getAttribute("user");
        if(user==null){
            model.addAttribute("error","用户未登录");
            return "publish";
        }*/
        if (userMapper.findByAccountMail(email) == null) {
            User user = new User();
            user.setName(nickname);
            user.setAccount_id(email);
            user.setAccount_mail(email);
            user.setGmt_create(System.currentTimeMillis());
            user.setGmt_modified(user.getGmt_create());
            user.setLogin(user.getName());
            user.setAvatar_url("https://avatars2.githubusercontent.com/u/57141441?v=4");
            user.setToken(user.getAccount_mail());
            user.setPassword(password);

            userMapper.insert(user);

            return "registerSucceed";
        } else {
            model.addAttribute("error", "邮箱已被注册！");

            return "register";
        }

    }
}
