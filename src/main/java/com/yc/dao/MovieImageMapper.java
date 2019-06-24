package com.yc.dao;

import com.yc.bean.MovieImage;
import com.yc.bean.MovieImageExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MovieImageMapper {
    long countByExample(MovieImageExample example);

    int deleteByExample(MovieImageExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MovieImage record);

    int insertSelective(MovieImage record);

    List<MovieImage> selectByExample(MovieImageExample example);

    MovieImage selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MovieImage record, @Param("example") MovieImageExample example);

    int updateByExample(@Param("record") MovieImage record, @Param("example") MovieImageExample example);

    int updateByPrimaryKeySelective(MovieImage record);

    int updateByPrimaryKey(MovieImage record);
}