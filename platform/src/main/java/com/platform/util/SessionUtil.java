package com.platform.util;

import org.apache.log4j.Logger;



public class SessionUtil {
	private Logger logger=Logger.getLogger(SessionUtil.class);
	private SessionUtil(){};
	
	/**
	 * 检查ticket，顺利返回用户的唯一标识;如果ticket的值不是
	 * @param ticket
	 * @return
	 */
	public String checkSession(String ticket){
		String user =null;
		if(ticket==null){
			return "0";
		}else{
			try {
				user = RedisUtil.get(ticket);
				logger.debug("redis get data back is :"+ user);
			} catch (Exception e) {
				// TODO: handle exception
				logger.error("fail to get the data from redis",e);
			}
			if(user ==null||user.length()==0){
				return "0";
			}else{
				return user;
			}	
		}
		
	}
	

}
