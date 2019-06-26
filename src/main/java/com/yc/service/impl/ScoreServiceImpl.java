package com.yc.service.impl;

import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yc.bean.Score;
import com.yc.bean.ScoreExample;
import com.yc.dao.ScoreMapper;
import com.yc.service.ScoreService;

@Service
public class ScoreServiceImpl implements ScoreService{
	
	@Autowired
	ScoreMapper scoreMapper;

	@Override
	public double get(int movieId) {
		ScoreExample example = new ScoreExample();
		example.createCriteria().andMovieIdEqualTo(movieId);
		List<Score> list = scoreMapper.selectByExample(example);
		if(list.size()>0) {
			Score score = list.get(0);
			setScore(score);
			return score.getScore();
		}
		return 0;
	}

	@Override
	public double getInRedis() {
		
		
		
		
		return 0;
	}
	
	/**
	 * 计算平均分
	 */
	@Override
	public void setScore(Score score) {
		Double sumScore = score.getSumScore();
		Integer sumPeople = score.getSumPeople();
		score.setScore(sumScore/sumPeople);
	}

	@Override
	public List<Score> getAll() {
		List<Score> list = scoreMapper.selectByExample(null);
		return list;
	}

	@Override
	public int updateScore(Score score) {
		ScoreExample example = new ScoreExample();
		example.createCriteria().andMovieIdEqualTo(score.getMovieId());
		return scoreMapper.updateByExampleSelective(score, example);
	}
	
}
