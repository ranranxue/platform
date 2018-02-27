package com.platform.data;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.platform.model.BasicResponse;

public class ApiResult {
	
	private Logger logger = Logger.getLogger(ApiResult.class);  
	
	private String result;
	
	
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public ApiResult(Object object){		
		
		ObjectMapper mapper = new ObjectMapper();  

        try {
    		
        	result = mapper.writeValueAsString(object);
			
		} catch (JsonProcessingException e) {
			
			logger.error("json paser error");
			
			result="{\"code\": \"1\",\"msg\": \"json paser error\"}";

		}  

	}

	public ApiResult(int code,String msg){
		
		ObjectMapper mapper = new ObjectMapper();  

        try {
        	BasicResponse basic=new BasicResponse();
        	basic.setCode(code);
        	basic.setMsg(msg);
        	result = mapper.writeValueAsString(basic);
			
		} catch (JsonProcessingException e) {
			
			logger.error("json paser error");
			
			result="{\"code\": \"1\",\"msg\": \"json paser error\"}";

		}  
        
	}
	
	@Override 
	public String toString(){
		
		return result;
	}

}
