package com.yc.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yc.bean.CommentAgreeUser;
import com.yc.bean.Comments;
import com.yc.bean.User;
import com.yc.service.CommentService;
import com.yc.service.MovieService;
import com.yc.service.RedisService;
import com.yc.util.DateUtils;

@Controller
public class CommentController {
	
	@Autowired
	CommentService commentService;
	
	@Autowired
	RedisService redisService;
	
	@ResponseBody
	@RequestMapping("addComment")
	public String addComment(Comments comments,HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("user");
		comments.setUserId(user.getUserId());
		int addComment = commentService.addComment(comments);
		redisService.updateScore(comments.getMovieId(), comments.getScore());
		if(addComment>0) {
			return "1";
		}else {
			return "0";
		}
	}
	
	@ResponseBody
	@RequestMapping("agree")
	public String agree(CommentAgreeUser commentAgreeUser,HttpServletRequest request) {
		
		User user = (User) request.getSession().getAttribute("user");
		commentAgreeUser.setUserId(user.getUserId());
		int agree = commentService.agree(commentAgreeUser);
		if(agree>0) {
			return "1";
		}
		return "0";
	}
	
	@ResponseBody
	@RequestMapping("concelAgree")
	public String concelAgree(CommentAgreeUser commentAgreeUser,HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("user");
		commentAgreeUser.setUserId(user.getUserId());
		int concelAgree = commentService.concelAgree(commentAgreeUser);
		if(concelAgree>0) {
			return "1";
		}
		return "0";
	}
}
