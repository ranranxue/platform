package com.platform.util;

import org.apache.log4j.Logger;

public class DataTypePaserUtil {
	private static Logger logger = Logger.getLogger(DataTypePaserUtil.class);

	private DataTypePaserUtil() {}

	public static Integer StringToInteger(String value) {

		try {

			return Integer.parseInt(value);
		} catch (Exception e) {
			logger.error("StringToInteger error, String value is "+value);
		}

		return null;
	}

	public static Double StringToDouble(String value) {

		try {

			return Double.parseDouble(value);
		} catch (Exception e) {
			logger.error("StringToDouble error, String value is "+value);
		}

		return null;
	}
	
	public static Float StringToFloat(String value) {

		try {

			return Float.parseFloat(value);
		} catch (Exception e) {
			logger.error("StringToFloat error, String value is "+value);
		}

		return null;
	}

	public static Long StringToLong(String value) {

		try {

			return Long.parseLong(value);
		} catch (Exception e) {
			logger.error("StringToLong error, String value is "+value);
		}

		return null;
	}
	
}
