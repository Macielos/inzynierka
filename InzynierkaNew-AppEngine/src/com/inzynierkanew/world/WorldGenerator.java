package com.inzynierkanew.world;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.persistence.EntityManager;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;

import com.inzynierkanew.endpoints.map.LandEndpoint;
import com.inzynierkanew.endpoints.players.PlayerEndpoint;
import com.inzynierkanew.entities.map.Dungeon;
import com.inzynierkanew.entities.map.Land;
import com.inzynierkanew.entities.map.Passage;
import com.inzynierkanew.entities.map.Town;
import com.inzynierkanew.entities.players.Player;
import com.inzynierkanew.utils.EMF;
import com.inzynierkanew.utils.Point;
import com.inzynierkanew.utils.Statistics;
import com.inzynierkanew.utils.WorldGenerationUtils;

public class WorldGenerator {

	// CONSTANTS
	private static final double INITIAL_CONTINUE_RATE = 7.0;
	private static final double CONTINUE_RATE_DROP = 0.1;
	private static final double START_FROM_PASSAGE_BONUS = 1.0;

	private static final int LAND_MAX_HEIGHT = (int) (INITIAL_CONTINUE_RATE / CONTINUE_RATE_DROP);
	private static final int LAND_MAX_WIDTH = 2 * LAND_MAX_HEIGHT;

	private static final double CHANCE_FOR_TOWN = 1.0;
	private static final int MIN_DISTANCE_TOWN_BORDER = 3;

	private static final int MIN_PASSAGE_COUNT = 2;
	private static final double EXTRA_PASSAGES_PER_BORDER_FIELD = 0.05;
	private static final int PASSAGE_MAX_RETRIES = 500;

	private static final int TOWN_MAX_RETRIES = 100;

	private static final double CHANCE_FOR_CROSSROAD = 0.8;
	private static final int CROSSROAD_MIN_GAP = 5;
	private static final int CROSSROAD_GAP_DIFF = 3;

	// FIELD TYPES - TEMP
	private static final int EMPTY = 0;
	private static final int PASSABLE = 1;
	private static final int NON_PASSABLE = 2;
	private static final int EXISTING_LAND = 3;

	private static final int ROAD = 6;
	private static final int PASSAGE = 7;
	private static final int TOWN = 8;
	private static final int DUNGEON = 9;

	// ENDPOINTS
	private LandEndpoint landEndpoint = new LandEndpoint();
	private PlayerEndpoint playerEndpoint = new PlayerEndpoint();

	// LOG
	private Log log = LogFactory.getLog(getClass());

	// TEMP PRODUCTS
	private Land newLand;

	private Land borderLand = null;
	private Passage borderLandPassage = null;

	private int[][] mapSegment;

	private List<Point> borderPoints = new ArrayList<>();
	private List<Point> neighbourFreePassages = new ArrayList<>();

	private Set<Point> passages;
	private List<Land> neighbours;
	private Set<Point> pointsExcludedFromPlacingPassages = new HashSet<>();
	private Point town = null;

	private Set<GraphEdge> edges = new HashSet<>();
	private Set<Point> crossRoads = new HashSet<>();

	// GENERATION START POINT
	private int startX;
	private int startY;

	// EXTREME POINTS OF GENERATED LAND
	private int minX;
	private int minY;
	private int maxX;
	private int maxY;

	private Random random = new Random();

	public void generateLand() {
		log.info("Generating land...");
		// findBorderLand();
		prepareMapSegment();
		log.info(WorldGenerationUtils.mapToString(mapSegment));
		placeNeighbours();
		log.info(WorldGenerationUtils.mapToString(mapSegment));
		populateMapSegment();
		log.info(WorldGenerationUtils.mapToString(mapSegment));
		reduceMapSegment();
		log.info(WorldGenerationUtils.mapToString(mapSegment));
		fillInHoles();
		log.info(WorldGenerationUtils.mapToString(mapSegment));
		createPassages();
		log.info(WorldGenerationUtils.mapToString(mapSegment));
		createTown();
		log.info(WorldGenerationUtils.mapToString(mapSegment));
		carveRoads();
		log.info(WorldGenerationUtils.mapToString(mapSegment));
		newLand = new Land();
	}

