package com.platform.util;

public class NamedByTime {
	public static String getQiNiuFileName(){
		
		
        long firstPart=System.currentTimeMillis();
		int thirdPart=(int)(Math.random()*100000);
		
		return new String(new StringBuilder("").append(firstPart).append(thirdPart));
		
	}

}
