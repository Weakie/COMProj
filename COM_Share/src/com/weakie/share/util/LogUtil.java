package com.weakie.share.util;

public class LogUtil {

	public static void info(String info){
		System.out.println(info);
	}
	
	public static void info(Throwable info){
		System.out.println(info);
	}
	
	public static void error(Throwable info){
		info.printStackTrace();
	}
	
	public static void error(String info){
		System.out.println(info);
	}
}
