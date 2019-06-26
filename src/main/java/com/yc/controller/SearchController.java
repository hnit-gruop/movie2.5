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
import com.yc.bean.MovieType;
import com.yc.bean.Type;
import com.yc.dao.UserMapper;


@Controller
public class SearchController {
	List<String> bList=null;
	List<List<String>> aList=null;
	@Resource
	private MovieBiz mb;
	
//	@Autowired
//	UserMapper userMapper;
		
	
	@RequestMapping("search" )
	public String search(Model m,String kw) {
		aList=new ArrayList<>();
		List<Movie> a=null;				
			 a=mb.findByMovieName(kw);		
			 System.out.println(a.size());
			 for(int i=0;i<a.size();i++){
				Integer id= a.get(i).getMovieId();				
				List<MovieType> aid=mb.getTypeId(id);		
				bList = new ArrayList<>();
				List<Type> li=new ArrayList<>();
				for(int j=0;j<aid.size();j++){			
					 li=mb.getTypeName(aid.get(j).getTypeId());
					 bList.add(li.get(0).getName());
				}
				aList.add(bList);				
			 }
			 System.out.println(a);
		System.out.println(aList);
		m.addAttribute("index", 0);
		m.addAttribute("kw", kw);
		 m.addAttribute("movie",a);
			m.addAttribute("movieType",aList);
		return "pages/Search";
	}
}
