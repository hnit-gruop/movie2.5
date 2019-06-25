package com.yc.service.impl;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.yc.bean.Score;
import com.yc.service.RedisService;
import com.yc.service.ScoreService;


@Service
public class RedisServiceImpl implements RedisService {
	@Autowired
	private RedisTemplate redisTemplate;

	@Autowired
	private ScoreService scoreService;

	@Override
	public void init() {
		List<Score> all = scoreService.getAll();
		for (Score score : all) {
			Integer sum = (int) score.getSumScore().floatValue();
			Integer cnt = score.getSumPeople();
			String s = String.valueOf(sum);
			String c = String.valueOf(cnt);
			StringBuilder sb = new StringBuilder();
			sb.append(s).append(":").append(c);
			redisTemplate.opsForHash().put("ms", String.valueOf(score.getMovieId()), sb);
		}
	}
	
	/**
	 * 综合评分
	 */
	@Override
	public double getAvgScore(int id) {
		Object object = redisTemplate.opsForHash().get("ms", ""+id);
		String string = object.toString();
		String[] split = string.split(":");
		if(split.length>1) {
			String sum = split[0];
			String cnt = split[1];
			double s = Double.parseDouble(sum);
			int c = Integer.parseInt(cnt);
			return s/c;
		}
		return 8.0;
	}

}
