package com.yc.Biz;

import java.util.List;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yc.bean.Actor;
import com.yc.bean.ActorExample;
import com.yc.bean.Movie;
import com.yc.bean.MovieActor;
import com.yc.bean.MovieActorExample;
import com.yc.bean.MovieExample;
import com.yc.bean.MovieType;
import com.yc.bean.MovieTypeExample;
import com.yc.bean.ScoreExample;
import com.yc.bean.Type;
import com.yc.bean.TypeExample;
import com.yc.dao.ActorMapper;
import com.yc.dao.MovieActorMapper;
import com.yc.dao.MovieMapper;
import com.yc.dao.MovieTypeMapper;
import com.yc.dao.TypeMapper;
import com.yc.util.PageHelper1;

@Service
public class MovieBiz {
	@Resource
	private  MovieMapper mm;
	
	@Resource
	private  MovieActorMapper mam;
	
	@Resource
	private  MovieTypeMapper mtm;
	
	@Resource
	private  ActorMapper am;
	
	@Resource
	private  TypeMapper tm;
	
	
	public List<Movie> findByMovieScore(){
		MovieExample example=new MovieExample();
		example.setOrderByClause("score desc");
		List<Movie> list=mm.selectAll(10,example);		
		return list;
	}

	public List<MovieActor> getActorId(Integer movie_id) {
		MovieActorExample example = new MovieActorExample();
		example.createCriteria().andMovieIdEqualTo(movie_id);
		List<MovieActor> list=mam.selectByExample(example);
		return list;
	}
	
	public List<Actor> getActorName(Integer actor_id){
		ActorExample example = new ActorExample();
		example.createCriteria().andActorIdEqualTo(actor_id);
		List<Actor> list=am.selectByExample(example);
		return list;
	}

	public List<Movie> findByMovieBoxOffice() {
		MovieExample example=new MovieExample();
		//me.setOrderByClause("boxoffice desc");
		List<Movie> list=mm.selectAll(10,example);		
		return list;
	}

	public List<Movie> findByMovieExpectation() {
		MovieExample example=new MovieExample();
		//me.setOrderByClause("expectation desc");
		List<Movie> list=mm.selectAll(10,example);		
		return list;
	}

	public PageInfo<Movie> findByMovieName(String kw ,Integer pageNum) {
		//PageHelper.offsetPage(offset, 2);
		PageHelper.startPage(pageNum, 2);
		 kw="%"+kw+"%";
		MovieExample example=new MovieExample();
		List<Movie> a=mm.selectByName(kw, kw, example);
		PageInfo<Movie> list = new PageInfo<>(a);
		return list;
	}

	public List<MovieType> getTypeId(Integer id) {
		MovieTypeExample example = new MovieTypeExample();
		example.createCriteria().andMovieIdEqualTo(id);
		List<MovieType> list=mtm.selectByExample(example);
		return list;
	}

	public List<Type> getTypeName(Integer id) {
		TypeExample example=new TypeExample();
		example.createCriteria().andTypeIdEqualTo(id);
		List<Type> list=tm.selectByExample(example);
		return list;
	}

	public PageInfo<Actor> findByActorName(String kw,Integer pageNum) {
		//PageHelper.startPage(offset, 2);
		PageHelper.startPage(pageNum, 2);
			kw="%"+kw+"%";
			ActorExample example=new ActorExample();
			List<Actor> a=am.selectByName(kw, kw, example);
			PageInfo<Actor> list = new PageInfo<>(a);
			return list;
	}

	public List<MovieActor> getMovieId(Integer id) {
		MovieActorExample example=new MovieActorExample();
		example.createCriteria().andActorIdEqualTo(id);
		List<MovieActor> list=mam.selectByExample(example);
		return list;
	}

	public List<Movie> getMovieName(Integer movieId) {
		MovieExample example=new MovieExample();
		example.createCriteria().andMovieIdEqualTo(movieId);
		List<Movie> list=mm.selectByExample(example);
		return list;
	}
}
