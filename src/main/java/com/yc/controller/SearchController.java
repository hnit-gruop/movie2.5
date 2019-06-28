package com.yc.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.pagehelper.PageInfo;
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
	List<String> cList=null;
	List<List<String>> dList=null;
	@Resource
	private MovieBiz mb;
	
//	@Autowired
//	UserMapper userMapper;
		
	
	@RequestMapping("search" )
	public String search(Model m,String kw,String type,String pageNum) {

		aList=new ArrayList<>();
		dList=new ArrayList<>();
		PageInfo<Movie> a=null;	
		PageInfo<Actor> b=null;
			 a=mb.findByMovieName(kw,Integer.parseInt(pageNum));		
			 b=mb.findByActorName(kw,Integer.parseInt(pageNum));
			 System.out.println(a.getPageSize());
			 System.out.println("---------------------------------"+a);
			 for(int i=0;i<a.getSize();i++){		
				 Integer id=a.getList().get(i).getMovieId();
				List<MovieType> aid=mb.getTypeId(id);		
				bList = new ArrayList<>();
				List<Type> li=new ArrayList<>();
				for(int j=0;j<aid.size();j++){			
					 li=mb.getTypeName(aid.get(j).getTypeId());
					 bList.add(li.get(0).getName());
				}
				aList.add(bList);				
			 }
			 
			 for(int i=0;i<b.getSize();i++){
					Integer id= b.getList().get(i).getActorId();
					List<MovieActor> aid=mb.getMovieId(id);		
					cList = new ArrayList<>();
					List<Movie> li=new ArrayList<>();
					for(int j=0;j<aid.size();j++){
						if(j<3){
							
						
						 li=mb.getMovieName(aid.get(j).getMovieId());
						 
						 cList.add("《"+li.get(0).getName()+"》");
						}else{
							break;
						}
					}
					dList.add(cList);				
				 }
		m.addAttribute("actormovie", dList);
		m.addAttribute("type", type);
		m.addAttribute("index", 0);
		m.addAttribute("kw", kw);
		 m.addAttribute("movie",a);
			m.addAttribute("movieType",aList);
			m.addAttribute("actor",b);
		return "pages/Search";
	}
}
