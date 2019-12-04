package com.yj.community.DtO;

import lombok.Data;

/**
 * Created by 22154 on 2019/11/29.
 */
@Data
public class GithubUser {
    private String name;
    private Long id;
    private String bio;
    private String login;
    private String avatar_url;

}
