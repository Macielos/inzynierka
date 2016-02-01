package com.explorersguild.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Set;

import com.explorersguild.entities.map.dungeonendpoint.model.Dungeon;
import com.explorersguild.entities.map.landendpoint.model.Land;
import com.explorersguild.entities.map.passageendpoint.model.Passage;
import com.explorersguild.entities.map.townendpoint.model.Town;
import com.explorersguild.game.GameView;
import com.explorersguild.shared.SharedConstants;
import com.explorersguild.utils.Point;
import com.google.api.client.json.GenericJson;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;

public class LandModel implements IRenderable {

	private static final String TAG = LandModel.class.getSimpleName();
	
	private final int[][] fields;

	private final int height;
	private final int width;
	
	private final Map<Integer, DrawableFieldType> fieldTypes;

	private final Map<Point, Passage> passages;
	private final Map<Point, Dungeon> dungeons;
	private final Point townPoint;
	private final Town town;
	
	private final int cornerX;
	private final int cornerY;
	
	private final Bitmap townBitmap;
	private final Bitmap dungeonBitmap;
	private final Bitmap passageHorizontalBitmap;
	private final Bitmap passageVerticalBitmap;
	private final Bitmap portalBitmap;
	private final Bitmap activePortalBitmap;
	
	private final GameView gameView;

	public LandModel(GameView gameView, Land land, Town town, List<Passage> passages, List<Dungeon> dungeons, Map<Integer, DrawableFieldType> fieldTypes) throws IllegalAccessException, IllegalArgumentException, NoSuchFieldException {
		this.gameView = gameView;
		this.town = town;
		this.height = land.getHeight();
		this.width = land.getWidth();
		this.fieldTypes = fieldTypes;
		
		townBitmap = fieldTypes.get(SharedConstants.TOWN_ID.intValue()).getBitmap();
		dungeonBitmap = fieldTypes.get(SharedConstants.DUNGEON_ID.intValue()).getBitmap();
		passageHorizontalBitmap = fieldTypes.get(SharedConstants.PASSAGE_HORIZONTAL_ID.intValue()).getBitmap();
		passageVerticalBitmap = fieldTypes.get(SharedConstants.PASSAGE_VERTICAL_ID.intValue()).getBitmap();
		portalBitmap = fieldTypes.get(SharedConstants.PORTAL_ID.intValue()).getBitmap();
		activePortalBitmap = fieldTypes.get(SharedConstants.ACTIVE_PORTAL_ID.intValue()).getBitmap();
		
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
		
		this.passages = new HashMap<>(passages.size());
		for(Passage passage: passages){
			Point point = new Point(passage.getX()-cornerX, passage.getY()-cornerY);
			this.passages.put(point, passage);
		}
		this.dungeons = new HashMap<>(dungeons.size());
		for(Dungeon dungeon: dungeons){
			Point point = new Point(dungeon.getX()-cornerX, dungeon.getY()-cornerY);
			this.dungeons.put(point, dungeon);
		}
		if(town!=null){
			townPoint = new Point(town.getX()-cornerX, town.getY()-cornerY);
		} else {
			townPoint = null;
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
					canvas.drawBitmap(drawableFieldType.getBitmap(), gameView.getOffsetX()+gameView.getTileSize()*i, gameView.getOffsetY()+gameView.getTileSize()*j, null);
				}
			}
		}
		for(Entry<Point, Passage> passage: passages.entrySet()){
			canvas.drawBitmap(passageBitmap(passage.getValue()), gameView.getOffsetX()+gameView.getTileSize()*passage.getKey().x, gameView.getOffsetY()+gameView.getTileSize()*passage.getKey().y, null);
		}
		for(Point dungeon: dungeons.keySet()){
			canvas.drawBitmap(dungeonBitmap, gameView.getOffsetX()+gameView.getTileSize()*dungeon.x, gameView.getOffsetY()+gameView.getTileSize()*dungeon.y, null);
		}
		if(townPoint!=null){
			canvas.drawBitmap(townBitmap, gameView.getOffsetX()+gameView.getTileSize()*townPoint.x, gameView.getOffsetY()+gameView.getTileSize()*townPoint.y, null);
		}
	}
	
	private Bitmap passageBitmap(Passage passage){
		if(passage.getPortal().booleanValue()){
			return passage.getNextLandId()==null ? portalBitmap : activePortalBitmap;
		}
		if(passage.getDirection() == SharedConstants.LEFT || passage.getDirection() == SharedConstants.RIGHT){
			return passageVerticalBitmap;
		}
		return passageHorizontalBitmap;
		
	}

	@Override
	public void update() {
		
	}
	
	public Queue<Point> findPath(Point start, Point destination){
		Log.i(TAG, "Finding path from " + start + " to " + destination + " using recursive algorithm");
		//Queue<Point> path = new ArrayBlockingQueue();
		ArrayList<Point> path = new ArrayList<>();
		boolean found = findPathInternal(destination, start, null, new HashSet<Point>(), path);
		Queue<Point> finalPath = null;
		if(found){
			path.add(destination);
			finalPath = shortenAndEnqueue(path);
		}
		Log.i(TAG, path.toString());
		if (!found) {
			Log.w(TAG, "Path " + start + "->"+destination+" not found!");
			return null;
		}
		return finalPath;
	}
	
	private Queue<Point> shortenAndEnqueue(ArrayList<Point> path) {
		int toCutStart = -1;
		int toCutEnd = -1;
		int largestToCutSize = -1;
		for(int i=0; i<path.size()-1; ++i){
			for(int j=path.size()-1; j>=i+3; --j){
				if(areNeighbours(path.get(i), path.get(j)) && (toCutEnd - toCutStart > largestToCutSize)){
					toCutStart = i;
					toCutEnd = j;
					largestToCutSize = toCutEnd - toCutStart;
					break;
				}
			}
		}
		if(largestToCutSize == -1){
			return new LinkedList<>(path);
		}
		Queue<Point> finalPath = new LinkedList<>();
		for(int i=0; i<=toCutStart; ++i){
			finalPath.add(path.get(i));
		}
		for(int i=toCutEnd; i<path.size(); ++i){
			finalPath.add(path.get(i));
		}
		return finalPath;
	}
	
	
	
	private boolean areNeighbours(Point a, Point b){
		return Math.abs(a.x - b.x) + Math.abs(a.y - b.y) == 1;
	}

	private boolean findPathInternal(Point current, Point destination, Point previous, Set<Point> visitedPoints, ArrayList<Point> path) {
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
	
	/**
	 * get town, passage or dungeon lying at this coordinates
	 */
	public GenericJson getObject(Point point){
		if(townPoint.equals(point)){
			return town;
		}
		GenericJson passage = passages.get(point);
		if(passage!=null){
			return passage;
		}
		return dungeons.get(point);
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
