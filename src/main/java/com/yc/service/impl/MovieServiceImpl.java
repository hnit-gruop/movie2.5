package com.yc.service.impl;

import java.lang.reflect.InvocationTargetException;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yc.bean.Actor;
import com.yc.bean.Movie;
import com.yc.bean.MovieActor;
import com.yc.bean.MovieExample;
import com.yc.bean.MovieExample.Criteria;
import com.yc.bean.MovieImage;
import com.yc.bean.MovieType;
import com.yc.bean.Type;
import com.yc.dao.MovieMapper;
import com.yc.service.ActorService;
import com.yc.service.MovieActorService;
import com.yc.service.MovieImageService;
import com.yc.service.MovieService;
import com.yc.service.MovieTypeService;
import com.yc.service.RedisService;
import com.yc.service.ScoreService;
import com.yc.service.TypeService;
import com.yc.util.Utils;

@Service
public class MovieServiceImpl implements MovieService{

	
	@Autowired
	MovieMapper movieMapper;
	
	@Autowired
	MovieImageService movieImageService;
	
	@Autowired
	ScoreService scoreService;
	
	@Autowired
	RedisTemplate redisTemplate;
	
	@Resource
	MovieActorService movieActorService;
	
	@Resource
	MovieTypeService movieTypeService;
	
	@Resource
	TypeService typeService;
	
	@Resource
	ActorService actorService;
	
	@Autowired
	RedisService redisServie;
	
	
	@Override
	public List<Movie> listShowing() {
		List<Movie> listShowing = movieMapper.listShowing();
		//redis中查询分数
		setScore(listShowing);
		return listShowing;
	}

	@Override
	public List<Movie> listUpComing() {
		/*关联查询中设置电影封面优化sql性能*/
		List<Movie> list = movieMapper.listUpComing();
		return list;
	}

	@Override
	public List<Movie> mostExpect() {
		return null;
	}

	@Override
	public List<Movie> top10() {
		return null;
	}

	@Override
	public void setCover(List<Movie> list) {
		for (Movie movie : list) {
			setCover(movie);
		}
	}

	@Override
	public void setCover(Movie movie) {
		String cover = movieImageService.getCover(movie.getMovieId());
		movie.setCoverImage(cover);
	}

	@Override
	public Movie get(int id) {
		Movie m = movieMapper.selectByPrimaryKey(id);
		setCover(m);
		return m;
	}

	@Override
	public void setScore(Movie movie) {
		
//		// 在数据库中取值 
//		double score = scoreService.get(movie.getMovieId()); 
		//在redis中取值
		double avgScore = redisServie.getAvgScore(movie.getMovieId());
		movie.setScore(avgScore);
	}

	@Override
	public void setScore(List<Movie> list) {
		for (Movie movie : list) {
			setScore(movie);
		}
	}

	@Override
	public int upComingCnt() {
		MovieExample e = new MovieExample();
		e.createCriteria().andStatusEqualTo(MovieService.up_coming_status);
		long cnt = movieMapper.countByExample(e);
		return (int)cnt;
	}
	
