package com.yc.service;

import java.util.List;

import com.yc.bean.Score;


public interface ScoreService {
	double get(int movieId);
	double getInRedis();
	
	List<Score> getAll();
	
	void setScore(Score score);
	
	int updateScore(Score score);
}
