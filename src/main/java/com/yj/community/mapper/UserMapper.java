package com.yj.community.mapper;

import com.yj.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by 22154 on 2019/11/30.
 */
@Mapper
public interface UserMapper {
   @Insert("insert into user (name,account_id,token,gmt_create,gmt_modified) values (#{Login},#{accountId},#{token},#{gmtCreate},#{gmtModified})")
    void insert(User user);
}
