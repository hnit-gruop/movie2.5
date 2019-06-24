package com.yc.service.impl;



import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yc.bean.MovieImage;
import com.yc.bean.MovieImageExample;
import com.yc.dao.MovieImageMapper;
import com.yc.service.MovieImageService;

@Service
public class MovieImageServiceImpl implements MovieImageService{
	
	
	@Autowired
	MovieImageMapper movieImageMapper;
	
	/**
	 * 获取电影封面
	 */
	@Override
	public String getCover(int movieId) {
		MovieImageExample example = new MovieImageExample();
		example.createCriteria().andMovieIdEqualTo(movieId).andTypeEqualTo(MovieImageService.COVER_TYPE);
		List<MovieImage> list = movieImageMapper.selectByExample(example);
		if(list.size()>0)
			return list.get(0).getImage();
		return null;
	}

	@Override
	public String getBig(int movieId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSmall(int movieId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int add(MovieImage mi) {
		return movieImageMapper.insert(mi);
	}

	@Override
	public int delete(MovieImage mi) {
		return 0;
	}

	@Override
	public int update(MovieImage mi) {
		MovieImageExample mie = new MovieImageExample();
		mie.createCriteria().andMovieIdEqualTo(mi.getMovieId());
		return movieImageMapper.updateByExampleSelective(mi, mie);
	}

	@Override
	public List<MovieImage> getMovieImageByMovieId(int movieId) {
		MovieImageExample mie = new MovieImageExample();
		mie.createCriteria().andMovieIdEqualTo(movieId);
		List<MovieImage> selectByExample = movieImageMapper.selectByExample(mie);
		return selectByExample;
	}

	@Override
	public int delete(int mid) {
		MovieImageExample mie = new MovieImageExample();
		mie.createCriteria().andMovieIdEqualTo(mid);
		return movieImageMapper.deleteByExample(mie);
	}


}
