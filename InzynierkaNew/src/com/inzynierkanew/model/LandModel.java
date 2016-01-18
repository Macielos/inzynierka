package com.inzynierkanew.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import com.google.api.client.json.GenericJson;
import com.inzynierkanew.entities.map.dungeonendpoint.model.Dungeon;
import com.inzynierkanew.entities.map.landendpoint.model.Land;
import com.inzynierkanew.entities.map.landendpoint.model.Passage;
import com.inzynierkanew.entities.map.townendpoint.model.Town;
import com.inzynierkanew.game.GameView;
import com.inzynierkanew.utils.Constants;
import com.inzynierkanew.utils.Point;

import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;

public class LandModel implements IRenderable {

	private static final String TAG = LandModel.class.getSimpleName();
	
	private final int[][] fields;
	private final Town town;

	private final int height;
	private final int width;
	
	private final Map<Integer, DrawableFieldType> fieldTypes;
	private final Map<Point, GenericJson> objects;
	
	private final int cornerX;
	private final int cornerY;
	
	private final int passageIndex;
	private final int townIndex;
	private final int dungeonIndex;
	
	private final GameView gameView;

	public LandModel(GameView gameView, Land land, Town town, List<Dungeon> dungeons, Map<Integer, DrawableFieldType> fieldTypes, int townIndex, int passageIndex,int dungeonIndex) throws IllegalAccessException, IllegalArgumentException, NoSuchFieldException {
		this.gameView = gameView;
		this.height = land.getHeight();
		this.width = land.getWidth();
		this.town = town;

		this.fieldTypes = fieldTypes;
		this.passageIndex = passageIndex;
		this.townIndex = townIndex;
		this.dungeonIndex = dungeonIndex;
		
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
		
		cornerX = land.getMinX();
		cornerY = land.getMinY();
		
		objects = new HashMap<>(land.getPassages().size()+dungeons.size()+(town!=null ? 1 : 0));
		for(Passage passage: land.getPassages()){
			objects.put(new Point(passage.getX()-cornerX, passage.getY()-cornerY), passage);
		}
		for(Dungeon dungeon: dungeons){
			objects.put(new Point(dungeon.getX()-cornerX, dungeon.getY()-cornerY), dungeon);
		}
		if(town!=null){
			objects.put(new Point(town.getX()-cornerX, town.getY()-cornerY), town);
		}
	}

	@Override
	public void render(Canvas canvas) {
		canvas.drawColor(Color.LTGRAY);
		DrawableFieldType drawableFieldType;
		float zoom = gameView.getZoom();
		//TODO draw only what is visible on screen
		for(int j=0; j<height; ++j){
			for(int i=0; i<width; ++i){
				drawableFieldType = fieldTypes.get(fields[j][i]);
				if(drawableFieldType!=null){
					canvas.drawBitmap(drawableFieldType.getBitmap(), gameView.getOffsetX()+zoom*Constants.TILE_SIZE*i, gameView.getOffsetY()+zoom*Constants.TILE_SIZE*j, null);
				}
			}
		}
	}

	@Override
	public void update() {
		
	}
	
	public Queue<Point> findPath(Point start, Point destination){
		Log.i(TAG, "Finding path from " + start + " to " + destination + " using recursive algorithm");
		Queue<Point> path = new LinkedList<>();
		boolean found = findPathInternal(destination, start, null, new HashSet<Point>(), path);
		if(found){
			path.add(destination);
		}
		Log.i(TAG, path.toString());
		if (!found) {
			Log.w(TAG, "Path " + start + "->"+destination+" not found!");
		}
		return path;
	}

	private boolean findPathInternal(Point current, Point destination, Point previous, Set<Point> visitedPoints, Queue<Point> path) {
		if (current.x == destination.x && current.y == destination.y) {
			return path.isEmpty();
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
	
	public GenericJson getObject(int x, int y){
		return getObject(new Point(x, y));
	}
	
	public GenericJson getObject(Point point){
		return objects.get(point);
	}
	
	public boolean isFieldPassable(int x, int y) {
		if(!withinMapSegment(x, y)){
			return false;
		}
		DrawableFieldType drawableFieldType = fieldTypes.get(fields[y][x]);
		if(drawableFieldType == null){
			return false;
		}
		return drawableFieldType.getFieldType().getPassable().booleanValue();
	}
	
	public DrawableFieldType getField(int x, int y) {
		if(!withinMapSegment(x, y)){
			return null;
		}
		DrawableFieldType drawableFieldType = fieldTypes.get(fields[y][x]);
		if(drawableFieldType == null){
			return null;
		}
		return drawableFieldType;
	}
	
	public boolean isDungeon(int x, int y){
		return withinMapSegment(x, y) && fields[y][x] == dungeonIndex;
	}
	
	public boolean isPassage(int x, int y){
		return withinMapSegment(x, y) && fields[y][x] == passageIndex;
	}
	
	public boolean isTown(int x, int y){
		return withinMapSegment(x, y) && fields[y][x] == townIndex;
	}
	
	private boolean withinMapSegment(int x, int y) {
		return x >= 0 && y >= 0 && x < width && y < height;
	}

	public int getCornerX() {
		return cornerX;
	}

	public int getCornerY() {
		return cornerY;
	}
}
