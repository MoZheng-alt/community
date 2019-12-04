package com.yj.community.controller;

import com.yj.community.DtO.AccessTokenDTO;
import com.yj.community.DtO.GithubUser;
import com.yj.community.DtO.pageDTO;
import com.yj.community.mapper.UserMapper;
import com.yj.community.model.User;
import com.yj.community.provider.GithubProvider;
import com.yj.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * Created by 22154 on 2019/11/29.
 */
@Controller
public class Authorize {

    @Autowired
    private GithubProvider githubProvider;
    @Autowired(required = false)
    private UserMapper userMapper;
    @Autowired(required = false)
    private QuestionService questionService;

    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String uri;

    @GetMapping("/callback")
    public String callback(@RequestParam(name="code") String code,
                          @RequestParam(name="state") String state,
                           Model model,
                           @RequestParam(name="page",defaultValue = "1") Integer page,
                           @RequestParam(name="size",defaultValue = "5") Integer size,
                           HttpServletRequest requst,
                           HttpServletResponse response
                          ){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();

        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setRedirect_uri(uri);

        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = githubProvider.getUser(accessToken);

        pageDTO pagination=questionService.List(page,size);
        model.addAttribute("pagination",pagination);

        if(githubUser!=null&&githubUser.getId()!=null){

            User user = new User();
            String randomToken = UUID.randomUUID().toString();

            user.setName(githubUser.getName());
            user.setAccount_id(String.valueOf(githubUser.getId()));
            user.setAccount_mail(user.getAccount_id());
            user.setGmt_create(System.currentTimeMillis());
            user.setGmt_modified(user.getGmt_create());
            user.setLogin(githubUser.getLogin());
            user.setAvatar_url(githubUser.getAvatar_url());
            user.setPassword("");

            String token=userMapper.findTokenByAccountId(user.getAccount_id());
            if(token==null){
                token=randomToken;
                user.setToken(token);
                userMapper.insert(user);
                response.addCookie(new Cookie("token",user.getToken()));
                //登陆成功 写session写cookie
                requst.getSession().setAttribute("user",githubUser);

                return "index";
            }else {
                user.setToken(token);
                response.addCookie(new Cookie("token",token));
                requst.getSession().setAttribute("user",githubUser);

                return "index";
            }

        }else{
            //登陆失败
            return "index";
        }

    }
}
