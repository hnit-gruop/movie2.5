package com.yc.service;

import java.util.List;

import com.yc.bean.Movie;
import com.yc.bean.MovieType;
import com.yc.bean.Type;

public interface MovieTypeService {

	List<MovieType> findMovieTypeByMovieID(int movieID);
	
	List<MovieType> findMovieIdByTypeId(int id);
	
	void updateType(Movie movie,String[] str);
	
	void add(Movie movie,String[] str);
	
	int delete(int mid);
}
