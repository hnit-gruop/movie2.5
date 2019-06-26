package com.yc.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.yc.bean.Comments;
import com.yc.bean.Movie;
import com.yc.bean.Score;
import com.yc.service.CommentService;
import com.yc.service.MovieService;
import com.yc.service.MovieTypeService;
import com.yc.service.RedisService;

@Controller
public class MovieController {
	
	@Autowired
	CommentService commentService;



	@Autowired
	StringRedisTemplate stringRedisTemplate;

	@Autowired
	MovieService movieService;
	
	@Autowired
	RedisService redisService;
	
	/**
	 * 首页
	 */
	@RequestMapping(value = { "/", "index" })
	public String index(Model m) {
		List<Movie> listShowing = movieService.listShowing();
		List<Movie> listUpComing = movieService.listUpComing();
		
		m.addAttribute("listShowing", listShowing);
		m.addAttribute("listUpComing", listUpComing);
		m.addAttribute("cnt1", movieService.showingCnt());
		m.addAttribute("cnt2", movieService.upComingCnt());
		m.addAttribute("index", 1);
		
		
		
		return "pages/HomePage";
	}
	
	/**
	 * 电影详情
	 */
	@RequestMapping("movieDetail")
	public String movieDetail(Model m, @RequestParam(required = true) int id,HttpSession session) {
		Movie movie = movieService.get(id);
		movieService.setTypeName(movie);
		movieService.setBigImage(movie);
		movieService.setSmallImage(movie);
		
		List<Comments> commonts = commentService.getCommonts(id);
		commentService.setIfAgree(session, commonts);
		m.addAttribute("score", redisService.getScore(id));
		m.addAttribute("movie", movie);
		m.addAttribute("commonts", commonts);
		return "pages/MovieDetail";
	}
	

}
