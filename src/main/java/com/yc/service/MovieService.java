package com.yc.service;

import java.lang.reflect.InvocationTargetException;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.yc.bean.Movie;
import com.yc.bean.MovieType;
import com.yc.bean.User;
import com.yc.bean.Wants;

public interface MovieService {
	PageInfo<Movie> findAllMoive(int pageNum,String sname,Date sTime);
	
	Map<String,Object> findMovieDetailsByMovieId(int movieId) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException;
	
	int updateMovie(Movie movie);
	
	int findTotal(String name,Date date);
	//数据库固定字段
	/**
	 * 上映中 
	 */
	String showing_status = "showing";
	/**
	 * 即将上映
	 */
	String up_coming_status = "upcoming";
	/**
	 * 经典电影
	 */
	String typical_status = "typical";
	
	
	/**
	 * 正在热映
	 */
	List<Movie> listShowing(int start,int offset);
	/**
	 * 即将上映
	 */
	List<Movie> listUpComing(int start,int offset);
	
	/**
	 * 最受期待
	 */
	List<Movie> mostExpect();
	
	/**
	 * 榜单
	 */
	List<Movie> top10();
	
	
	/**
	 * 设置封面
	 * @param list
	 */
	 void setCover(List<Movie> list);

	 void setCover(Movie movie);
	 
	 /**
	  * 设置评分
	  * @param movie
	  */
	 void setScore(Movie movie);
	 
	 void setScore(List<Movie> list);
	 
	 Movie get(int id);
	 
	 int upComingCnt();
	 
	 int showingCnt();
	 
	 int add(Movie movie);
	 
	 int getMovieId(Movie movie);
	 
	 int deleteMovie(int id,String number);
	 
	 void setTypeName(List<Movie> list);
	 
	 void setTypeName(Movie movie);

	void setBigImage(Movie movie);

	void setSmallImage(Movie movie);


	void setActor(Movie movie);

	int addWants(Integer userId, int movieId);

	Wants getWant(User user, int id);

	int updateWants(Integer userId, int movieId, int flag);
	
	int getWantCnt(int movieId);

	List<String> findAllRegion();

	List<Movie> hotMovie();
	
}
