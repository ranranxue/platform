package com.platform.test;




/**
*
* @author 23653_000
* @version ����ʱ�䣺2017��7��14�� ����7:37:16
*/
public class Test {
	public static void main(String[] args) {
//		String jsonString = "{\"code\":2,\"msg\":\"lack necessary parameters\"}";
//		JSONObject object = null;
//		try {
//			object = new JSONObject(jsonString);
//		} catch (JSONException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		try {
//			System.out.println(object.get("code"));
//		} catch (JSONException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		ApiResultMap.init();
//		System.out.println(ApiResultMap.get(1));
		String timeStr = String.valueOf(20170713161011L);
		StringBuilder builder = new StringBuilder();
		builder.append(timeStr.substring(0,4));
		builder.append(".");
		builder.append(timeStr.substring(4,6));
		builder.append(".");
		builder.append(timeStr.substring(6,8));
		builder.append(" ");
		builder.append(timeStr.substring(8,10));
		builder.append(":");
		builder.append(timeStr.substring(10,12));
		System.out.println(builder.toString());
	}
}

