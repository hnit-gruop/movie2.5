package com.yc.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yc.bean.Comments;
import com.yc.bean.Movie;
import com.yc.bean.Score;
import com.yc.bean.User;
import com.yc.bean.Wants;
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
		User user = (User) session.getAttribute("user");
		
		int wantCnt = movieService.getWantCnt(id);
		System.out.println(wantCnt);
		
		
		Movie movie = movieService.get(id);
		movieService.setActor(movie);
		movieService.setTypeName(movie);
		movieService.setBigImage(movie);
		movieService.setSmallImage(movie);
		Wants w = movieService.getWant(user,id);
		List<Comments> commonts = commentService.getCommonts(id);
		commentService.setIfAgree(user, commonts);
		m.addAttribute("score", redisService.getScore(id));
		m.addAttribute("movie", movie);
		m.addAttribute("commonts", commonts);
		m.addAttribute("w", w);
		return "pages/MovieDetail";
	}
	
	
	@ResponseBody
	@RequestMapping("wants")
	public String wants(int movieId,int flag,HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("user");
		int update = movieService.updateWants(user.getUserId(),movieId,flag);
		return update+"";
	}

}
