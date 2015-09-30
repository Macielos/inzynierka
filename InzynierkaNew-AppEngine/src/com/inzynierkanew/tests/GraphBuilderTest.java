package com.inzynierkanew.tests;

import java.util.HashSet;
import java.util.Set;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.junit.Assert;

import com.inzynierkanew.utils.Point;
import com.inzynierkanew.world.GraphBuilder;

public class GraphBuilderTest {

	private Log log = LogFactory.getLog(getClass());
	
	private Set<Point> points;
	private GraphBuilder graphBuilder;
	
	@org.junit.Test
	public void testLandGeneration(){
		points = new HashSet<>();
		Point point1 = new Point(1, 2);
		Point point2 = new Point(3, 4);
		Point point3 = new Point(5, 6);
		Point point4 = new Point(7, 8);
		Point point5 = new Point(9, 10);
		
		points.add(point1);
		points.add(point2);
		points.add(point3);
		points.add(point4);
		points.add(point5);
		
		graphBuilder = new GraphBuilder(points);
		
		graphBuilder.addConnection(point1, point2);
		graphBuilder.addConnection(point1, point2);
		graphBuilder.addConnection(point2, point3);
		graphBuilder.addConnection(point4, point5);
		graphBuilder.addConnection(point5, point1);
		
		Assert.assertTrue(graphBuilder.isConnected());
		Assert.assertEquals(4, graphBuilder.getEdgesIfConnected().size());
	}
		
}