	private boolean isBorder(int x, int y) {
		if (mapSegment[y][x] != NON_PASSABLE) {
			return false;
		}
		int segmentWidth = mapSegment[0].length;
		int segmentHeight = mapSegment.length;
		if (x == 0 || y == 0 || x == segmentWidth - 1 || y == segmentHeight - 1) {
			return true;
		}
		if (mapSegment[y - 1][x] != NON_PASSABLE) {
			return true;
		}
		if (mapSegment[y + 1][x] != NON_PASSABLE) {
			return true;
		}
		if (mapSegment[y][x + 1] != NON_PASSABLE) {
			return true;
		}
		if (mapSegment[y][x - 1] != NON_PASSABLE) {
			return true;
		}
		return false;
	}
	
	private boolean isBeyondCurrentLand(int x, int y) {
		return mapSegment[y][x] == EMPTY || mapSegment[y][x] == EXISTING_LAND;
	}

	private void findBorderLand() {

		// TODO tylko pobliskie landy (skminiæ jak to zrobiæ)
		// TODO tylko aktywni gracze posiadaj¹cy herosa z currentLandId
		List<Player> players = (List<Player>) playerEndpoint.listPlayer(null, null);

		Statistics<Long> playerPresenceInLands = new Statistics<>();

		for (Player player : players) {
			if (player.getHero() != null && player.getHero().getCurrentLandId() != null) {
				playerPresenceInLands.increment(player.getHero().getCurrentLandId());
			}
		}
		Long bestLandId = playerPresenceInLands.getMax();
		if (bestLandId == null) {
			return;
		}
		borderLand = landEndpoint.getLand(bestLandId);
		for (Passage passage : borderLand.getPassages()) {
			if (passage.getNextLandId() == null) {
				borderLandPassage = passage;
				break;
			}
		}

	}

	private void prepareMapSegment() {
		mapSegment = newMapSegment(LAND_MAX_HEIGHT, LAND_MAX_WIDTH);

		// int minX = Integer.MIN_VALUE;
		// int minY = Integer.MIN_VALUE;
		// int maxX = Integer.MAX_VALUE;
		// int maxY = Integer.MAX_VALUE;
		//
		// // TODO
		// //pamietaj o passage'ach!!!
		// for (Land land : neighbours) {
		// if(land.getX()<minX){
		// minX = land.getX();
		// }
		// if(land.getY()<minY){
		// minY = land.getY();
		// }
		// if(land.getRightBorderX()>maxX){
		// maxX = land.getRightBorderX();
		// }
		// if(land.getBottomBorderY()>maxY){
		// maxY = land.getBottomBorderY();
		// }
		// }
		//
		// if (minX == Integer.MIN_VALUE || minY == Integer.MIN_VALUE || maxX ==
		// Integer.MAX_VALUE || maxY == Integer.MAX_VALUE) {
		// mapSegment = newMapSegment(LAND_MAX_HEIGHT, LAND_MAX_WIDTH);
		// return;
		// }
	}

	private void populateMapSegment() {
		startX = mapSegment[0].length / 2; // borderLandPassage == null ?
											// mapSegment[0].length / 2 :
											// borderLandPassage.getX();
		startY = mapSegment.length / 2; // borderLandPassage == null ?
										// mapSegment.length / 2 :
										// borderLandPassage.getY();
		minX = maxX = startX;
		minY = maxY = startY;
		double initialContinueRate = INITIAL_CONTINUE_RATE;
		if (borderLandPassage != null) {
			initialContinueRate += START_FROM_PASSAGE_BONUS;
		}
		populateMapSegmentInternal(startX, startY, initialContinueRate);
	}

