package com.yc.dao;

import com.yc.bean.Movie;
import com.yc.bean.MovieExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MovieMapper {
	
	 List<Movie> listShowing(@Param("start")int start, @Param("offset")int offset);
	 List<Movie> listUpComing(@Param("start")int start, @Param("offset")int offset);
	 
	
    long countByExample(MovieExample example);

    int deleteByExample(MovieExample example);

    int deleteByPrimaryKey(Integer movieId);

    int insert(Movie record);

    int insertSelective(Movie record);

    List<Movie> selectByExample(MovieExample example);

    Movie selectByPrimaryKey(Integer movieId);

    int updateByExampleSelective(@Param("record") Movie record, @Param("example") MovieExample example);

    int updateByExample(@Param("record") Movie record, @Param("example") MovieExample example);

    int updateByPrimaryKeySelective(Movie record);

    int updateByPrimaryKey(Movie record);
    
    List<Movie> selectAll(@Param("limit") int limit, @Param("example")MovieExample example);
    
    List<Movie> selectByName(@Param("kw1") String kw1,@Param("kw2") String kw2, @Param("example")MovieExample example);
    
	
    List<Movie> listActor(Integer movieId);
    
	List<Movie> getTypeList(Integer movieId);
	
	List<Movie> listRegion();
}