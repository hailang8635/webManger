package com.tool;

import java.util.LinkedHashMap;
import java.util.Map;

public class Constants {

	public static Map<String,String> type1Map;
	public static String getType1Map(String key){
		if(type1Map!=null){
			return type1Map.get(key);
		}else{
			getType1Map();
			return "无";
		}
	}
	public static Map<String,String> getType1Map(){
		if(type1Map==null){
			type1Map = new LinkedHashMap<String,String>();
			type1Map.put("1", "健康");
			type1Map.put("2", "电影");
			type1Map.put("3", "聊天");
			type1Map.put("4", "情感");
			type1Map.put("5", "校园");
			type1Map.put("6", "女生");
			type1Map.put("7", "生活");
		}
		return type1Map;
	}
}
