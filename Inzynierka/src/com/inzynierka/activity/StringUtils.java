package com.inzynierka.activity;

public class StringUtils {

	private StringUtils(){
	}
	
	public static boolean isEmpty(String s){
		return s == null || s.length() == 0;
	}
	
	public static boolean isEmpty(byte[] b){
		return b == null || b.length == 0;
	}
	
}
