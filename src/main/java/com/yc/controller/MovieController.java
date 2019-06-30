package com.yc.controller;

import java.util.Arrays;
import java.util.Collections;
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
import com.yc.bean.Type;
import com.yc.bean.User;
import com.yc.bean.Wants;
import com.yc.service.CommentService;
import com.yc.service.MovieService;
import com.yc.service.MovieTypeService;
import com.yc.service.RedisService;
import com.yc.service.TypeService;

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
	
	@Autowired
	TypeService typeService;
	
	/**
	 * 首页
	 */
	@RequestMapping(value = { "/", "index" })
	public String index(Model m) {
		List<Movie> listShowing = movieService.listShowing(0,8);
		List<Movie> listUpComing = movieService.listUpComing(0,8);
		Collections.shuffle(listUpComing);
		Collections.shuffle(listShowing);
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
	
	/**
	 * 电影列表
	 */
	@RequestMapping("movieList")
	public String movieList(Model m,@RequestParam(defaultValue="1") int selectType) {
		List<Type> typelist = typeService.findAllType();
		List<String> regions = movieService.findAllRegion();
		m.addAttribute("typelist", typelist);
		m.addAttribute("regions", regions);
		m.addAttribute("selectType", selectType);
		m.addAttribute("index", 2);
		return "pages/MovieList";
	}
	
	/**
	 * 电影列表带条件
	 */
	
	@ResponseBody
	@RequestMapping("selectMovie")
	public List<Movie> selectMovie(Model m,Integer selectType,@RequestParam(defaultValue="0")Integer catId,@RequestParam(defaultValue="0")String region,@RequestParam(defaultValue="0")Integer yearId) {

		if(selectType==1) {
			List<Movie> list = movieService.hotMovie();
			return list;
		}else if(selectType==2) {
			List<Movie> list = movieService.hotMovie();
			return list;
		}else if(selectType==3){
			List<Movie> list = movieService.hotMovie();
			return list;
		}
		return null;
	}
	
}