	private void populateMapSegmentInternal(int x, int y, double minResultToContinue) {
		if (minResultToContinue <= 0 || x < 0 || y < 0 || x >= mapSegment[0].length || y >= mapSegment.length || mapSegment[y][x] != 0) {
			return;
		}
		double nextField = random.nextDouble();
		if (nextField > minResultToContinue) {
			return;
		}
		mapSegment[y][x] = NON_PASSABLE;
		if (x > maxX) {
			maxX = x;
		}
		if (x < minX) {
			minX = x;
		}
		if (y > maxY) {
			maxY = y;
		}
		if (y < minY) {
			minY = y;
		}

		// log.info(WorldGenerationUtils.mapToString(mapSegment));
		populateMapSegmentInternal(x + 1, y, minResultToContinue - CONTINUE_RATE_DROP);
		populateMapSegmentInternal(x, y + 1, minResultToContinue - CONTINUE_RATE_DROP);
		populateMapSegmentInternal(x - 1, y, minResultToContinue - CONTINUE_RATE_DROP);
		populateMapSegmentInternal(x, y - 1, minResultToContinue - CONTINUE_RATE_DROP);
	}

	private void reduceMapSegment() {
		int width = maxX - minX + 1;
		int height = maxY - minY + 1;
		int[][] reducedMapSegment = newMapSegment(height, width);
		for (int i = 0; i < width; ++i) {
			for (int j = 0; j < height; ++j) {
				reducedMapSegment[j][i] = mapSegment[j + minY][i + minX];
				if (isBorder(i + minX, j + minY)) {
					borderPoints.add(new Point(i, j));
				}
			}
		}
		mapSegment = reducedMapSegment;
	}

	private int[][] newMapSegment(int height, int width) {
		int[][] mapSegment = new int[height][];
		for (int i = 0; i < height; ++i) {
			mapSegment[i] = new int[width];
		}
		return mapSegment;
	}

	private void fillInHoles() {
		for (int i = 0; i < mapSegment[0].length; ++i) {
			for (int j = 0; j < mapSegment.length; ++j) {
				if (isHole(i, j)) {
					mapSegment[j][i] = NON_PASSABLE;
				}
			}
		}
		for (int i = 0; i < mapSegment[0].length; ++i) {
			for (int j = 0; j < mapSegment.length; ++j) {
				if (isPinnacle(i, j)) {
					mapSegment[j][i] = EMPTY;
				}
			}
		}
	}

	private boolean isHole(int x, int y) {
		return mapSegment[y][x] == EMPTY && getNeighbourCountOfType(x, y, NON_PASSABLE) > 2;
	}

	private boolean isPinnacle(int x, int y) {
		return mapSegment[y][x] == NON_PASSABLE && getNeighbourCountOfType(x, y, NON_PASSABLE) < 2;
	}

	private int getNeighbourCountOfType(int x, int y, int type) {
		int neighbourLandFields = 0;
		for (Point point : new Point[] { new Point(x + 1, y), new Point(x - 1, y), new Point(x, y + 1), new Point(x, y - 1) }) {
			if (withinMapSegment(point.x, point.y) && mapSegment[point.y][point.x] == type) {
				++neighbourLandFields;
			}
		}
		return neighbourLandFields;
	}

	private boolean isSuitableForPassage(int x, int y) {
		return getNeighbourCountOfType(x, y, NON_PASSABLE) == 3;
	}

	private boolean hasType(Point point, int type) {
		return withinMapSegment(point.x, point.y) && mapSegment[point.y][point.x] == type;
	}

	private boolean xor(boolean a, boolean b) {
		return a && !b || !a && b;
	}

