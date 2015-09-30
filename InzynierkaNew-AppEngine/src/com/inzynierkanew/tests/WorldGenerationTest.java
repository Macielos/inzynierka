package com.inzynierkanew.tests;

import java.util.HashSet;
import java.util.Set;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.junit.Assert;

import com.inzynierkanew.utils.Point;
import com.inzynierkanew.utils.WorldGenerationUtils;
import com.inzynierkanew.world.WorldGenerator;

public class WorldGenerationTest {

	private Log log = LogFactory.getLog(getClass());
	
	@org.junit.Test
	public void testLandGeneration(){
		while(true){
			WorldGenerator generator = new WorldGenerator();
			generator.generateLand();
			try {
				Thread.sleep(4000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	//@org.junit.Test
	public void testContainsPoints(){
		Set<Point> points = new HashSet<>(2);
		points.add(new Point(1, 1));
		points.add(new Point(2, 4));
		Assert.assertTrue(points.contains(new Point(1, 1)));
		Assert.assertTrue(points.contains(new Point(2, 4)));
		Assert.assertFalse(points.contains(new Point(2, 1)));
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
