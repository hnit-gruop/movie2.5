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
	
	@ResponseBody
	@RequestMapping("doLogin")
	public String login(String username,String password,HttpSession session) {
		User user = userService.login(username, password);
		if(user==null) {
			 user = userService.loginByEamil(username, password);
		}
		session.setAttribute("user", user);
		if(user!=null)
			return "1";
		return "0";
	}
	
    @RequestMapping("/login")
    public String log() {
        return "pages/Login";
    }
	
    @RequestMapping("/reg")
    public String reg() {
        return "pages/Register";
    }
	
	@RequestMapping("t")
	public String test1(){
		return "pages/test";
	}
}
