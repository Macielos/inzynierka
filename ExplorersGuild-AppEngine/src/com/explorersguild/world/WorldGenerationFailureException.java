package com.explorersguild.world;

public class WorldGenerationFailureException extends AbstractWorldGenerationException {

	private static final long serialVersionUID = 110883941251765720L;

	public WorldGenerationFailureException(int[][] mapSegment, long seed, String message) {
		super(mapSegment, seed, message);
	}

}