	private void placeNeighbours() {
		int mapSegmentCornerX;
		int mapSegmentCornerY;

		if (borderLand == null) {
			neighbours = new ArrayList<>(0);
			mapSegmentCornerX = 0;
			mapSegmentCornerY = 0;
		} else {
			neighbours = (List<Land>) landEndpoint.findNeighbours(borderLand.getId());
			mapSegmentCornerX = borderLandPassage.getX() - LAND_MAX_WIDTH / 2;
			mapSegmentCornerY = borderLandPassage.getY() - LAND_MAX_HEIGHT / 2;
		}

		int landCornerX;
		int landCornerY;

		// int x;
		// int y;
		for (Land land : neighbours) {
			// wsp. lewego górnego rogu tablicy istniej¹cego s¹siedniego landu
			// na tymczasowej tablicy
			landCornerX = land.getX() - mapSegmentCornerX;
			landCornerY = land.getY() - mapSegmentCornerY;

			int field;
			for (int i = 0; i < land.getWidth(); ++i) {
				for (int j = 0; j < land.getHeight(); ++j) {
					if (withinMapSegment(landCornerX + i, landCornerY + j)) {
						field = (int) land.getFields()[j * land.getWidth() + i];
						if (field != EMPTY) {
							mapSegment[landCornerY + j][landCornerX + i] = EXISTING_LAND;
							if (field == PASSAGE) {
								neighbourFreePassages.add(new Point(landCornerX + i, landCornerY + j));
							}
						}
					}
				}
			}
		}
	}

	private boolean withinMapSegment(int x, int y) {
		return x >= 0 && y >= 0 && x < mapSegment[0].length && y < mapSegment.length;
	}

	private void createPassages() {
		int passageCount = MIN_PASSAGE_COUNT + (int) Math.ceil(EXTRA_PASSAGES_PER_BORDER_FIELD * ((double) borderPoints.size()));
		passages = new HashSet<>(passageCount - 1);

		Point gateToCreatedLand = null;
		for (Point passage : neighbourFreePassages) {
			gateToCreatedLand = getNeighbourFieldOfType(passage.x, passage.y, NON_PASSABLE);
			if (gateToCreatedLand != null) {
				passages.add(gateToCreatedLand);
				mapSegment[gateToCreatedLand.y][gateToCreatedLand.x] = PASSAGE;
			}
		}

		int nextPassageIndex;
		Point candidateForPassage = null;
		int retries = 0;
		while (passages.size() < passageCount && retries <= PASSAGE_MAX_RETRIES) {
			nextPassageIndex = random.nextInt(borderPoints.size());
			candidateForPassage = borderPoints.get(nextPassageIndex);
			if (getNeighbourFieldOfTypeCrossingIncluded(candidateForPassage.x, candidateForPassage.y, EXISTING_LAND) == null
					&& getNeighbourFieldOfTypeCrossingIncluded(candidateForPassage.x, candidateForPassage.y, PASSAGE) == null
					&& isSuitableForPassage(candidateForPassage.x, candidateForPassage.y)
					&& !pointsExcludedFromPlacingPassages.contains(candidateForPassage)) {
				passages.add(candidateForPassage);
				for (Point point : borderPoints) {
					if (inRange(point, candidateForPassage, 3)) {
						pointsExcludedFromPlacingPassages.add(point);
					}
				}
				mapSegment[candidateForPassage.y][candidateForPassage.x] = PASSAGE;
			} else {
				++retries;
			}
		}
	}

	private boolean inRange(Point point1, Point point2, int maxRange) {
		return inRange(point1.x, point1.y, point2.x, point2.y, maxRange);
	}

	private boolean inRange(int x1, int y1, int x2, int y2, int maxRange) {
		return Math.abs(x1 - x2) + Math.abs(y1 - y2) <= maxRange;
	}

	private Point getNeighbourFieldOfType(int x, int y, int fieldType) {
		for (Point point : new Point[] { new Point(x + 1, y), new Point(x - 1, y), new Point(x, y + 1), new Point(x, y - 1) }) {
			if (withinMapSegment(point.x, point.y) && mapSegment[point.y][point.x] == fieldType) {
				return point;
			}
		}
		return null;
	}

	private Point getNeighbourFieldOfTypeCrossingIncluded(int x, int y, int fieldType) {
		for (Point point : new Point[] { new Point(x + 1, y), new Point(x - 1, y), new Point(x, y + 1), new Point(x, y - 1),
				new Point(x + 1, y + 1), new Point(x - 1, y + 1), new Point(x + 1, y - 1), new Point(x - 1, y - 1) }) {
			if (withinMapSegment(point.x, point.y) && mapSegment[point.y][point.x] == fieldType) {
				return point;
			}
		}
		return null;
	}

