package com.yc.service.impl;

import java.util.List;


import javax.annotation.Resource;
import javax.lang.model.element.TypeElement;

import org.springframework.stereotype.Service;

import com.yc.bean.Movie;
import com.yc.bean.MovieExample;
import com.yc.bean.MovieType;
import com.yc.bean.MovieTypeExample;
import com.yc.bean.Type;
import com.yc.bean.TypeExample;
import com.yc.dao.MovieTypeMapper;
import com.yc.dao.TypeMapper;
import com.yc.service.MovieTypeService;

@Service
public class MovieTypeServiceImpl implements MovieTypeService{
	@Resource
	MovieTypeMapper mtm;
	
	@Override
	public List<MovieType> findMovieTypeByMovieID(int movieID) {
		MovieTypeExample mte = new MovieTypeExample();
		mte.createCriteria().andMovieIdEqualTo(movieID);
		List<MovieType> list = mtm.selectByExample(mte);
		return list;
	}
	
	public List<MovieType> findMovieIdByTypeId(int id){
		MovieTypeExample mte = new MovieTypeExample();
		mte.createCriteria().andTypeIdEqualTo(id);
		List<MovieType> selectByExample = mtm.selectByExample(mte);
		return selectByExample;
	}

	@Override
	public void updateType(Movie movie, String[] str) {
		MovieTypeExample mte = new MovieTypeExample();
		mte.createCriteria().andMovieIdEqualTo(movie.getMovieId());
		mtm.deleteByExample(mte);
		for(String s:str) {
			MovieType mt = new MovieType();
			mt.setMovieId(movie.getMovieId());
			mt.setTypeId(Integer.parseInt(s));
			mtm.insert(mt);
		}
	}

	public void add(Movie movie, String[] typeList) {
		for(String s:typeList) {
			MovieType mt = new MovieType();
			mt.setMovieId(movie.getMovieId());
			mt.setTypeId(Integer.parseInt(s));
			mtm.insert(mt);
		}
	}

	@Override
	public int delete(int mid) {
		MovieTypeExample mte = new MovieTypeExample();
		mte.createCriteria().andMovieIdEqualTo(mid);
		return mtm.deleteByExample(mte);
	} 
	
	
}
