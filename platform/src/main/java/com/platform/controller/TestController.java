package com.platform.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.platform.data.TimeData;
import com.platform.util.RedisUtil;

@Controller
public class TestController {
	private Logger logger=Logger.getLogger(TestController.class);
	@RequestMapping("test")
	private @ResponseBody String  test(HttpServletRequest requestHttp){
		try {
			RedisUtil.setx("name", TimeData.RedisTimeOut.SessionOut, "Xrr");
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("redis error");
			return "redis error";
		}
		
		String value=RedisUtil.get("name");
		logger.debug("the value is"+value);
		
		return value;
		
		
	}
	@RequestMapping("hello")
	private @ResponseBody String  hello(HttpServletRequest requestHttp){
		
		return "hello world";
		
	}

}
