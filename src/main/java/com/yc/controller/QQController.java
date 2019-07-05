package com.yc.controller;

import com.alibaba.fastjson.JSONObject;
import com.yc.util.QQHttpClient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.URLEncoder;
import java.util.UUID;

@Controller
public class QQController {

	@Value("${qq.oauth.http}")
	private String http;

	/**
	 * 发起请求
	 * 
	 * @param session
	 * @return
	 */
	@GetMapping("/qq/oauth")
	public String qq(HttpSession session) {
		// QQ互联中的回调地址
		String backUrl = "http://chenxuwen.com/callback";

		// 用于第三方应用防止CSRF攻击
		String uuid = UUID.randomUUID().toString().replaceAll("-", "");
		session.setAttribute("state", uuid);

		// Step1：获取Authorization Code
		String url = "https://graph.qq.com/oauth2.0/authorize?response_type=code" + "&client_id=" + QQHttpClient.APPID
				+ "&redirect_uri=" + URLEncoder.encode(backUrl) + "&state=" + uuid;
		return "redirect:" + url;
	}

	/**
	 * QQ回调
	 * @param request
	 * @return
	 */
	@GetMapping("/callback")
	public String qqcallback(HttpServletRequest request) throws Exception {
		HttpSession session = request.getSession();

		String code = request.getParameter("code");
		String state = request.getParameter("state");
		String uuid = (String) session.getAttribute("state");

		if (uuid != null) {
			if (!uuid.equals(state)) {
				throw new Exception("QQ,state错误");
			}
		}

		// Step2：通过Authorization Code获取Access Token
		String backUrl = http + "/callback";
		String url = "https://graph.qq.com/oauth2.0/token?grant_type=authorization_code" + "&client_id="
				+ QQHttpClient.APPID + "&client_secret=" + QQHttpClient.APPKEY + "&code=" + code + "&redirect_uri="
				+ backUrl;

		String access_token = QQHttpClient.getAccessToken(url);

		// Step3: 获取回调后的 openid 值
		url = "https://graph.qq.com/oauth2.0/me?access_token=" + access_token;
		String openid = QQHttpClient.getOpenID(url);

		// Step4：获取QQ用户信息
		url = "https://graph.qq.com/user/get_user_info?access_token=" + access_token + "&oauth_consumer_key="
				+ QQHttpClient.APPID + "&openid=" + openid;

		JSONObject jsonObject = QQHttpClient.getUserInfo(url);

		// 也可以放到Redis和mysql中
		session.setAttribute("openid", openid); // openid,用来唯一标识qq用户
		session.setAttribute("nickname", (String) jsonObject.get("nickname")); // QQ名
		session.setAttribute("figureurl_qq_2", (String) jsonObject.get("figureurl_qq_2")); // 大小为100*100像素的QQ头像URL
		
		
		//openid,昵称，头像url
		System.out.println("openid:"+openid);
		System.out.println("nickname:"+jsonObject.get("nickname"));
		System.out.println("figureurl_qq_2:"+ jsonObject.get("figureurl_qq_2"));
		
		//登陆成功后
		
		//从数据库查询是否有这个用户
		
		
		//有则直接把user放入session
		
		//没有则插入一条用户信息
		
		
		return "redirect:index";
	}

}