	private void createTown() {
		double createTown = random.nextDouble();
		// TODO uzaleznic szanse na miasto od liczby miast w sasiednich landach
		if (createTown <= CHANCE_FOR_TOWN) {
			int x;
			int y;
			for (int i = 0; i < TOWN_MAX_RETRIES; ++i) {
				x = random.nextInt(mapSegment[0].length);
				y = random.nextInt(mapSegment.length);
				if (mapSegment[y][x] == NON_PASSABLE && !isBorder(x, y) && getNeighbourFieldOfTypeCrossingIncluded(x, y, PASSAGE) == null) {
					mapSegment[y][x] = TOWN;
					town = new Point(x, y);
					break;
				}
			}
		}
	}

	private void carveRoads() {
		selectCrossRoads();
		//log.info(WorldGenerationUtils.mapToString(mapSegment));
		connectCrossRoads();
		fillInRoadsOnMap();

		/*
		 * if(town != null){ for(Point passage: passages){ carveRoad(town,
		 * passage); } } else { Point[] passageArray = passages.toArray(new
		 * Point[passages.size()]); Point startPassage =
		 * passageArray[random.nextInt(passages.size())]; for(Point passage:
		 * passages){ if(passage!=startPassage){ carveRoad(startPassage,
		 * passage); } } }
		 */
	}

	private void selectCrossRoads() {
		Point point;
//		for (int i = 0; i < mapSegment[0].length; i = i + CROSSROAD_MIN_GAP + random.nextInt(CROSSROAD_GAP_DIFF)) {
//			for (int j = 0; j < mapSegment.length; j = j + CROSSROAD_MIN_GAP + random.nextInt(CROSSROAD_GAP_DIFF)) {
//				if (mapSegment[j][i] == NON_PASSABLE) {
//					point = new Point(i, j);
//					if (!borderPoints.contains(point)) {
//						crossRoads.add(new Point(i, j));
//						// temp
//						mapSegment[j][i] = 9;
//					}
//				}
//			}
//		}

		int x; 
		int y;
		for (int i = 0; i < mapSegment[0].length; i = i + CROSSROAD_MIN_GAP) {
			for (int j = 0; j < mapSegment.length; j = j + CROSSROAD_MIN_GAP) {
				x = i + random.nextInt(CROSSROAD_GAP_DIFF);
				y = j + random.nextInt(CROSSROAD_GAP_DIFF);	
				if (isFieldValid(x, y)) {
					point = new Point(x, y);
					if (!borderPoints.contains(point)) {
						crossRoads.add(point);
						// temp
						mapSegment[y][x] = 9;
					}
				}
			}
		}
		
		
		if (town != null) {
			crossRoads.add(town);
		}
		crossRoads.addAll(passages);
	}

