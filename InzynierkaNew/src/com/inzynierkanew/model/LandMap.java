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
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;

import com.google.api.client.json.GenericJson;
import com.inzynierkanew.R;
import com.inzynierkanew.entities.map.fieldtypeendpoint.model.FieldType;
import com.inzynierkanew.entities.map.landendpoint.model.Dungeon;
import com.inzynierkanew.entities.map.landendpoint.model.Land;
import com.inzynierkanew.entities.map.landendpoint.model.Passage;
import com.inzynierkanew.entities.map.landendpoint.model.Town;
import com.inzynierkanew.game.GameView;
import com.inzynierkanew.utils.Constants;
import com.inzynierkanew.utils.Point;

public class LandMap implements IRenderable {

	private static final String TAG = LandMap.class.getSimpleName();
	
	private final int[][] fields;
	private final Land land;
	
	private final int height;
	private final int width;
	
	private final Map<Integer, DrawableFieldType> fieldTypes;
	private final Map<Point, GenericJson> objects;
	
	private float offsetX;
	private float offsetY;
	
	private final int cornerX;
	private final int cornerY;
	
	private int PASSAGE;
	private int TOWN;
	private int DUNGEON;
	
	private final GameView gameView;

	public LandMap(GameView gameView, Land land, List<FieldType> fieldTypes) throws IllegalAccessException, IllegalArgumentException, NoSuchFieldException {
		this.gameView = gameView;
		this.land = land;
		this.height = land.getHeight();
		this.width = land.getWidth();

		this.fieldTypes = new HashMap<>(fieldTypes.size());
		for(FieldType fieldType: fieldTypes){
			this.fieldTypes.put(fieldType.getId().intValue(), new DrawableFieldType(fieldType, gameView.createBitmap(R.drawable.class.getField(fieldType.getName().toLowerCase()).getInt(null))));
			if(fieldType.getName().equals("Dungeon")){
				DUNGEON = fieldType.getId().intValue();
			}
			if(fieldType.getName().equals("Town")){
				TOWN = fieldType.getId().intValue();
			}			
			if(fieldType.getName().equals("Passage")){
				PASSAGE = fieldType.getId().intValue();
			}
		}
		
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
		
		objects = new HashMap<>(land.getPassages().size()+land.getDungeons().size()+(land.getHasTown()!=null && land.getHasTown().booleanValue() ? 1 : 0));
		for(Passage passage: land.getPassages()){
			objects.put(new Point(passage.getX()-cornerX, passage.getY()-cornerY), passage);
		}
		for(Dungeon dungeon: land.getDungeons()){
			objects.put(new Point(dungeon.getX()-cornerX, dungeon.getY()-cornerY), dungeon);
		}
		Town town = land.getTown();
		if(town!=null){
			objects.put(new Point(town.getX()-cornerX, town.getY()-cornerY), town);
		}
	}

	@Override
	public void render(Canvas canvas) {
		canvas.drawColor(Color.LTGRAY);
		DrawableFieldType drawableFieldType;
		for(int j=0; j<height; ++j){
			for(int i=0; i<width; ++i){
				drawableFieldType = fieldTypes.get(fields[j][i]);
				if(drawableFieldType!=null){
					canvas.drawBitmap(drawableFieldType.getBitmap(), offsetX+Constants.TILE_SIZE*i, offsetY+Constants.TILE_SIZE*j, null);
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
		DrawableFieldType drawableFieldType = fieldTypes.get(fields[y][x]);
		if(drawableFieldType == null){
			return false;
		}
		return withinMapSegment(x, y) && drawableFieldType.getFieldType().getPassable().booleanValue();
	}
	
	public boolean isDungeon(int x, int y){
		return withinMapSegment(x, y) && fields[y][x] == DUNGEON;
	}
	
	public boolean isPassage(int x, int y){
		return withinMapSegment(x, y) && fields[y][x] == PASSAGE;
	}
	
	public boolean isTown(int x, int y){
		return withinMapSegment(x, y) && fields[y][x] == TOWN;
	}
	
	private boolean withinMapSegment(int x, int y) {
		return x >= 0 && y >= 0 && x < width && y < height;
	}
	
}
