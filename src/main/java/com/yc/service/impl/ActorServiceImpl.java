package com.yc.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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

	@Override
	public Map<String,Object> findActor(int flag,String name,int pageNum) {
		PageHelper.startPage(pageNum, 5);
		Map<String,Object> map = new HashMap<>();
		List<Actor> la = new ArrayList<>();
		if(flag == 0) {
			if(name == null || name.trim().equals("")) {
				la = am.selectByExample(null);
			}else {
				ActorExample ae = new ActorExample();
				ae.createCriteria().andAnameLike("%"+name+"%");
				la = am.selectByExample(ae);
			}
		}else if(flag == 1){
			
			if(name == null || name.trim().equals("")) {
				ActorExample ae = new ActorExample();
				ae.createCriteria().andPositionEqualTo("actor");
				la = am.selectByExample(ae);
			}else {
				ActorExample ae = new ActorExample();
				ae.createCriteria().andAnameLike("%"+name+"%").andPositionEqualTo("actor");
				la = am.selectByExample(ae);
			}
		}else {
			if(name == null || name.trim().equals("")) {
				ActorExample ae = new ActorExample();
				ae.createCriteria().andPositionEqualTo("director");
				la = am.selectByExample(ae);
			}else {
				ActorExample ae = new ActorExample();
				ae.createCriteria().andAnameLike("%"+name+"%").andPositionEqualTo("director");
				la = am.selectByExample(ae);
			}
		}

		PageInfo<Actor> list = new PageInfo<>(la);
		map.put("total", list.getTotal());
		map.put("list", list.getList());
		return map;
	}

	@Override
	public Actor findById(int id) {
		return am.selectByPrimaryKey(id);
	}

	@Override
	public int update(Actor actor) {
		return am.updateByPrimaryKeySelective(actor);
	}

}
