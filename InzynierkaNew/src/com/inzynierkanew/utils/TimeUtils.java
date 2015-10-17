package com.inzynierkanew.utils;

public abstract class TimeUtils {

	private TimeUtils(){
		
	}
	
	public static long now(){
		return System.currentTimeMillis();
	}
	
	public static long timeElapsed(long time){
		return now() - time;
	}
	
}
