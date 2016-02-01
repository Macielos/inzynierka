package com.explorersguild.tests;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.junit.Test;

import com.explorersguild.utils.Point;
import com.explorersguild.utils.WorldGenerationUtils;
import com.explorersguild.world.GraphEdge;
import com.explorersguild.world.WorldGenerationFailureException;
import com.explorersguild.world.WorldGenerator;

public class PathfindingTest {
	
	private Log log = LogFactory.getLog(getClass());
	
	int[][] map = new int[][]{
			new int[]{ 110, 110, 110, 110, 110, 110}, 
			new int[]{ 100, 100, 100, 110, 110, 110}, 
			new int[]{ 110, 100, 100, 110, 100, 100}, 
			new int[]{ 110, 100, 100, 100, 100, 110}, 
			new int[]{ 110, 110, 110, 110, 110, 110}}; 

	/**
	 * Tests if path between given points has been found. If not, method buildRoadRecursive throws an exception
	 */
	@Test
	public void testFindPath() throws WorldGenerationFailureException{
		WorldGenerator worldGenerator = WorldGenerationTest.newWorldGenerator(map);
		worldGenerator.buildRoadRecursive(new GraphEdge(new Point(0, 1), new Point(5, 2)));
		log.info(WorldGenerationUtils.mapToString(map));
	}
	
}
