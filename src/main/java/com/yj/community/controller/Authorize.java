package com.yj.community.controller;

import com.yj.community.DtO.AccessTokenDTO;
import com.yj.community.DtO.GithubUser;
import com.yj.community.mapper.UserMapper;
import com.yj.community.model.User;
import com.yj.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
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
    @Autowired
    private UserMapper userMapper;

    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String uri;

    @GetMapping("/callback")
    public String callback(@RequestParam(name="code") String code,
                          @RequestParam(name="state") String state,
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
        if(githubUser!=null){

            User user = new User();
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(githubUser.getName());
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            user.setLogin(githubUser.getLogin());
            userMapper.insert(user);

            response.addCookie(new Cookie("token",token));
            //登陆成功 写session写cookie
            requst.getSession().setAttribute("user",githubUser);
            return "index";
        }else{
            //登陆失败
            return "index";
        }

    }
}
