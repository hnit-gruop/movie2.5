package com.yc.service;

import java.util.List;
import java.util.Map;

import com.yc.bean.Actor;
import com.yc.bean.MovieActor;

public interface ActorService {
	
	String POSITION_DERECTOR = "director";
	
	String POSITION_ACTOR = "actor";
	
	List<Actor> fingActorByMovieActor(List<MovieActor> list);
	
	void add(Actor actor);
	
	int update(Actor actor);
	
	int find(String name);
	
	Actor findById(int id);
	
	Map<String,Object> findActor(int flag,String name,int pageNum);
}
