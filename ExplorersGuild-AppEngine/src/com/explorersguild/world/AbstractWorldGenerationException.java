package com.explorersguild.world;

import com.explorersguild.utils.WorldGenerationUtils;

public abstract class AbstractWorldGenerationException extends Exception {

	private static final long serialVersionUID = -6158487371847731121L;
	
	private final int[][] mapSegment;
	private final long seed;
	
	public AbstractWorldGenerationException(int[][] mapSegment, long seed, String message) {
		super(message);
		this.mapSegment = mapSegment;
		this.seed = seed;
	}
	
	@Override
	public String toString() {
		return super.toString()+"\nSeed: "+seed+"\nMap Segment: \n"+WorldGenerationUtils.mapToString(mapSegment);
	}
	
}