	private void connectCrossRoads() {
		int i = CROSSROAD_MIN_GAP;
		GraphBuilder graphBuilder = new GraphBuilder(crossRoads);
		while (connectCrossRoadsInternal(graphBuilder, i++));
		if(i > mapSegment.length){
			log.info("Connecting roads did not stop!!!");
			log.info(WorldGenerationUtils.mapToString(mapSegment));
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		edges = graphBuilder.getEdgesIfConnected();
	}

	private boolean connectCrossRoadsInternal(GraphBuilder graphBuilder, int maxDistance) {
		Point[] crossRoadArray = crossRoads.toArray(new Point[crossRoads.size()]);
		GraphEdge edge;
		log.info("connectCrossRoadsInternal - iteration for max distance = " + maxDistance);
		for (int i = 0; i < crossRoadArray.length; ++i) {
			for (int j = i + 1; j < crossRoadArray.length; ++j) {
				if (inRange(crossRoadArray[i], crossRoadArray[j], maxDistance)
						&& (!borderPoints.contains(crossRoadArray[i]) || passages.contains(crossRoadArray[j]))) {
					graphBuilder.addConnection(crossRoadArray[i], crossRoadArray[j]);
				}
			}
		}
		return !graphBuilder.isConnected();
	}

	private void fillInRoadsOnMap() {
		printGraph();
		log.info("Crossroads:");
		for (Point point: crossRoads){
			log.info(point);
		}
		for (GraphEdge edge : edges) {
			log.info(edge);
			carveRoad(edge);
			//log.info(WorldGenerationUtils.mapToString(mapSegment, true));
		}
		log.info(WorldGenerationUtils.mapToString(mapSegment, true));
		
	}

	private void carveRoad(GraphEdge edge) {
		int x = edge.point1.x;
		int y = edge.point1.y;
		int finishX = edge.point2.x;
		int finishY = edge.point2.y;
		int diffX;
		int diffY;
		boolean moving;
		log.info("Carving road from "+edge.point1+" to "+edge.point2);
		while (x != finishX || y != finishY && withinMapSegment(x, y)) {
			if (mapSegment[y][x] == EMPTY) {
				log.warn("Road has gone to empty space! Point is: (" + x + ", " + y + ")");
				//break;
			}
			if (mapSegment[y][x] == EXISTING_LAND) {
				log.warn("Road has gone to an existing land! Point is: (" + x + ", " + y + ")");
				//break;
			}
			if (mapSegment[y][x] == ROAD) {
				//break;
				//log.warn("Road has gone to an existing road! Point is: (" + x + ", " + y + ")");
			}
			if(mapSegment[y][x]!=DUNGEON && mapSegment[y][x]!=TOWN && mapSegment[y][x]!=PASSAGE){
				mapSegment[y][x] = ROAD;
			}
			diffX = finishX - x;
			diffY = finishY - y;
			moving = false;
			if(Math.abs(diffX) > Math.abs(diffY)){
				if (x < finishX && isFieldValid(x+1, y)) {
					++x;
					moving = true;
				} else if (x > finishX && isFieldValid(x-1, y)) {
					--x;
					moving = true;
				} else if (y < finishY && isFieldValid(x, y+1)) {
					++y;
					moving = true;
				} else if (y > finishY && isFieldValid(x, y-1)) {
					--y;
					moving = true;
				}
			} else {
				if (y < finishY && isFieldValid(x, y+1)) {
					++y;
					moving = true;
				} else if (y > finishY && isFieldValid(x, y-1)) {
					--y;
					moving = true;
				} else if (x < finishX && isFieldValid(x+1, y)) {
					++x;
					moving = true;
				} else if (x > finishX && isFieldValid(x-1, y)) {
					--x;
					moving = true;
				}
			}
			if(!moving){
				log.warn("Cannot move! Interrupted! Point is: (" + x + ", " + y + ")");
				break;
			}
			if (!withinMapSegment(x, y)) {
				log.warn("Road has gone beyond map borders! Point is: (" + x + ", " + y + ")");
			}
		}
		log.info(WorldGenerationUtils.mapToString(mapSegment));
	}
	
	private boolean isFieldValid(int x, int y){
		return withinMapSegment(x, y) && mapSegment[y][x]!=EXISTING_LAND && mapSegment[y][x]!=EMPTY;
	}

	private void printGraph() {
		StringBuilder sb = new StringBuilder();
		for (GraphEdge edge : edges) {
			sb.append(edge + "\n");
		}
		log.info(sb.toString());
	}

	private void generateAndPersistLand() {
		Land land = generatekLand();
		EntityManager entityManager = EMF.get().createEntityManager();
		try {
			entityManager.persist(land);
		} finally {
			entityManager.close();
		}
	}

	private Land generatekLand() {
		Land land = new Land();
		land.setTown(new Town(2, 1, 1, "Aringrad"));
		land.setPassages(Arrays.asList(new Passage(3, 1, 1, null)));
		land.setDungeons(Arrays.asList(new Dungeon(3, 0, 1)));
		land.setFields(new long[] { 3, 2, 1 });
		return land;
	}

}
