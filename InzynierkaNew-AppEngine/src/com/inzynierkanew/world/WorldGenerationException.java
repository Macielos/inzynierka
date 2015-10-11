package com.inzynierkanew.world;

import com.inzynierkanew.utils.WorldGenerationUtils;

public class WorldGenerationException extends Exception {

	private final int[][] mapSegment;
	private final long seed;
	
	public WorldGenerationException(int[][] mapSegment, long seed, String message) {
		super(message);
		this.mapSegment = mapSegment;
		this.seed = seed;
	}
	
	@Override
	public String toString() {
		return super.toString()+"\nSeed: "+seed+"\nMap Segment: \n"+WorldGenerationUtils.mapToString(mapSegment);
	}
	
}
