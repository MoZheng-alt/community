package com.yj.community.service;

import com.yj.community.DtO.QuestionDTO;
import com.yj.community.DtO.pageDTO;
import com.yj.community.mapper.QuestionMapper;
import com.yj.community.mapper.UserMapper;
import com.yj.community.model.Question;
import com.yj.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 22154 on 2019/12/1.
 */
@Service
public class QuestionService {

    @Autowired(required = false)
    private QuestionMapper questionmapper;
    @Autowired(required = false)
    private UserMapper usermapper;

    public pageDTO List(Integer page, Integer size) {
        pageDTO pageDTO = new pageDTO();
        Integer totalCount=questionmapper.count();
        pageDTO.setPagination(totalCount,page,size);

        if(page<=0) page=1;
        else if(page>pageDTO.getTotalPages()) page=pageDTO.getTotalPages();
        pageDTO.setPage(page);

        Integer offset=size*(page-1);

        List<Question> questions=questionmapper.List(offset,size);
        List<QuestionDTO> questionDTOList=new ArrayList<>();

        for(Question question:questions){
            User user=usermapper.findById(question.getCreator());
            QuestionDTO questionDTO=new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        pageDTO.setQuestions(questionDTOList);

        return pageDTO;
    }

    public pageDTO List(Integer creator, Integer page, Integer size) {
        pageDTO pageDTO = new pageDTO();
        Integer totalCount=questionmapper.countByCreator(creator);
        pageDTO.setPagination(totalCount,page,size);

        if(page<=0) page=1;
        if(page>pageDTO.getTotalPages()) page=pageDTO.getTotalPages();
        pageDTO.setPage(page);

        Integer offset=size*(page-1);

        List<Question> questions=questionmapper.ListByCreator(creator,offset,size);
        List<QuestionDTO> questionDTOList=new ArrayList<>();

        for(Question question:questions){
            User user=usermapper.findById(question.getCreator());
            QuestionDTO questionDTO=new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        pageDTO.setQuestions(questionDTOList);


        return pageDTO;
    }

    public QuestionDTO findByQuestionId(Integer questionId) {
        QuestionDTO questionDTO=new QuestionDTO();
        Question question=questionmapper.findByQuestionId(questionId);
        BeanUtils.copyProperties(question,questionDTO);
        User user=usermapper.findById(question.getCreator());
        questionDTO.setUser(user);
        return questionDTO;
    }

    public void creatQuestionById(Question question) {
        if(question.getId()==null) {

            question.setGmt_create(System.currentTimeMillis());
            question.setGmt_modified(question.getGmt_create());

            questionmapper.create(question);
        }else{//更新question
            question.setGmt_modified(question.getGmt_create());
            questionmapper.updata(question);
        }
    }
}
