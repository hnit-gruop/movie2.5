/*
 * Copyright 2018 KiWiPeach.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.yc.controller;

import com.qq.connect.QQConnectException;

import com.qq.connect.api.OpenID;
import com.qq.connect.api.qzone.UserInfo;
import com.qq.connect.javabeans.AccessToken;
import com.qq.connect.javabeans.qzone.UserInfoBean;
import com.qq.connect.oauth.Oauth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Create Date: 2018/03/30
 * Description: springmvc restfule风格请求改造控制器,
 * 详情参考：http://wiki.connect.qq.com/%E4%BD%BF%E7%94%A8authorization_code%E8%8E%B7%E5%8F%96access_token
 *
 * @author kiwipeach [1099501218@qq.com]
 */
@Controller
public class MvcQQLoginController {

    private static final String QQ_ACCESS_TOKEN = "accessToken";
    private static final String QQ_OPENID = "openid";

    private Logger logger = LoggerFactory.getLogger(MvcQQLoginController.class);

    /**
     * 请求QQ的登陆界面
     *
     * @param request request请求
     * @return QQ登陆页面
     */
    @RequestMapping(value = "qqlogin/login", method = {RequestMethod.GET})
    public String toQQLoginPage(HttpServletRequest request) {
        String authorizeURL = null;
        try {
            authorizeURL = new Oauth().getAuthorizeURL(request);
        } catch (QQConnectException e) {
            logger.error("QQ登陆错误:", e);
        }
        authorizeURL = new StringBuffer("redirect:").append(authorizeURL).toString();
        logger.info("请求QQ登陆地址:{}", authorizeURL);
        return authorizeURL;
    }

    /**
     * FIXME 实际网路请求部分代码可以自己使用httpclient类来进行实现
     * 跳转到登陆成功页面
     *
     * @return 登陆成功页面
     */
    @RequestMapping(value = "/callback", method = {RequestMethod.GET})
    public String toLoginSuccessPage(HttpServletRequest request, Model model) throws QQConnectException {
        //注意：accessToken，openID是最重要的两个东西，要控制好
        HttpSession session = request.getSession();
        AccessToken accessTokenObj = null;
        String accessToken = null;
        String openID = null;
        
        
        AccessToken at = (new Oauth()).getAccessTokenByRequest(request);
        
        System.out.println(at.getAccessToken()+"as");
        accessToken = at.getAccessToken();
        
        OpenID openIDObj = new OpenID(accessToken);
        openID = openIDObj.getUserOpenID();
        
        UserInfo qzoneUserInfo = new UserInfo(accessToken, openID);
        UserInfoBean userInfoBean = qzoneUserInfo.getUserInfo();
        
        
        //获取的用户名
        System.out.println(userInfoBean.getNickname());
        System.out.println(userInfoBean.getMsg());
        System.out.println(userInfoBean.toString());
        return "redirect:index";
        
//        try {
//            //请求：client_id=不告诉你&client_secret=不告诉你&grant_type=authorization_code&code=8263B419034D73FC561177912E4C5C5C&redirect_uri=http%3A%2F%2Flocalhost%3A8080%2Fafterlogin.do
//            //返回："access_token=7E6ABD0D6CD6A9440985E24D005210D8&expires_in=7776000&refresh_token=80A3B27CC7BD4A677847FBFF4E9F43D2"
//            //1.发出第一次请求获取access_token
//            //用户已经登陆过
//            if (session.getAttribute(QQ_ACCESS_TOKEN) != null && session.getAttribute(QQ_OPENID) != null) {
//                accessToken = String.valueOf(session.getAttribute(QQ_ACCESS_TOKEN));
//                openID = String.valueOf(session.getAttribute(QQ_OPENID));
//                System.out.println(accessToken+"as");
//            } else {
//            	System.out.println(accessToken+"first");
//                //用户第一次登陆
//                if ("".equals(accessToken)) {
//                    //第一次登陆非法请求
//                    logger.warn("[ip:{}]请停止当前非法请求!", IpHelper.getIpAddr(request));
//                    return "redirect:/fail";
//                } else {
//                    //第一次合法登陆
//                    //在回调的地址栏中通过Authorization Code获取Access Token，这里工具类进行一步封装了
//                    accessTokenObj = new Oauth().getAccessTokenByRequest(request);
//                    accessToken = accessTokenObj.getAccessToken();
//                    OpenID openIDObj = new OpenID(accessToken);
//                    //2.发出第二次请求获取openid
//                    //GET /oauth2.0/me?access_token=7E6ABD0D6CD6A9440985E24D005210D8
//                    //callback( {"client_id":"不告诉你","openid":"003BBF74E531E193401A0C42B4015605"})
//                    openID = openIDObj.getUserOpenID();
//                    session.setAttribute("accessToken", accessToken);
//                    session.setAttribute("openid", openID);
//                }
//            }
//            UserInfo qzoneUserInfo = new UserInfo(accessToken, openID);
//            //3.发出第三次请求获取用户信息 userInfoBean
//            //GET /user/get_user_info?openid=003BBF74E531E193401A0C42B4015605&oauth_consumer_key=不告诉你&access_token=7E6ABD0D6CD6A9440985E24D005210D8&format=json
//            //返回用户信息
//            UserInfoBean userInfoBean = qzoneUserInfo.getUserInfo();
//            model.addAttribute("userInfoBean", userInfoBean);
//            System.out.println("ub");
//
//        } catch (QQConnectException e) {
//            logger.error("跳转到回调地址失败:", e);
//            return "fail";
//        }
//        return "success";
    }


    /**
     * 注销QQ登陆操作
     *
     * @param request 请求request
     * @return 返回登陆页面
     */
    @RequestMapping(value = "/qqlogin/out", method = {RequestMethod.GET})
    public String toLoutoutPage(HttpServletRequest request) {
        request.getSession().removeAttribute(QQ_ACCESS_TOKEN);
        request.getSession().removeAttribute(QQ_OPENID);
        return "redirect:/index.jsp";
    }


}
