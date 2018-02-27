package com.platform.data;

public class TimeData {
private TimeData(){}
	
	public class TimeFormat{
		
		public final static String Y="yyyy";
		public final static String YM="yyyy-MM";
		public final static String YMD="yyyy-MM-dd";
		public final static String MD="MM-dd";
		public final static String H="HH";
		public final static String HM="HH:mm";
		public final static String HMS="HH:mm:ss";
		public final static String DHMS="dd HH:mm:ss";
		public final static String YMDHM="yyyy-MM-dd HH:mm";
		public final static String YMDHMS="yyyy-MM-dd HH:mm:ss";
		
		
	}
	

	public class RedisTimeOut{
		
		public final static int SessionOut=60*180;
		public final static int CommonDataOut=30;
		public final static int EmailVerificationCodeOut=5*60;
		public final static int EmailAdminListOut=60*60*24*10;
	}

}
