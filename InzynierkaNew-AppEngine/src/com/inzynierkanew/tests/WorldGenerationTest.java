package com.inzynierkanew.tests;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;

import com.inzynierkanew.utils.WorldGenerationUtils;
import com.inzynierkanew.world.WorldGenerator;

public class WorldGenerationTest {

	private Log log = LogFactory.getLog(getClass());
	
	@org.junit.Test
	public void testLandGeneration(){
		//while(true){
			WorldGenerator generator = new WorldGenerator();
			generator.generateLand();
	/*		try {
				Thread.sleep(4000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}*/
	}
	
	//@org.junit.Test
	public void testMapToString() {
		int[][] map = new int[][]{
				new int[]{
						1, 2, 3
				}, 
				new int[]{
						6, 5, 4
				}
		};
		log.info(WorldGenerationUtils.mapToString(map));
	}
	
	
}
