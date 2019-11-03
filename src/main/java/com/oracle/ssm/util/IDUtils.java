package com.oracle.ssm.util;


import java.text.SimpleDateFormat;
import java.util.Date;

public class IDUtils {
	private static SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddhhmmssms");
	
	public static String getId(){
		Date date=new Date();
				
		return sdf.format(date)+(int)(Math.random()*8999+1000);
	}

}
