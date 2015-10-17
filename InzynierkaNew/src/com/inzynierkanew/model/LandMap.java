package com.inzynierkanew.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;

import com.inzynierkanew.entities.map.landendpoint.model.Land;
import com.inzynierkanew.utils.Constants;
import com.inzynierkanew.utils.Point;

public class LandMap implements IRenderable {

	private static final String TAG = LandMap.class.getSimpleName();
	
	private int[][] fields;
	private Land land;
	
	private int height;
	private int width;
	
	private Map<Integer, Bitmap> bitmaps = new HashMap<>();
	
	private float offsetX;
	private float offsetY;
	
	// FIELD TYPES - TEMP
	public static final int EMPTY = 0;
	public static final int PASSABLE = 1;
	public static final int NON_PASSABLE = 2;
	public static final int EXISTING_LAND = 3;
	public static final int EXISTING_LAND_PASSAGE = 4;
	public static final int CROSSROAD = 5;

	public static final int ROAD = 6;
	public static final int PASSAGE = 7;
	public static final int TOWN = 8;
	public static final int DUNGEON = 9;
	
	public LandMap(Land land, Map<Integer, Bitmap> bitmaps){
		this.land = land;
		this.bitmaps = bitmaps;
		this.height = land.getHeight();
		this.width = land.getWidth();
		
		fields = new int[height][];
		for(int i=0; i<height; ++i){
			fields[i] = new int[width];
		}
		
		int x, y, i=0;
		for(int field: land.getFields()){
			x = i % width;
			y = i / width;
			fields[y][x] = field;
			++i;
		}
	}
	
	@Override
	public void render(Canvas canvas) {
		canvas.drawColor(Color.LTGRAY);
		Bitmap bitmap;
		for(int j=0; j<height; ++j){
			for(int i=0; i<width; ++i){
				bitmap = bitmaps.get(fields[j][i]);
				if(bitmap!=null){
					canvas.drawBitmap(bitmap, offsetX+Constants.TILE_SIZE*i, offsetY+Constants.TILE_SIZE*j, null);
				}
			}
		}
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}
	
	public void setOffset(float offsetX, float offsetY){
		this.offsetX = offsetX;
		this.offsetY = offsetY;
	}
	
	public Queue<Point> findPath(Point start, Point destination){
		//TODO ostatni pkt nie trafia, startPoint jest dwa razy
		//TODO jak gracz kliknie ponownie w czasie trasy - albo przerywac droge i wytyczac nowa albo ignorowac klik
		Log.i(TAG, "Finding path from " + start + " to " + destination + " using recursive algorithm");
		Queue<Point> path = new LinkedList<>();
		boolean found = findPathInternal(start, destination, null, new HashSet<Point>(), path);
		Log.i(TAG, path.toString());
		if (!found) {
			Log.w(TAG, "Path " + start + "->"+destination+" not found!");
		}
		return path;
	}

	private boolean findPathInternal(Point current, Point destination, Point previous, Set<Point> visitedPoints, Queue<Point> path) {
		if (current.x == destination.x && current.y == destination.y) {
			if(path.isEmpty()){
				path.add(current);
				return true;
			};
			return false;
		}

		visitedPoints.add(current);

		List<Point> orderedNeighbourPoints = getOrderedNeighbourPoints(current, destination, previous, visitedPoints);

		boolean found = false;
		for (Point next : orderedNeighbourPoints) {
			found = findPathInternal(next, destination, current, visitedPoints, path);
			if (found) {
				path.add(next);
				return true;
			}
		}
		return false;

	}

