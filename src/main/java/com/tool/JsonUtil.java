package com.tool;

import java.text.SimpleDateFormat;

import org.codehaus.jackson.map.ObjectMapper;
public class JsonUtil {

	public static ObjectMapper objectMapper;
	public static ObjectMapper getObjectMapper(){
		if(objectMapper!=null){
		}else{
			objectMapper = new ObjectMapper();
			objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		}
		return objectMapper;
		
	}
}
