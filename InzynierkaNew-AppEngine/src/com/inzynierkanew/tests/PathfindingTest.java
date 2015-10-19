package com.inzynierkanew.tests;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import com.inzynierkanew.utils.Point;
import com.inzynierkanew.utils.PointWithAttributes;

public class PathfindingTest {
	
	int[][] map = new int[][]{
			new int[]{ 1, 1, 1, 1, 1, 1}, 
			new int[]{ 0, 0, 0, 1, 1, 1}, 
			new int[]{ 1, 0, 0, 1, 0, 0}, 
			new int[]{ 1, 0, 0, 0, 0, 1}, 
			new int[]{ 1, 1, 1, 1, 1, 1}}; 


	@Test
	public void testFindPath(){
		List<PointWithAttributes> path = findPath(new PointWithAttributes(0, 1), new PointWithAttributes(5, 2));
		System.out.println(path);
	}
	
	public List<PointWithAttributes> findPath(PointWithAttributes start, PointWithAttributes destination){
//		Set<PointWithAttributes> open = new HashSet<>();
//		Set<PointWithAttributes> closed = new HashSet<>();
//		//List<PointWithAttributes> closedList = new ArrayList<>();
//		List<PointWithAttributes> path = new ArrayList<>();
//		
//		start.setH(estimatedCost(start, destination));
//		open.add(start);
//		
//		PointWithAttributes bestPoint;
//		while(!open.isEmpty()){
//			bestPoint = getBestPoint(open);
//			if(bestPoint.equals(destination)){
//				//path found
//				return null;
//			}
//			open.remove(bestPoint);
//			closed.add(bestPoint);
//			
////			if(open.isEmpty()){
////				//path does not exist
////				return null;
////			}
//			for(PointWithAttributes point: neighbourPoints(bestPoint.x, bestPoint.y)){
//				if(closed.contains(point)){
//					continue;
//				}
//				//point.setAttributes(point.getG()+1, estimatedCost(point, destination));
//				if(!open.contains(point)){
//					open.add(point);
//					
//					
//					if(point.getF()<bestPoint.getF()){
//						//update path
//					}
//				} else {
//					open.add(point);
//				}
//			}
//		}
//		
		//assignAttributeValues(start, start, destination);
		
		
		return null;
	}
	
//	private void assignAttributeValues(PointWithAttributes point, PointWithAttributes start, PointWithAttributes destination){
//		
//	}
	
	private PointWithAttributes getBestPoint(Collection<PointWithAttributes> points){
		int lowestF = Integer.MAX_VALUE;
		PointWithAttributes bestPoint = null;
		for(PointWithAttributes point: points){
			if(point.getF()<lowestF){
				lowestF = point.getF();
				bestPoint = point;
			}
		}
		return bestPoint;
	}
	
	private int estimatedCost(Point start, Point destination){
		return Math.abs(start.x - destination.x) + Math.abs(start.y - destination.y);
	}
	
	private List<PointWithAttributes> neighbourPoints(int x, int y){
		List<PointWithAttributes> points = new ArrayList<>(4);
		for(PointWithAttributes point: new PointWithAttributes[] { new PointWithAttributes(x + 1, y), new PointWithAttributes(x - 1, y), new PointWithAttributes(x, y + 1), new PointWithAttributes(x, y - 1) }){
			if(withinMapSegment(point.x, point.y)){
				points.add(point);
			}
		}
		return points;
	}
	
	private boolean withinMapSegment(int x, int y) {
		return x >= 0 && y >= 0 && x < map[0].length && y < map.length;
	}
	
}
