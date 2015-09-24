package com.inzynierkanew.utils;

public abstract class WorldGenerationUtils {
	
	private WorldGenerationUtils(){
		
	}
	
	public static String mapToString(int[][] map){
		StringBuilder sb = new StringBuilder("\n");
		for(int i=0; i<map.length; ++i){
			for(int j=0; j<map[0].length; ++j){
				sb.append(map[i][j]<10 ? " " : "").append(map[i][j]);
			}
			sb.append("\n");
		}
		return sb.toString();
	}
}
