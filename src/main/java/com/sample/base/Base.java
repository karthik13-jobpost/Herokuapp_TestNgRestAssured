package com.sample.base;


import com.sample.utils.XLUtility;

import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class Base {
	protected Properties p,up;
	protected FileOutputStream outputstream ;
	protected XLUtility xlutil;
	protected String csvPath,baseURL;
	protected int totalRows, statCode = 0;

	protected File upropFile;
	public Base(){

		p = new Properties();
		File propFile = new File(System.getProperty("user.dir")+"\\src\\main\\resources\\config.properties");
		try {
			FileInputStream reader = new FileInputStream(propFile);
			p.load(reader);
		} catch (Throwable e) {
			e.printStackTrace();
		}


		csvPath = p.getProperty("csvPath");
		baseURL = p.getProperty("baseURL");

		
		
	}


	public void setValue(String key, String value) {
		up = new Properties();
		 upropFile = new File(System.getProperty("user.dir")+"\\src\\test\\resources\\udata.properties");
	  
	    try {
	        final FileInputStream configStream = new FileInputStream(upropFile);
	        up.load(configStream);
	        configStream.close();
	        up.setProperty(key, value);
	        final FileOutputStream output = new FileOutputStream(upropFile);
	        up.store(output, "");
	        output.close();
	    } catch (IOException ex) {
	        ex.printStackTrace();
	    }
	}
	public String getValue(String key) {
        String value = null;
        try {
        	up = new Properties();
   		 upropFile = new File(System.getProperty("user.dir")+"\\src\\test\\resources\\udata.properties");
   	  
            if (upropFile.exists()) {
                up.load(new FileInputStream(upropFile));
                value = up.getProperty(key);
            }
        } catch (Exception e) {
            System.out.println("Failed to read from udata.properties");
        }
        return value;
    }

}
