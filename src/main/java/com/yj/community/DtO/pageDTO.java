package com.yj.community.DtO;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 22154 on 2019/12/1.
 */

@Data
public class pageDTO {
    private List<QuestionDTO> questions;
    private Boolean showPrevious;//
    private Boolean showFirstPage;//
    private Boolean showNext;//
    private Boolean showEndPage;//
    private Boolean Is0=false;//是否0条帖子
    private Integer page;//当前页
    private List<Integer> pages=new ArrayList<>();
    private Integer totalPages;

    public void setPagination(Integer totalCount, Integer page, Integer size) {


        if(totalCount%size==0){
            totalPages=totalCount/size;
        }else totalPages=totalCount/size+1;

        if(totalPages<1) {
            Is0=true;
            totalPages=1;
        }

        if(page<=0) page=1;
        if(page>totalPages) page=totalPages;

        pages.add(page);
        for(int i=1;i<=3;i++){
            if(page+i<=totalPages) pages.add(page+i);
            if(page-i>=1) pages.add(0,page-i);
        }

        //
        if(page==1) showPrevious=false;
        else{
            showPrevious=true;
        }
        //
        if(page==totalPages) showNext=false;
        else{
            showNext=true;
        }
        //
        if(pages.contains(1)){
            showFirstPage=false;
        }else showFirstPage=true;
        //
        if(pages.contains(totalPages)){
            showEndPage=false;
        }else showEndPage=true;
        //
    }
}
