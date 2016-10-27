package com.zendaimoney.utils;


import java.util.ResourceBundle;

import org.springside.modules.ee.ServiceException;

 
/**
 * 
 * @author bianxj
 *读取属性文件
 */
public class ConfigurationHelper {
	

	private static Object lock              = new Object();
	private static ConfigurationHelper config     = null;
	private static ResourceBundle rb        = null;
	private static final String CONFIG_FILE = "crm";
	
	private ConfigurationHelper() {
		rb = ResourceBundle.getBundle(CONFIG_FILE);
	}
	
	private static ConfigurationHelper getInstance() {
		synchronized(lock) {
			if(null == config) {
				config = new ConfigurationHelper();
			}
		}
		return (config);
	}
	
	public static String getString(String key) {
		return (getInstance().rb.getString(key));
	}
	
	public static Boolean getBoolean(String key) {
		String value = getString(key);
		if (value == null) {
			throw new ServiceException("格式化错误！");
		}
		return Boolean.valueOf(value);
	}
	public static Double getDouble(String key) {
		String value = getString(key);
		if (value == null) {
			throw new ServiceException("格式化错误！");
		}
		return Double.valueOf(value);
	}
	
	public static Integer getInteger(String key) {
		String value = getString(key);
		if (value == null) {
			throw new ServiceException("格式化错误！");
		}
		return Integer.valueOf(value);
	}
	
	public static String getStringUTF8(String key){
		String str="";
		try {
			str = new String(getInstance().rb.getString(key).getBytes("ISO8859-1"),"UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}
}
