package com.yc.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yc.Biz.MovieBiz;
import com.yc.bean.Actor;
import com.yc.bean.Movie;
import com.yc.bean.MovieActor;
import com.yc.dao.UserMapper;


@Controller
public class SearchController {
	List<String> bList=null;
	List<List<String>> aList=new ArrayList<>();
	@Resource
	private MovieBiz mb;
	
//	@Autowired
//	UserMapper userMapper;
		
	
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
