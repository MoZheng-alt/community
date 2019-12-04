package com.yj.community.mapper;

import com.yj.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * Created by 22154 on 2019/11/30.
 */
@Mapper
public interface UserMapper {
    @Insert("insert into user (name,account_id,token,gmt_create,gmt_modified,avatar_url,account_mail,password) values (#{login},#{account_id},#{token},#{gmt_create},#{gmt_modified},#{avatar_url},#{account_mail},#{password})")
    void insert(User user);

    @Select("select * from user where token=#{token}")
    User findByToken(@Param("token") String token);

    @Select("select * from user where id=#{id}")
    User findById(@Param("id") Integer id);

    @Select("select count(1) from user where account_id=#{account_id}")
    Integer findByAccoutId(@Param("account_id") String account_id);

    @Select("select token from user where account_id=#{account_id}")
    String findTokenByAccountId(@Param("account_id") String account_id);

    @Select("select * from user where account_mail=#{account_mail}")
    User findByAccountMail(@Param("account_mail") String account_mail);
}