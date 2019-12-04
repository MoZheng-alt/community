package com.yj.community.DtO;

import lombok.Data;

/**
 * Created by 22154 on 2019/11/29.
 */
@Data
public class AccessTokenDTO {
    private String client_id;
    private String client_secret;
    private String code;
    private String redirect_uri;
    private String state;
}
