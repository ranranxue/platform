package com.platform.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

public class TimeUtil {
	
	private static Logger logger = Logger.getLogger(TimeUtil.class);  
	
	public static long getCurrentTime(String format){
		
		logger.debug("format : "+format);
		
		if(format==null){
			
			format="yyyy-MM-dd HH:mm:ss";
		}
		
		try{
			
			long l=System.currentTimeMillis();
			
			Date date=new Date(l);
			
			SimpleDateFormat dateFormat=new SimpleDateFormat(format);
			
			String timeString=dateFormat.format(date);
			
			String str=timeString.replaceAll(" ", "").replaceAll("-", "").replaceAll(":", "");
			
			return Long.parseLong(str);
			
		}catch(Exception e){
			
			logger.error("format : "+format);
			logger.error("getCurrentTime is error ");
		}
		
		return 0;
		
	}
	
	
}
