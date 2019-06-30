package com.yc.controller;

import java.util.ArrayList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yc.bean.User;
//页面跳转
import com.yc.dao.UserMapper;

@Controller
public class PageController {

	@Autowired
	UserMapper userMapper;

	/**
	 * 购票页面
	 */
	@RequestMapping("buyticket")
	public String buyticket(Model m) {
		return "pages/BuyTicket";
	}

	/**
	 * 影院
	 */
	@RequestMapping("cinema")
	public String cinema(Model m) {
		m.addAttribute("index", 3);
		return "pages/Cinema";
	}
}
