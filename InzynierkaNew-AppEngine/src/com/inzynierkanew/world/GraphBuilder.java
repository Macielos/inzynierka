package com.inzynierkanew.world;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;

import com.inzynierkanew.utils.Point;

public class GraphBuilder {
	
	private final Log log = LogFactory.getLog(getClass());
	
	private final Set<Point> points;
	
	private List<Set<Point>> connectedPoints = new ArrayList<>();
	
	private Set<GraphEdge> edges = new HashSet<>();
	
	private int maxSize = 0;
	private Set<Point> biggestSet;
	
	public GraphBuilder(Set<Point> points){
		this.points = points;
	}
	
	public boolean addConnection(Point point1, Point point2){
		Set<Point> containingFirst = null;
		Set<Point> containingSecond = null;
		for(Set<Point> set: connectedPoints){
			if(set.contains(point1)){
				containingFirst = set;
			}
			if(set.contains(point2)){
				containingSecond = set;
			}
		}
		boolean addEdge = true;
		if(containingFirst == null && containingSecond == null){
			connectedPoints.add(newSet(point1, point2));
		}
		else if(containingFirst != null && containingSecond == null){
			containingFirst.add(point2);
		}
		else if(containingFirst == null && containingSecond != null){
			containingSecond.add(point1);
		}
		else if(containingFirst != containingSecond){
			containingFirst.addAll(containingSecond);
			connectedPoints.remove(containingSecond);
			containingSecond.clear();
		} else {
			addEdge = false;
		}
		if(addEdge){
			edges.add(new GraphEdge(point1, point2));
			//log.info(new GraphEdge(point1, point2));
		}
		
		if(containingFirst != null && containingFirst.size() > maxSize){
			maxSize = containingFirst.size();
			biggestSet = containingFirst;
		}
		if(containingSecond != null && containingSecond.size() > maxSize){
			maxSize = containingSecond.size();
			biggestSet = containingSecond;
		}
		
		return !isConnected();
	}
	
	public boolean isConnected(){
		return biggestSet != null && biggestSet.size() == points.size();
	}
	
	public Set<GraphEdge> getEdgesIfConnected(){
		return isConnected() ? edges : null; 
	}
	
	private static Set<Point> newSet(Point... points){
		Set<Point> set = new HashSet<>(points.length);
		for(Point point: points){
			set.add(point);
		}
		return set;
	}
	
}
