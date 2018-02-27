package com.platform.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;





/**
*
* @author Xrr
* @version 20171017
*/
public class RequestUtil {
	private static Logger logger = Logger.getLogger(RequestUtil.class);
	
	/**
	 * 将请求参数转化为Map 原来参数的形式是字符串json格式
	 * @param request
	 * @return
	 */
	public static Map<String,String> getParameterMap(HttpServletRequest request){
		//HttpServletRequest httpRequest = (HttpServletRequest)request;
		//CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(httpRequest.getSession().getServletContext()); 
		//MultipartHttpServletRequest multipartRequest = commonsMultipartResolver.resolveMultipart(httpRequest); 
		
		MultipartResolver resolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		MultipartHttpServletRequest  mRequest = resolver.resolveMultipart(request);
		// 参数Map  
	    Map<String, String[]> properties = mRequest.getParameterMap();  
	    
	    // 返回值Map  
	    Map<String, String> returnMap = new HashMap<String, String>();  
	    Iterator<Map.Entry<String, String[]>> iterator = properties.entrySet().iterator();  
	    
	    Map.Entry<String, String[]> entry;  
	    while (iterator.hasNext()) {  
	        entry = (Map.Entry<String, String[]>) iterator.next();  
	        logger.debug("key is "+entry.getKey()+", value is "+entry.getValue()[0]);
	        returnMap.put(entry.getKey(), entry.getValue()[0]);  
	    }  
	    return returnMap;   		
	}
	
	/**
	 * 
	 * @param request
	 * @return List<MultipartFile>
	 */
	public static List<MultipartFile> getFileMap(HttpServletRequest request){
		MultipartResolver resolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		MultipartHttpServletRequest  mRequest = resolver.resolveMultipart(request);
		
		List<MultipartFile> fileMap = mRequest.getFiles("platformfile");
		return fileMap;
	}
	
	/**
	 * 验证参数是否缺失
	 * @param requestPara
	 * @param request
	 * @return
	 */
	public static boolean validate(String[] para, Map<String, String> requestParams) {

		for (int i = 0; i < para.length; i++) {
			if (requestParams.get(para[i]) == null) {
				return false;
			}
		}
		return true;

	}
}
