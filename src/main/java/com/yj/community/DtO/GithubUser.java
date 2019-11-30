package com.yj.community.DtO;

/**
 * Created by 22154 on 2019/11/29.
 */
public class GithubUser {
    private String name;
    private Long id;
    private String bio;
    private String login;

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
