package com.yj.community.model;

import lombok.Data;

/**
 * Created by 22154 on 2019/11/30.
 */
@Data
public class Question {
    private Integer id;
    private String title;
    private String description;
    private String tag;
    private Long gmt_create;
    private Long gmt_modified;
    private Integer creator;
    private Integer comment_count;
    private Integer view_count;
    private Integer like_count;


}
