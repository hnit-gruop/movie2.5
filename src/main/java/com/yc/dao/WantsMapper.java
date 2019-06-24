package com.yc.dao;

import com.yc.bean.Wants;
import com.yc.bean.WantsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WantsMapper {
    long countByExample(WantsExample example);

    int deleteByExample(WantsExample example);

    int deleteByPrimaryKey(Integer wantsId);

    int insert(Wants record);

    int insertSelective(Wants record);

    List<Wants> selectByExample(WantsExample example);

    Wants selectByPrimaryKey(Integer wantsId);

    int updateByExampleSelective(@Param("record") Wants record, @Param("example") WantsExample example);

    int updateByExample(@Param("record") Wants record, @Param("example") WantsExample example);

    int updateByPrimaryKeySelective(Wants record);

    int updateByPrimaryKey(Wants record);
}