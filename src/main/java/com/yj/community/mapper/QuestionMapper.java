package com.yj.community.mapper;

import com.yj.community.DtO.QuestionDTO;
import com.yj.community.model.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by 22154 on 2019/11/30.
 */
@Mapper
public interface QuestionMapper {
    @Insert("insert into question (title,description,gmt_create,gmt_modified,creator,comment_count,view_count,like_count,tag) values (#{title},#{description},#{gmt_create},#{gmt_modified},#{creator},#{comment_count},#{view_count},#{like_count},#{tag})")
    void create(Question question);

    @Select("select * from question limit #{offset},#{size}")
    List<Question> List(@Param(value = "offset") Integer offset, @Param(value = "size") Integer size);

    @Select("select count(1) from question")
    Integer count();

    @Select("select * from question  where creator=#{creator} limit #{offset},#{size}")
    List<Question> ListByCreator(@Param(value = "creator") Integer creator, @Param(value = "offset") Integer offset, @Param(value = "size") Integer size);

    @Select("select count(1) from question where creator=#{creator}")
    Integer countByCreator(@Param(value = "creator") Integer creator);

    @Select("select * from question  where id=#{id}")
    Question findByQuestionId(@Param("id") Integer id);

    @Update("update question set title=#{title},description=#{description},gmt_modified=#{gmt_modified},tag=#{tag} where id=#{id}")
    void updata(Question question);
}