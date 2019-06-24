package com.yc.service;

public interface RedisService {
	void init();
	
	double getAvgScore(int id);
}