	private List<Point> getOrderedNeighbourPoints(Point current, Point destination, Point previous, Set<Point> visitedPoints) {
		int diffX = destination.x - current.x;
		int diffY = destination.y - current.y;

		List<Point> orderedNeighbourPointsTemp = new ArrayList<>(4);
		if (diffX > 0) {
			if (diffY > 0) {
				if (Math.abs(diffX) > Math.abs(diffY)) {
					orderedNeighbourPointsTemp.add(new Point(current.x + 1, current.y));
					orderedNeighbourPointsTemp.add(new Point(current.x, current.y + 1));
					orderedNeighbourPointsTemp.add(new Point(current.x, current.y - 1));
					orderedNeighbourPointsTemp.add(new Point(current.x - 1, current.y));
				} else {
					orderedNeighbourPointsTemp.add(new Point(current.x, current.y + 1));
					orderedNeighbourPointsTemp.add(new Point(current.x + 1, current.y));
					orderedNeighbourPointsTemp.add(new Point(current.x - 1, current.y));
					orderedNeighbourPointsTemp.add(new Point(current.x, current.y - 1));
				}
			} else if (diffY < 0) {
				if (Math.abs(diffX) > Math.abs(diffY)) {
					orderedNeighbourPointsTemp.add(new Point(current.x + 1, current.y));
					orderedNeighbourPointsTemp.add(new Point(current.x, current.y - 1));
					orderedNeighbourPointsTemp.add(new Point(current.x, current.y + 1));
					orderedNeighbourPointsTemp.add(new Point(current.x - 1, current.y));
				} else {
					orderedNeighbourPointsTemp.add(new Point(current.x, current.y - 1));
					orderedNeighbourPointsTemp.add(new Point(current.x + 1, current.y));
					orderedNeighbourPointsTemp.add(new Point(current.x - 1, current.y));
					orderedNeighbourPointsTemp.add(new Point(current.x, current.y + 1));
				}
			} else {
				orderedNeighbourPointsTemp.add(new Point(current.x + 1, current.y));
				orderedNeighbourPointsTemp.add(new Point(current.x, current.y - 1));
				orderedNeighbourPointsTemp.add(new Point(current.x, current.y + 1));
				orderedNeighbourPointsTemp.add(new Point(current.x - 1, current.y));
			}
		} else if (diffX < 0) {
			if (diffY > 0) {
				if (Math.abs(diffX) > Math.abs(diffY)) {
					orderedNeighbourPointsTemp.add(new Point(current.x - 1, current.y));
					orderedNeighbourPointsTemp.add(new Point(current.x, current.y + 1));
					orderedNeighbourPointsTemp.add(new Point(current.x, current.y - 1));
					orderedNeighbourPointsTemp.add(new Point(current.x + 1, current.y));
				} else {
					orderedNeighbourPointsTemp.add(new Point(current.x, current.y + 1));
					orderedNeighbourPointsTemp.add(new Point(current.x - 1, current.y));
					orderedNeighbourPointsTemp.add(new Point(current.x + 1, current.y));
					orderedNeighbourPointsTemp.add(new Point(current.x, current.y - 1));
				}
			} else if (diffY < 0) {
				if (Math.abs(diffX) > Math.abs(diffY)) {
					orderedNeighbourPointsTemp.add(new Point(current.x - 1, current.y));
					orderedNeighbourPointsTemp.add(new Point(current.x, current.y - 1));
					orderedNeighbourPointsTemp.add(new Point(current.x, current.y + 1));
					orderedNeighbourPointsTemp.add(new Point(current.x + 1, current.y));
				} else {
					orderedNeighbourPointsTemp.add(new Point(current.x - 1, current.y));
					orderedNeighbourPointsTemp.add(new Point(current.x + 1, current.y));
					orderedNeighbourPointsTemp.add(new Point(current.x, current.y + 1));
					orderedNeighbourPointsTemp.add(new Point(current.x, current.y - 1));
				}
			} else {
				orderedNeighbourPointsTemp.add(new Point(current.x - 1, current.y));
				orderedNeighbourPointsTemp.add(new Point(current.x, current.y - 1));
				orderedNeighbourPointsTemp.add(new Point(current.x, current.y + 1));
				orderedNeighbourPointsTemp.add(new Point(current.x + 1, current.y));
			}
		} else {
			if (diffY > 0) {
				orderedNeighbourPointsTemp.add(new Point(current.x, current.y + 1));
				orderedNeighbourPointsTemp.add(new Point(current.x - 1, current.y));
				orderedNeighbourPointsTemp.add(new Point(current.x + 1, current.y));
				orderedNeighbourPointsTemp.add(new Point(current.x, current.y - 1));
			} else if (diffY < 0) {
				orderedNeighbourPointsTemp.add(new Point(current.x, current.y - 1));
				orderedNeighbourPointsTemp.add(new Point(current.x - 1, current.y));
				orderedNeighbourPointsTemp.add(new Point(current.x + 1, current.y));
				orderedNeighbourPointsTemp.add(new Point(current.x, current.y + 1));
			}
		}

		List<Point> orderedNeighbourPoints = new ArrayList<>(previous == null ? 4 : 3);
		for (Point point : orderedNeighbourPointsTemp) {
			if (isFieldPassable(point.x, point.y) && !point.equals(previous) && !visitedPoints.contains(point)) {
				orderedNeighbourPoints.add(point);
			}
		}
		return orderedNeighbourPoints;
	}
	
	public boolean isFieldPassable(int x, int y) {
		return withinMapSegment(x, y) && hasType(x, y, PASSABLE, ROAD, CROSSROAD, PASSAGE, DUNGEON, TOWN);
	}
	
	private boolean withinMapSegment(int x, int y) {
		return x >= 0 && y >= 0 && x < width && y < height;
	}
	
	private boolean hasType(int x, int y, int... types) {
		if(!withinMapSegment(x, y)){
			return false;
		}
		for(int type: types){
			if(fields[y][x] == type){
				return true;
			}
		}
		return false;
	}

}
