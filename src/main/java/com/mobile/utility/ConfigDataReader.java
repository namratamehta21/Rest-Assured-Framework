package com.mobile.utility;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class ConfigDataReader 
{

static Properties pro;
	
	public static String ConfigDataProvider()
	{
		File src = new File("./Config/Config.properties");
		try 
		{
			FileInputStream fis = new FileInputStream(src);
			
			pro = new Properties();
			
			pro.load(fis);
			
			
		} 
		catch (Exception e) 
		{
			System.out.println("Not able to load config file"+e.getMessage());
		}
		
		return pro.getProperty("Url");
		
	}
}
