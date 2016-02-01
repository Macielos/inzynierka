package com.explorersguild.world;

public class WorldGenerationSevereException extends AbstractWorldGenerationException {

	private static final long serialVersionUID = 4169946974149392825L;
	
	public WorldGenerationSevereException(int[][] mapSegment, long seed, String message) {
		super(mapSegment, seed, message);
	}
	
}
