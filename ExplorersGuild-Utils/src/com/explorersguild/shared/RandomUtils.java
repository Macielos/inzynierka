package com.explorersguild.shared;

import java.util.Random;

public abstract class RandomUtils {

	private static final Random random = new Random();
	
	private RandomUtils(){
		
	}
	
	public static double randomizedFactor(double deviation){
		return 1 - deviation + random.nextDouble()*2*deviation;
	}
}
