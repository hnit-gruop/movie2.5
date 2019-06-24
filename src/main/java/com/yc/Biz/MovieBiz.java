package com.yc.Biz;

import java.util.List;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yc.bean.Actor;
import com.yc.bean.ActorExample;
import com.yc.bean.Movie;
import com.yc.bean.MovieActor;
import com.yc.bean.MovieActorExample;
import com.yc.bean.MovieExample;
import com.yc.bean.ScoreExample;
import com.yc.dao.ActorMapper;
import com.yc.dao.MovieActorMapper;
import com.yc.dao.MovieMapper;

@Service
public class MovieBiz {
	@Resource
	private  MovieMapper mm;
	
	@Resource
	private  MovieActorMapper mam;
	
	@Resource
	private  ActorMapper am;
	
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

	public List<Movie> findByMovieName(String kw) {
		MovieExample example=new MovieExample();
		example.createCriteria().andNameLike("%"+kw+"%");
		List<Movie> list=mm.selectAll(18,example);
		return list;
	}
}
