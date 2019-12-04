package com.yj.community.model;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.Loader;
import com.sun.xml.internal.ws.api.ha.StickyFeature;
import lombok.Data;

/**
 * Created by 22154 on 2019/11/30.
 */
@Data
public class User {
    private Integer id;
    private String name;
    private String account_id;
    private String token;
    private Long gmt_create;
    private Long gmt_modified;
    private String login;
    private String avatar_url;
    private String account_mail;
    private String password;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", account_id='" + account_id + '\'' +
                ", token='" + token + '\'' +
                ", gmt_create=" + gmt_create +
                ", gmt_modified=" + gmt_modified +
                ", login='" + login + '\'' +
                ", avatar_url='" + avatar_url + '\'' +
                ", account_mail='" + account_mail + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
