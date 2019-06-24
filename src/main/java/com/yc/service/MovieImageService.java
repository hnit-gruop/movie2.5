package com.yc.service;

import java.util.List;

import com.yc.bean.MovieImage;

public interface MovieImageService {
	String COVER_TYPE = "cover_type";//封面图片
	String BIG_IMG_TYPE = "big_img_type";//剧情大图
	String SMALL_IMG_TYPE = "small_img_type";//剧情小图
	
	List<MovieImage> getMovieImageByMovieId(int movieId);
	
	String getCover(int movieId);
	String getBig(int movieId);
	String getSmall(int movieId);
	
	int add(MovieImage mi);
	int delete(MovieImage mi);
	int delete(int mid);
	int update(MovieImage mi);
}
