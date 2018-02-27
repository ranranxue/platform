package com.platform.util;

import javax.servlet.http.Cookie;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
/**
 * 先暂时保留着，应该前端来设置cookie的
 * @author LENOVO
 *
 */
public class CookieUtil {
	private static Logger logger=Logger.getLogger(CookieUtil.class);
	private CookieUtil(){}//构造函数
	
	@Autowired
	private RedisUtil redisUtil;
	public RedisUtil getRedisUtil() {
		return redisUtil;
	}

	public void setRedisUtil(RedisUtil redisUtil) {
		this.redisUtil = redisUtil;
	}
	
	public static void setCookie(HttpServletRequest request,HttpServletResponse response,String redisKey){
		try {
			logger.debug("start to set cookie");
			Cookie cookie=new Cookie("SessionId",redisKey);
			cookie.setMaxAge(-1);//单位是秒,无限长的时间,那么失效取决于redis那边的缓存
			cookie.setPath("/");
			response.addCookie(cookie);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("fail to set cookie",e);
		}
	}
	
	public String checkCookie(HttpServletRequest request,HttpServletResponse response){
		String info=null;
		Cookie[] cookies=request.getCookies();
		if(cookies!=null){
		  //获取cookies数组中的第一个cookie的value值
			info=cookies[0].getValue();
		}else{
			//没有cookie，说明没有需要登录
			info=null;
		}
		return info;
	}
	/**
	 * 根据redisKey来获取对应人的唯一标识学号
	 * @param redisKey
	 * @return
	 */
	public String getStuid(String redisKey){
		String stuid=null;
		try {
			logger.debug(" get the stuid according to the redisKey");
			stuid=RedisUtil.get(redisKey);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("fail to get the stuid",e);
		}
		return stuid;
		
	}

	
	

}
