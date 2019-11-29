package com.yj.community.controller;

import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by 22154 on 2019/11/29.
 */
@Controller
public class helloController {
    @GetMapping("/")
    public String index(){
        //model.addAttribute("name",name);
        return "index";
    }
}
