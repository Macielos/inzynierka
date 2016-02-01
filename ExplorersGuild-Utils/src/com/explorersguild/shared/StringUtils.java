package com.explorersguild.shared;

import java.util.List;

public class StringUtils {

	private StringUtils(){
	}
	
	public static boolean isEmpty(String s){
		return s == null || s.length() == 0;
	}
	
	public static boolean isEmpty(byte[] b){
		return b == null || b.length == 0;
	}
	
	public static String join(List<Integer> ints){
		StringBuilder sb = new StringBuilder();
		boolean first = true;
		for(Integer i: ints){
			if(i==null){
				continue;
			}
			if(first){
				first = false;
			}else{
				sb.append(SharedConstants.SEPARATOR);
			}
			sb.append(i);
		}
		return sb.toString();
	}
	
	
}
