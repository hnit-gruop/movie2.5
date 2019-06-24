package com.yc.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.yc.Biz.MovieBiz;
import com.yc.bean.Actor;
import com.yc.bean.Movie;
import com.yc.bean.MovieActor;
import com.yc.dao.UserMapper;

@Controller
public class RankController {
	
	List<String> bList=null;
	List<List<String>> aList=new ArrayList<>();
	@Resource
	private MovieBiz mb;
	
	@Autowired
	UserMapper userMapper;
	
	/**
	 * 榜单
	 */
	@RequestMapping("rank")
	public String rank(Model m,String type) {	
		List<Movie> a=null;		
		m.addAttribute("index", 4);
		if(type.equals("1")){
			 a=mb.findByMovieScore();			 
			 for(int i=0;i<a.size();i++){
				Integer id= a.get(i).getMovieId();				
				List<MovieActor> aid=mb.getActorId(id);		
				bList = new ArrayList<>();
				List<Actor> li=new ArrayList<>();
				for(int j=0;j<aid.size();j++){			
					 li=mb.getActorName(aid.get(j).getActorId());
					 bList.add(li.get(0).getAname());
				}
				aList.add(bList);				
			 }
			 System.out.println(a);
			 m.addAttribute("movie",a);
				m.addAttribute("actor",aList);
		}
		if(type.equals("2")){
			 a=mb.findByMovieExpectation();		 	 
			 for(int i=0;i<a.size();i++){
				Integer id= a.get(i).getMovieId();				
				List<MovieActor> aid=mb.getActorId(id);		
				bList = new ArrayList<>();
				List<Actor> li=new ArrayList<>();
				for(int j=0;j<aid.size();j++){			
					 li=mb.getActorName(aid.get(j).getActorId());
					 bList.add(li.get(0).getAname());
				}
				aList.add(bList);				
			 }
			 m.addAttribute("movie",a);
				m.addAttribute("actor",aList);
		}
		
		if(type.equals("3")){
			a=mb.findByMovieBoxOffice();			 
			 for(int i=0;i<a.size();i++){
				Integer id= a.get(i).getMovieId();				
				List<MovieActor> aid=mb.getActorId(id);		
				bList = new ArrayList<>();
				List<Actor> li=new ArrayList<>();
				for(int j=0;j<aid.size();j++){			
					 li=mb.getActorName(aid.get(j).getActorId());
					 bList.add(li.get(0).getAname());
				}
				aList.add(bList);				
			 }
			 m.addAttribute("movie",a);
				m.addAttribute("actor",aList);
		}
		
		return "pages/MovieRank";
	}
	
	
	@RequestMapping("search" )
	public String search(Model m,String kw) {
		List<Movie> a=null;				
			 a=mb.findByMovieName(kw);			 
			 for(int i=0;i<a.size();i++){
				Integer id= a.get(i).getMovieId();				
				List<MovieActor> aid=mb.getActorId(id);		
				bList = new ArrayList<>();
				List<Actor> li=new ArrayList<>();
				for(int j=0;j<aid.size();j++){			
					 li=mb.getActorName(aid.get(j).getActorId());
					 bList.add(li.get(0).getAname());
				}
				aList.add(bList);				
			 }
		
		m.addAttribute("index", 0);
		m.addAttribute("kw", kw);
		 m.addAttribute("movie",a);
			m.addAttribute("actor",aList);
		return "pages/Search";
	}
}
