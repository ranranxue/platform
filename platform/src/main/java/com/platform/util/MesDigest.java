package com.platform.util;

import java.math.BigInteger;
import java.security.MessageDigest;

import org.apache.log4j.Logger;
/**
 * 利用java自带的加密机制SHA和MD5来加密
 * @author xrr 2017.10.18

 */

public class MesDigest {
	private MesDigest(){}//构造函数
	private static Logger logger = Logger.getLogger(MesDigest.class);
	

	private static final String KEY_SHA = "SHA";
	
	public static String SHA(String inputStr){
		BigInteger sha=null;
		byte[] inputData=inputStr.getBytes();
		try {
			MessageDigest messageDigest = MessageDigest.getInstance(KEY_SHA);
			messageDigest.update(inputData);
			sha= new BigInteger (messageDigest.digest()); 
			return sha.toString(32);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("SHA error",e);
			
		}
		return null;
	}
	
	private static final String KEY_MD5 = "MD5";

	public static String MD5(String inputStr) {

		BigInteger md5 = null;

		try {
			
			MessageDigest  messageDigest= MessageDigest.getInstance(KEY_MD5);
			
			byte[] inputData = inputStr.getBytes();
			
			messageDigest.update(inputData);
			
			md5 = new BigInteger(messageDigest.digest());
			
			return md5.toString(16);
			
		} catch (Exception e) {
			//e.printStackTrace();
			logger.error("MD5 error",e);
		}

		return null;
	}



}
