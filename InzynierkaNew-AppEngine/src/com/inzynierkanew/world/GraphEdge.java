package com.inzynierkanew.world;

import com.inzynierkanew.utils.Point;

public class GraphEdge {
	
	public final Point point1;
	public final Point point2;
	
	public GraphEdge(Point point1, Point point2) {
		this.point1 = point1;
		this.point2 = point2;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((point1 == null) ? 0 : point1.hashCode());
		result = prime * result + ((point2 == null) ? 0 : point2.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GraphEdge other = (GraphEdge) obj;
		if (point1 == null) {
			if (other.point1 != null)
				return false;
		} else if (!point1.equals(other.point1))
			return false;
		if (point2 == null) {
			if (other.point2 != null)
				return false;
		} else if (!point2.equals(other.point2))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "GraphEdge ["+point1.x+", "+point1.y+"] - ["+point2.x+", "+point2.y + "]";
	}
	
	
	
	
	
}
