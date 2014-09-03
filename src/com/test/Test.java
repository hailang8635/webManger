package com.test;

import com.tool.Constants;

public class Test {

	public static void main(String[] args) {
		String content = "<h3>fdasdfd</h3>";
		System.out.println(content.substring(content.indexOf("<h")+4, content.indexOf("</h")));
		//org.apache.log4j.HTMLLayout
	}

}
