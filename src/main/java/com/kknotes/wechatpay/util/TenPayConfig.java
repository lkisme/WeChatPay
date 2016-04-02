package com.kknotes.wechatpay.util;

import java.io.IOException;
import java.util.Properties;

/**
 * requires a properties file named wechat.properties, 
 * otherwise this class will not be loaded successfully
 * 
 * @author kuikui
 *
 */
public class TenPayConfig {

	public static boolean hasProperties(){
		return isSuccessToRead;
	}
	private static boolean isSuccessToRead = false;
	
	public static String APPID ;
	public static String SECRET;
	public static String MCH_ID;
	public static String notifyUri;
	public static Integer EXPIRE_HOUR;
	public static String KEY;
	
	static{
		Properties weChatConfigProperties = new Properties();
		try {
			weChatConfigProperties.load(TenPayConfig.class.getResourceAsStream("/wechat.properties"));
		} catch (IOException e) {
			e.printStackTrace();
			
		}
		APPID = weChatConfigProperties.get("appid").toString();
		SECRET = weChatConfigProperties.getProperty("secret").toString();
		MCH_ID = weChatConfigProperties.getProperty("mch_id").toString();
		notifyUri = weChatConfigProperties.getProperty("notifyUri").toString();
		KEY = weChatConfigProperties.getProperty("key").toString();
		
		String expireHour = weChatConfigProperties.getProperty("expire_hour").toString();
		
		try{
			EXPIRE_HOUR = Integer.valueOf(expireHour);
		}catch(NumberFormatException e){
			EXPIRE_HOUR = 24;
		}
		
		isSuccessToRead = null != APPID && null != SECRET && null != MCH_ID && null != notifyUri && null != EXPIRE_HOUR;
	}
	
	
}
