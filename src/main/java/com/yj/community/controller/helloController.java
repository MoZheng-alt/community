package com.yj.community.controller;

import com.yj.community.mapper.UserMapper;
import com.yj.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by 22154 on 2019/11/29.
 */
@Controller
public class helloController {
    @Autowired(required = false)
    private UserMapper userMapper;

    @GetMapping("/")
    public String index(HttpServletRequest request){
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
        }else{
            request.getSession().setAttribute("user",null);
        }
        boolean a=(request.getSession().getAttribute("user")==null);

        return "index";
    }
}
