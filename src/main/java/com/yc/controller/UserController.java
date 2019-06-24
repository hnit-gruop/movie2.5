package com.yc.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.yc.bean.User;
import com.yc.service.UserService;

@Controller
public class UserController {
	
	
	@Autowired
	UserService userService;
	
	@RequestMapping("logout")
	public String  logout(HttpSession session) {
		session.setAttribute("user", null);
		return "redirect:index";
	}
	
	@RequestMapping("testlog")
	public String login(String username,String password,HttpSession session) {
		User user = userService.login("cxw", "aaa");
		session.setAttribute("user", user);
		return "redirect:index";
	}
	
	@RequestMapping("t")
	public String test1(){
		return "pages/test";
	}
}
