package com.yc.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yc.bean.Actor;
import com.yc.bean.ActorExample;
import com.yc.bean.ActorExample.Criteria;
import com.yc.bean.MovieActor;
import com.yc.dao.ActorMapper;
import com.yc.service.ActorService;

@Service
public class ActorServiceImpl implements ActorService {
	@Resource
	ActorMapper am;
	@Override
	public List<Actor> fingActorByMovieActor(List<MovieActor> list) {
		List<Actor> actorList = new ArrayList<>();
		for(MovieActor ma : list) {
			Actor actor = am.selectByPrimaryKey(ma.getActorId());
			actorList.add(actor);
		}
		return actorList;
	}
	
	@Override
	public void add(Actor actor) {
		am.insertSelective(actor);
	}
	@Override
	public int find(String name) {
		ActorExample ae = new ActorExample();
		ae.createCriteria().andAnameEqualTo(name.trim());
		List<Actor> selectByExample = am.selectByExample(ae);
		if(selectByExample.size() > 0) {
			return selectByExample.get(0).getActorId();
		}else {
			return 0;	
		}
		
	}

}
