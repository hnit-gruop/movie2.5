package com.yc.dao;

import com.yc.bean.CommentAgreeUser;
import com.yc.bean.CommentAgreeUserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CommentAgreeUserMapper {
    long countByExample(CommentAgreeUserExample example);

    int deleteByExample(CommentAgreeUserExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CommentAgreeUser record);

    int insertSelective(CommentAgreeUser record);

    List<CommentAgreeUser> selectByExample(CommentAgreeUserExample example);

    CommentAgreeUser selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CommentAgreeUser record, @Param("example") CommentAgreeUserExample example);

    int updateByExample(@Param("record") CommentAgreeUser record, @Param("example") CommentAgreeUserExample example);

    int updateByPrimaryKeySelective(CommentAgreeUser record);

    int updateByPrimaryKey(CommentAgreeUser record);
}