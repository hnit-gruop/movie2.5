package com.yc.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yc.bean.CommentAgreeUser;
import com.yc.bean.Comments;
import com.yc.service.CommentService;
import com.yc.util.DateUtils;

@Controller
public class CommentController {
	
	@Autowired
	CommentService commentService;
	
	@ResponseBody
	@RequestMapping("addComment")
	public String addComment(Comments comments) {
		int addComment = commentService.addComment(comments);
		String convertTimeToFormat = DateUtils.convertTimeToFormat(comments.getCommentsTime().getTime());
		if(addComment>0) {
			return "1";
		}else {
			return "0";
		}
	}
	
	@ResponseBody
	@RequestMapping("agree")
	public String agree(CommentAgreeUser commentAgreeUser) {
		int agree = commentService.agree(commentAgreeUser);
		if(agree>0) {
			return "1";
		}
		return "0";
	}
	
	
	@ResponseBody
	@RequestMapping("concelAgree")
	public String concelAgree(CommentAgreeUser commentAgreeUser) {
		int concelAgree = commentService.concelAgree(commentAgreeUser);
		if(concelAgree>0) {
			return "1";
		}
		return "0";
	}
}