	@Override
	public int showingCnt() {
		MovieExample e = new MovieExample();
		e.createCriteria().andStatusEqualTo(MovieService.showing_status);
		long cnt = movieMapper.countByExample(e);
		return (int)cnt;
	}

	
	@Override
	public PageInfo<Movie> findAllMoive(int pageNum,String sname,Date sTime) {
		
		PageHelper.startPage(pageNum,5);
		MovieExample me = new MovieExample();
		List<Movie> lm = new ArrayList<>();
		if((sname != null && sname.trim().length() > 0) || sTime != null) {
			Criteria createCriteria = me.createCriteria();
			if(sname != null && sname.trim().length() > 0) {
				createCriteria.andNameLike("%"+sname+"%");
			}
			if(sTime != null) {
				createCriteria.andReleaseTimeEqualTo(sTime);
			}
			lm = movieMapper.selectByExample(me);
			
			for (Movie movie : lm) {
				List<MovieType> findMovieTypeByMovieID = movieTypeService.findMovieTypeByMovieID(movie.getMovieId());
				movie.setType(findMovieTypeByMovieID);
			}
		}else {
			lm = movieMapper.selectByExample(null);
		}
		for (Movie movie : lm) {
			List<MovieType> findMovieTypeByMovieID = movieTypeService.findMovieTypeByMovieID(movie.getMovieId());
			movie.setType(findMovieTypeByMovieID);
		}
		PageInfo<Movie> list = new PageInfo<>(lm);
		return list;
	}

	
	public Map<String, Object> findMovieDetailsByMovieId(int movieId) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		Map<String,Object> map = new HashMap<>();
		Movie movie = movieMapper.selectByPrimaryKey(movieId);
		List<MovieType> findMovieTypeByMovieID = movieTypeService.findMovieTypeByMovieID(movieId);
		List<Type> typeList = new ArrayList<Type>();
		
		for(MovieType mt:findMovieTypeByMovieID) {
			typeList.add(typeService.findTypeByTypeID(mt.getTypeId()));
		}
		Utils.transformBeanToMap(movie, map);
		List<MovieActor> actorByMovieId = movieActorService.getActorByMovieId(movieId);
		List<Actor> fingActorByMovieActor = actorService.fingActorByMovieActor(actorByMovieId);
		List<MovieImage> movieImageByMovieId = movieImageService.getMovieImageByMovieId(movieId);
		map.put("actorList", fingActorByMovieActor);
		map.put("imageList", movieImageByMovieId);
		map.put("typeList", typeList);
		return map;
	}

	@Override
	public int findTotal(String sname,Date date) {
		List<Movie> lm = new ArrayList<>();
		if((sname == null || sname.trim().length() == 0) && date == null) {
			lm = movieMapper.selectByExample(null);
		}else {
			MovieExample me = new MovieExample();
			Criteria createCriteria = me.createCriteria();
			if(sname != null && sname.trim().length() > 0) {
				createCriteria.andNameLike("%"+sname+"%");
			}
			if(date != null) {
				createCriteria.andReleaseTimeEqualTo(date);
			}
			lm = movieMapper.selectByExample(me);
		}
		 
		return lm.size();
	}

	@Override
	public int updateMovie(Movie movie) {
		int updateByPrimaryKeySelective = movieMapper.updateByPrimaryKeySelective(movie);
		return updateByPrimaryKeySelective;
	}

	@Override
	public int add(Movie movie) {
		int insert = movieMapper.insert(movie);
		return insert;
	}
	
	@Override
	public int getMovieId(Movie movie) {
		MovieExample me = new MovieExample();
		me.createCriteria().andNameEqualTo(movie.getName());
		List<Movie> selectByExample = movieMapper.selectByExample(me);
		if(selectByExample.size() > 0) {
			return selectByExample.get(0).getMovieId();
		}else {
			return 0;
		}
	}

	@Override
	public int deleteMovie(int id,String number) {
		
		Movie m = new Movie();
		if(number.equals("1")) {
			m.setStatus(typical_status);
		}else {
			m.setStatus(showing_status);
		}
		
		m.setMovieId(id);
		return movieMapper.updateByPrimaryKeySelective(m);
	}

	@Override
	public void setTypeName(List<Movie> list) {
		
	}

	@Override
	public void setTypeName(Movie movie) {
		List<Movie> list = movieMapper.getTypeNameList(movie.getMovieId());
		List<Type> listType = new ArrayList<>();
		for (Movie m : list) {
			listType.add(m.getTempType());
		}
		movie.setListType(listType);
	}

	@Override
	public void setBigImage(Movie movie) {
		String bigImage = movieImageService.getBig(movie.getMovieId());
		movie.setBigImage(bigImage);
	}

	@Override
	public void setSmallImage(Movie movie) {
		List<MovieImage> list = movieImageService.getSmall(movie.getMovieId());
		movie.setSmallImage(list);
	}
}
