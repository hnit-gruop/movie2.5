package com.yc.controller;
import java.util.ArrayList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.yc.service.MailService;

@Controller
public class HelloController {
	
	@Autowired
	MailService mailService;
	
	@ResponseBody
    @RequestMapping("/hello")
    public String hello() {
		//mailService.sendMail("1054735193@qq.com", "springboot邮件发送", "哈哈哈哈哈");
        return "Hello Spring Boot!";
    }
	
    @RequestMapping("/test")
    public String thymeleaf(Model m) {
    	m.addAttribute("name", "thymeleaf");
        return "temp/thymeleaf";
    }
    
    //thymelea基本语法
//    @RequestMapping("/t1")
//    public String thymeleaf1(Model m) {
//    	  String htmlContent = "<p style='color:red'> 红色文字</p>";
//          Product currentProduct =new Product(5,"product e", 200);
//          boolean testBoolean = true;
//          List<Product> ps = new ArrayList<>();
//          ps.add(new Product(1,"product a", 50));
//          ps.add(new Product(2,"product b", 100));
//          ps.add(new Product(3,"product c", 150));
//          ps.add(new Product(4,"product d", 200));
//          ps.add(currentProduct);
//          ps.add(new Product(6,"product f", 200));
//          ps.add(new Product(7,"product g", 200));       
//          m.addAttribute("ps", ps);
//          m.addAttribute("htmlContent", htmlContent);
//          m.addAttribute("currentProduct", currentProduct);
//          m.addAttribute("testBoolean", testBoolean);
//          return "temp/test";
//    }
}