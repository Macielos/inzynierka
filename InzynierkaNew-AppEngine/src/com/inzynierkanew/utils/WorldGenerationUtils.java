package com.inzynierkanew.utils;

import java.util.HashMap;
import java.util.Map;

public abstract class WorldGenerationUtils {
	
	private static final Map<Integer, String> printableSymbols = new HashMap<>();
	
	static {
		printableSymbols.put(0, " ");
		printableSymbols.put(1, "1");
		printableSymbols.put(2, ".");
		printableSymbols.put(6, "+");
		printableSymbols.put(7, "I");
		printableSymbols.put(8, "O");
		printableSymbols.put(9, "X");
	}
	
	private WorldGenerationUtils(){
		
	}
	
	public static String mapToString(int[][] map){
		return mapToString(map, true);
	}
	
	public static String mapToString(int[][] map, boolean axis){
		StringBuilder sb = new StringBuilder("\n");
		if(axis){
			sb.append("   ");
			for(int j=0; j<map[0].length; ++j){
				sb.append(j<10 ? " " : "").append(j);
			}
			sb.append("\n");
		}
		
		for(int i=0; i<map.length; ++i){
			if(axis){
				sb.append(i).append(i<10 ? "  " : " ");
			}
			for(int j=0; j<map[0].length; ++j){
				sb.append(map[i][j]<10 ? " " : "").append(printableSymbols.get(map[i][j])==null ? map[i][j] : printableSymbols.get(map[i][j]));
			}
			sb.append("\n");
		}
		return sb.toString();
	}
}
