package com.explorersguild.world;

import com.explorersguild.dumps.WorldDump;
import com.explorersguild.endpoints.map.LandEndpoint;
import com.explorersguild.world.WorldGenerator.GenerationOutcome;

public class WorldGeneratorFactory {

	private static final LandEndpoint landEndpoint = new LandEndpoint();

	public static synchronized GenerationOutcome fireWorldGeneration() {
		GenerationOutcome outcome = new WorldGenerator().generateAndPersistLand();
		if (WorldGenerator.DUMP_WORLD_ON_FINISH) {
			new WorldDump(landEndpoint).dumpLands().dumpTerrain();
		}
		return outcome;
	}
}
