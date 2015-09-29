package com.inzynierkanew.utils;

public abstract class WorldGenerationUtils {
	
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
				sb.append(map[i][j]<10 ? " " : "").append(map[i][j]);
			}
			sb.append("\n");
		}
		return sb.toString();
	}
}
