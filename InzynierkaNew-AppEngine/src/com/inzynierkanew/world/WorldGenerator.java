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

	//CONSTANTS
	private static final double INITIAL_CONTINUE_RATE = 1.3;
	private static final double CONTINUE_RATE_DROP = 0.1;
	private static final double START_FROM_PASSAGE_BONUS = 1.0;
	
	private static final int LAND_MAX_HEIGHT = (int) (INITIAL_CONTINUE_RATE / CONTINUE_RATE_DROP);
	private static final int LAND_MAX_WIDTH = 2 * LAND_MAX_HEIGHT;

	private static final double CHANCE_FOR_TOWN = 1.0;
	private static final int MIN_DISTANCE_TOWN_BORDER = 3;
	
	private static final int MIN_PASSAGE_COUNT = 2;
	private static final double EXTRA_PASSAGES_PER_BORDER_FIELD = 0.05;
	private static final int PASSAGE_MAX_RETRIES = 500;

	//FIELD TYPES - TEMP
	private static final int EMPTY = 0;
	private static final int PASSABLE = 1;
	private static final int NON_PASSABLE = 2;
	private static final int EXISTING_LAND = 3;

	private static final int ROAD = 6;
	private static final int PASSAGE = 7;
	private static final int TOWN = 8;
	private static final int DUNGEON = 9;

	//ENDPOINTS
	private LandEndpoint landEndpoint = new LandEndpoint();
	private PlayerEndpoint playerEndpoint = new PlayerEndpoint();

	//LOG
	private Log log = LogFactory.getLog(getClass());

	//TEMP PRODUCTS
	private Land newLand;
	
	private Land borderLand = null;
	private Passage borderLandPassage = null;
	private int[][] mapSegment;
	
	//GENERATION START POINT
	private int startX; 
	private int startY; 
	
	//EXTREME POINTS OF GENERATED LAND
	private int minX;
	private int minY;
	private int maxX;
	private int maxY;
	
	private List<Point> borderPoints = new ArrayList<>();
	private List<Point> neighbourFreePassages = new ArrayList<>();
	
	private Set<Point> passages;
	private List<Land> neighbours;
	
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
		newLand = new Land();
	}
	
	private boolean isBorder(int x, int y){
		if(mapSegment[y][x]!=PASSABLE){
			return false;
		}
		int segmentWidth = mapSegment[0].length;
		int segmentHeight = mapSegment.length;
		if(x == 0 || y == 0 || x == segmentWidth-1 || y == segmentHeight-1){
			return true;
		}
		if(mapSegment[y-1][x]!=PASSABLE){
			return true;
		}
		if(mapSegment[y+1][x]!=PASSABLE){
			return true;
		}
		if(mapSegment[y][x+1]!=PASSABLE){
			return true;
		}
		if(mapSegment[y][x-1]!=PASSABLE){
			return true;
		}
		return false;
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
		if(bestLandId == null){
			return;
		}
		borderLand = landEndpoint.getLand(bestLandId);
		for(Passage passage: borderLand.getPassages()){
			if(passage.getNextLandId()==null){
				borderLandPassage = passage;
				break;
			}
		}
		
	}

	private void prepareMapSegment() {
		mapSegment = newMapSegment(LAND_MAX_HEIGHT, LAND_MAX_WIDTH);
		
		
		
//		int minX = Integer.MIN_VALUE;
//		int minY = Integer.MIN_VALUE;
//		int maxX = Integer.MAX_VALUE;
//		int maxY = Integer.MAX_VALUE;
//		
//		// TODO
//		//pamietaj o passage'ach!!!
//		for (Land land : neighbours) {
//			if(land.getX()<minX){
//				minX = land.getX();
//			}
//			if(land.getY()<minY){
//				minY = land.getY();
//			}
//			if(land.getRightBorderX()>maxX){
//				maxX = land.getRightBorderX();
//			}
//			if(land.getBottomBorderY()>maxY){
//				maxY = land.getBottomBorderY();
//			}
//		}
//
//		if (minX == Integer.MIN_VALUE || minY == Integer.MIN_VALUE || maxX == Integer.MAX_VALUE || maxY == Integer.MAX_VALUE) {
//			mapSegment = newMapSegment(LAND_MAX_HEIGHT, LAND_MAX_WIDTH);
//			return;
//		}
	}

	private void populateMapSegment() {
		startX = mapSegment[0].length / 2;  //borderLandPassage == null ? mapSegment[0].length / 2 : borderLandPassage.getX();
		startY = mapSegment.length / 2; 	//borderLandPassage == null ? mapSegment.length / 2 : borderLandPassage.getY();
		minX = maxX = startX;
		minY = maxY = startY;
		double initialContinueRate = INITIAL_CONTINUE_RATE;
		if(borderLandPassage != null){
			initialContinueRate+=START_FROM_PASSAGE_BONUS;
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
		mapSegment[y][x] = PASSABLE;
		if(x > maxX){
			maxX = x;
		} 
		if(x < minX){
			minX = x;
		}
		if(y > maxY){
			maxY = y;
		} 
		if(y < minY){
			minY = y;
		}
		
		//log.info(WorldGenerationUtils.mapToString(mapSegment));
		populateMapSegmentInternal(x + 1, y, minResultToContinue - CONTINUE_RATE_DROP);
		populateMapSegmentInternal(x, y + 1, minResultToContinue - CONTINUE_RATE_DROP);
		populateMapSegmentInternal(x - 1, y, minResultToContinue - CONTINUE_RATE_DROP);
		populateMapSegmentInternal(x, y - 1, minResultToContinue - CONTINUE_RATE_DROP);
	}

	private void reduceMapSegment() {
		int width = maxX-minX+1;
		int height = maxY-minY+1;
		int[][] reducedMapSegment = newMapSegment(height, width);
		for(int i=0; i<width; ++i){
			for(int j=0; j<height; ++j){
				reducedMapSegment[j][i] = mapSegment[j+minY][i+minX];
				if(isBorder(i+minX, j+minY)){
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
	
	private void fillInHoles(){
		for(int i=0; i<mapSegment[0].length; ++i){
			for(int j=0; j<mapSegment.length; ++j){
				if(isHole(i, j)){
					mapSegment[j][i] = PASSABLE;
				}
			}
		}
		for(int i=0; i<mapSegment[0].length; ++i){
			for(int j=0; j<mapSegment.length; ++j){
				if(isPinnacle(i, j)){
					mapSegment[j][i] = EMPTY;
				}
			}
		}
	}

	private boolean isHole(int x, int y) {
		return mapSegment[y][x]==EMPTY && getNeighbourCountOfType(x, y, PASSABLE) > 2;
	}
	
	private boolean isPinnacle(int x, int y) {
		return mapSegment[y][x]==PASSABLE && getNeighbourCountOfType(x, y, PASSABLE) < 2;
	}
	
	private int getNeighbourCountOfType(int x, int y, int type){
		int neighbourLandFields = 0;
		for(Point point: new Point[]{
				new Point(x+1, y), 
				new Point(x-1, y),
				new Point(x, y+1),
				new Point(x, y-1)
		}){
			if(withinMapSegment(point.x, point.y) && mapSegment[point.y][point.x]==type){
				++neighbourLandFields;
			}
		}
		return neighbourLandFields;
	}
	
	private boolean isSuitableForPassage(int x, int y) {
		return getNeighbourCountOfType(x, y, PASSABLE)==3;
//		Point right = new Point(x+1, y);
//		Point left = new Point(x-1, y);
//		Point up = new Point(x, y+1);
//		Point down = new Point(x, y-1);
//		
//		return xor(hasType(left, PASSABLE) && hasType(right, PASSABLE), hasType(up, PASSABLE) && hasType(down, PASSABLE));
	}

	private boolean hasType(Point point, int type){
		return withinMapSegment(point.x, point.y) && mapSegment[point.y][point.x]==type;
	}
	
	private boolean xor(boolean a, boolean b){
		return a && !b || !a && b;
	}

	private void placeNeighbours() {
		int mapSegmentCornerX;
		int mapSegmentCornerY;

		if(borderLand == null){
			neighbours = new ArrayList<>(0);
			mapSegmentCornerX = 0;
			mapSegmentCornerY = 0;
		} else {
			neighbours = (List<Land>) landEndpoint.findNeighbours(borderLand.getId());
			mapSegmentCornerX = borderLandPassage.getX()-LAND_MAX_WIDTH/2;
			mapSegmentCornerY = borderLandPassage.getY()-LAND_MAX_HEIGHT/2;
		}
			
		int landCornerX;
		int landCornerY;
		
		//int x;
		//int y;
		for(Land land: neighbours){
			//wsp. lewego górnego rogu tablicy istniej¹cego s¹siedniego landu na tymczasowej tablicy
			landCornerX = land.getX() - mapSegmentCornerX;
			landCornerY = land.getY() - mapSegmentCornerY;
			
			int field;
			for(int i=0; i<land.getWidth(); ++i){
				for(int j=0; j<land.getHeight(); ++j){
					if(withinMapSegment(landCornerX+i, landCornerY+j)){
						field = (int) land.getFields()[j*land.getWidth()+i];
						if(field != EMPTY){
							mapSegment[landCornerY+j][landCornerX+i] = EXISTING_LAND;
							if(field == PASSAGE){
								neighbourFreePassages.add(new Point(landCornerX+i, landCornerY+j));
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
		int passageCount = MIN_PASSAGE_COUNT + (int) Math.ceil(EXTRA_PASSAGES_PER_BORDER_FIELD*((double)borderPoints.size()));
		passages = new HashSet<>(passageCount-1);
		
		Point gateToCreatedLand = null;
		for(Point passage: neighbourFreePassages){
			gateToCreatedLand = getNeighbourFieldOfType(passage.x, passage.y, PASSABLE);
			if(gateToCreatedLand != null){
				passages.add(gateToCreatedLand);
				mapSegment[gateToCreatedLand.y][gateToCreatedLand.x] = PASSAGE;
			}
		}
				
		int nextPassageIndex;
		Point candidateForPassage = null;
		int retries = 0;
		while(passages.size() < passageCount && retries <= PASSAGE_MAX_RETRIES){
			nextPassageIndex = random.nextInt(borderPoints.size());
			candidateForPassage = borderPoints.get(nextPassageIndex);
			if(getNeighbourFieldOfTypeCrossingIncluded(candidateForPassage.x, candidateForPassage.y, EXISTING_LAND)==null 
					&& getNeighbourFieldOfTypeCrossingIncluded(candidateForPassage.x, candidateForPassage.y, PASSAGE)==null 
					&& isSuitableForPassage(candidateForPassage.x, candidateForPassage.y)){
				passages.add(candidateForPassage);
				mapSegment[candidateForPassage.y][candidateForPassage.x] = PASSAGE;
			} else {
				++retries;
			}
		}
	}
	
	private Point getNeighbourFieldOfType(int x, int y, int fieldType) {
		for(Point point: new Point[]{
				new Point(x+1, y), 
				new Point(x-1, y),
				new Point(x, y+1),
				new Point(x, y-1)
		}){
			if(withinMapSegment(point.x, point.y) && mapSegment[point.y][point.x]==fieldType){
				return point;
			}
		}
		return null;
	}
	
	private Point getNeighbourFieldOfTypeCrossingIncluded(int x, int y, int fieldType) {
		for(Point point: new Point[]{
				new Point(x+1, y), 
				new Point(x-1, y),
				new Point(x, y+1),
				new Point(x, y-1),
				new Point(x+1, y+1), 
				new Point(x-1, y+1),
				new Point(x+1, y-1),
				new Point(x-1, y-1)
		}){
			if(withinMapSegment(point.x, point.y) && mapSegment[point.y][point.x]==fieldType){
				return point;
			}
		}
		return null;
	}

	private void createTown() {
		
	}

	private void carveRoads() {
		// TODO Auto-generated method stub

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
