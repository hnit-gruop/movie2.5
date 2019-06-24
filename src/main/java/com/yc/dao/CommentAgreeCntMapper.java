package com.yc.dao;

import com.yc.bean.CommentAgreeCnt;
import com.yc.bean.CommentAgreeCntExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CommentAgreeCntMapper {
    long countByExample(CommentAgreeCntExample example);

    int deleteByExample(CommentAgreeCntExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CommentAgreeCnt record);

    int insertSelective(CommentAgreeCnt record);

    List<CommentAgreeCnt> selectByExample(CommentAgreeCntExample example);

    CommentAgreeCnt selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CommentAgreeCnt record, @Param("example") CommentAgreeCntExample example);

    int updateByExample(@Param("record") CommentAgreeCnt record, @Param("example") CommentAgreeCntExample example);

    int updateByPrimaryKeySelective(CommentAgreeCnt record);

    int updateByPrimaryKey(CommentAgreeCnt record);
}