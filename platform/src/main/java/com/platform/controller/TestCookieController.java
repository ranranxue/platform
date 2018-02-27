package com.platform.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
@Controller
public class TestCookieController {
	private Logger logger=Logger.getLogger(TestCookieController.class);
	/**
	 * 获取cookies的值
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("get/cookie")
	public @ResponseBody String getCookie(HttpServletRequest request,HttpServletResponse response){
		Cookie[] cookies=request.getCookies();
		if(null==cookies){
			logger.debug("$$$$$$$$$$$$$$$$$$$$$$$$$$");
			logger.debug(" there is no cookie");
		}else{
			for(Cookie cookie:cookies){
				logger.debug("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
				logger.debug("name :"+cookie.getName()+"and value :"+cookie.getValue());
			}
		}
		return "get cookies";
		
	}
	
	@RequestMapping("add/cookie")
	public @ResponseBody String addCookie(HttpServletRequest request,HttpServletResponse response){
		Cookie cookie1=new Cookie("myCookie","1");
		response.addCookie(cookie1);
		Cookie[] cookies1=request.getCookies();
		if(null==cookies1){
			logger.debug("$$$$$$$$$$$$$$$$$$$$$$$$$$");
			logger.debug(" there is no cookie");
		}else{
			for(Cookie cookie:cookies1){
				logger.debug("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
				logger.debug("name :"+cookie.getName()+"and value :"+cookie.getValue());
			}
		}
		
		
		logger.debug("dhcnjcmxzcnnck,njczxnnisjnclxcj");
		Cookie cookie2=new Cookie("myCookie","2");
		
		response.addCookie(cookie2);
		Cookie[] cookies2=request.getCookies();
		if(null==cookies2){
			logger.debug("$$$$$$$$$$$$$$$$$$$$$$$$$$");
			logger.debug(" there is no cookie");
		}else{
			for(Cookie cookie:cookies2){
				logger.debug("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
				logger.debug("name :"+cookie.getName()+"and value :"+cookie.getValue());
			}
		}
		
		
		return "add cookies";
		
	}
	
	
	

}
