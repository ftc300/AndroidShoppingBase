package com.shoping.mall.util;

import java.io.IOException;
import java.util.Properties;

public class EmallProUtil {
	
	private static Properties props;
	static {
		props = new Properties();
		try {
			props.load(EmallProUtil.class.getClassLoader().getResourceAsStream("emall.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String getValueByKey(String key){
		String className = props.getProperty(key);
		return className;
	}
}
