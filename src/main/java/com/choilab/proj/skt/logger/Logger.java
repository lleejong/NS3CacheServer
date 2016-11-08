package com.choilab.proj.skt.logger;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Logger {
	
	private static ArrayList<String> logToFileList = new ArrayList<String>();
	
	
	private static String createTimestamp(){
		String timestamp = new SimpleDateFormat("[yyyy/MM/dd HH:mm:ss]").format(new Date());
		return timestamp;
	}
	public static void logToConsole(Object obj){
		System.out.println(createTimestamp() + " " + obj.toString());
	}
	public static void logToFile(Object obj){
		
	}
}
