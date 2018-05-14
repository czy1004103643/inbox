package com.test.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


/**
 * 读取配置文件 123
 * @author lenovo
 *
 */
public class Config {
	private static String fileNamePath ="config.properties";
	
	public static String getValues(String key){
		Properties props = new Properties();
		InputStream in = null;
		try{
			/*in = new FileInputStream(fileNamePath);*/
			in = Config.class.getClassLoader().getResourceAsStream(fileNamePath);
			props.load(in);
			String value = props.getProperty(key);
			return value;
		}catch(IOException e){
			e.printStackTrace();
			return null;
		}finally{
			if(null!=in)
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		
	}

	public static void main(String[] args){
		System.out.println(getValues("uploadDirectory"));
	}
	
}
