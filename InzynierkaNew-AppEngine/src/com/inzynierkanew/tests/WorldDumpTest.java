package com.inzynierkanew.tests;

import org.junit.Test;

import com.inzynierkanew.dumps.WorldDump;

public class WorldDumpTest {
	
	@Test
	public void testDump(){
		new WorldDump(WorldGenerationTest.mockLandEndpointLandsForDump()).dumpTerrain();
	}
	
}
