package com.yc.service;

import java.util.List;

import com.yc.bean.Actor;
import com.yc.bean.MovieActor;

public interface ActorService {
	List<Actor> fingActorByMovieActor(List<MovieActor> list);
	
	void add(Actor actor);
	
	int find(String name);
}
