package com.yc.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yc.bean.Cinema;
import com.yc.bean.CinemaExample;
import com.yc.bean.CinemaExample.Criteria;
import com.yc.dao.CinemaMapper;
import com.yc.service.CinemaService;

@Service
public class CinemaServiceImpl implements CinemaService{
	@Resource
	CinemaMapper cinemaMapper;
	
	@Override
	public int addCinema(Cinema cinema) {
		int result = cinemaMapper.insertSelective(cinema);
		return result;
	}

	@Override
	public List<Cinema> getAllCinema(int pageNum) {
		PageHelper.startPage(pageNum, 5);
		List<Cinema> selectByExample = cinemaMapper.selectByExample(null);
		PageInfo<Cinema> page = new PageInfo<>(selectByExample);
		return page.getList();
	}

	@Override
	public Cinema getCinemaDetail(int id) {
		Cinema cinema = cinemaMapper.selectByPrimaryKey(id);
		return cinema;
	}

	@Override
	public int updataCinema(Cinema cinema) {
		int result = cinemaMapper.updateByPrimaryKeySelective(cinema);
		return result;
	}

	@Override
	public List<Cinema> getCinemaByName(String name,int pageNum) {
		CinemaExample ce = new CinemaExample();
		PageHelper.startPage(pageNum, 5);
		ce.createCriteria().andNameLike("%"+name+"%");
		List<Cinema> selectByExample = cinemaMapper.selectByExample(ce);
		PageInfo<Cinema> page = new PageInfo<>(selectByExample);
		return page.getList();
	}

}
