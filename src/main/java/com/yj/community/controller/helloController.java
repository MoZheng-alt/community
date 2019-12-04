package com.yj.community.controller;

import com.yj.community.DtO.pageDTO;
import com.yj.community.mapper.UserMapper;
import com.yj.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by 22154 on 2019/11/29.
 */
@Controller
public class helloController {
    @Autowired(required = false)
    private UserMapper userMapper;

    @Autowired(required = false)
    private QuestionService questionService;

    @GetMapping("/")
    public String index(HttpServletRequest request,
                        Model model,
                        @RequestParam(name="page",defaultValue = "1") Integer page,
                        @RequestParam(name="size",defaultValue = "5") Integer size
                        ){

        /*User user=null;
        Cookie[] cookies = request.getCookies();
        if(cookies!=null){
            for (Cookie cookie : cookies) {
                if(cookie.getName().equals("token")){
                    String token =cookie.getValue();
                    user=userMapper.findByToken(token);

                    if(user!=null){
                        user.setLogin(user.getName());
                        System.out.println(user.toString());
                        request.getSession().setAttribute("user",user);
                    }
                    break;
                }
            }
        }else{
            request.getSession().setAttribute("user",null);
        }*/

        pageDTO pagination=questionService.List(page,size);
        model.addAttribute("pagination",pagination);

        return "index";
    }
}
