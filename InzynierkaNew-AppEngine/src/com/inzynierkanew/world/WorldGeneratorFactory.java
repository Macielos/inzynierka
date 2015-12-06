package com.inzynierkanew.world;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;

import com.inzynierkanew.dumps.WorldDump;
import com.inzynierkanew.endpoints.map.LandEndpoint;


public class WorldGeneratorFactory {
	
	//private static final int MIN_LANDS = 3;
	//private static final double LANDS_PER_PLAYER = 0.25;
	//private static final int COUNT = 2;

	private static final Log log = LogFactory.getLog(WorldGeneratorFactory.class);
	
	public static synchronized void fireWorldGeneration() throws WorldGenerationException{
		LandEndpoint landEndpoint = new LandEndpoint();
		new WorldGenerator().generateAndPersistLand();
		WorldDump dump = new WorldDump(landEndpoint);
		dump.dumpLands();
		dump.dumpTerrain();
	}
}
