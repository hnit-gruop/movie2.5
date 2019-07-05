package com.yc.service;

import java.util.List;

import com.yc.bean.Cinema;

public interface CinemaService {
	int addCinema(Cinema cinema);
	
	List<Cinema> getAllCinema(int pageNum);
	
	Cinema getCinemaDetail(int id);
	
	int updataCinema(Cinema cinema);
	
	List<Cinema> getCinemaByName(String name,int pageNum);
}
