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

	private static final double INITIAL_CONTINUE_RATE = 1.2;
	private static final double CONTINUE_RATE_DROP = 0.1;
	
	private static final int LAND_MAX_HEIGHT = (int) (INITIAL_CONTINUE_RATE / CONTINUE_RATE_DROP);
	private static final int LAND_MAX_WIDTH = 2 * LAND_MAX_HEIGHT;

	private static final double CHANCE_FOR_TOWN = 1.0;
	private static final int MIN_DISTANCE_TOWN_BORDER = 3;
	
	private static final int MIN_PASSAGE_COUNT = 2;
	private static final double EXTRA_PASSAGES_PER_BORDER_FIELD = 0.1;

	private static final int GRASS = 1;
	private static final int NON_PASSABLE = 2;
	private static final int EXISTING_LAND = 3;

	private static final int ROAD = 6;
	private static final int PASSAGE = 7;
	private static final int TOWN = 8;
	private static final int DUNGEON = 9;

	private LandEndpoint landEndpoint = new LandEndpoint();
	private PlayerEndpoint playerEndpoint = new PlayerEndpoint();

	private Log log = LogFactory.getLog(getClass());

	private Land newLand;

	private Land borderLand;
	private Passage borderLandPassage;
	private int[][] mapSegment;
	
	private int minX;
	private int minY;
	private int maxX;
	private int maxY;
	
	private List<Point> borderPoints = new ArrayList<>();
	private List<Point> neighbourLandPassages = new ArrayList<>();
	private Set<Point> passages;
	
	private Random random = new Random();

	public void generateLand() {
		log.info("Generating land...");
		// findBorderLand();
		prepareMapSegment();
		log.info(WorldGenerationUtils.mapToString(mapSegment));
		populateMapSegment();
		log.info(WorldGenerationUtils.mapToString(mapSegment));
		reduceMapSegment();
		log.info(WorldGenerationUtils.mapToString(mapSegment));
		createPassages();
		log.info(WorldGenerationUtils.mapToString(mapSegment));
		createTown();
		log.info(WorldGenerationUtils.mapToString(mapSegment));
		carveRoads();
		newLand = new Land();
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
	
	private boolean isBorder(int x, int y){
		if(mapSegment[y][x]!=GRASS){
			return false;
		}
		int segmentWidth = mapSegment[0].length;
		int segmentHeight = mapSegment.length;
		if(x == 0 || y == 0 || x == segmentWidth-1 || y == segmentHeight-1){
			return true;
		}
		if(mapSegment[y-1][x]!=GRASS){
			return true;
		}
		if(mapSegment[y+1][x]!=GRASS){
			return true;
		}
		if(mapSegment[y][x+1]!=GRASS){
			return true;
		}
		if(mapSegment[y][x-1]!=GRASS){
			return true;
		}
		return false;
	}

	private void createPassages() {
		int passageCount = MIN_PASSAGE_COUNT + (int) Math.ceil(EXTRA_PASSAGES_PER_BORDER_FIELD*((double)borderPoints.size()));
		passages = new HashSet<>(passageCount-1);
		int nextPassageIndex;
		while(passages.size() < passageCount){
			nextPassageIndex = random.nextInt(borderPoints.size());
			passages.add(new Point(borderPoints.get(nextPassageIndex)));
		}
	}
	
	private void createTown() {

	}

	private void carveRoads() {
		// TODO Auto-generated method stub

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
		borderLand = bestLandId == null ? null : landEndpoint.getLand(bestLandId);
	}

	private void prepareMapSegment() {
		if (borderLand == null) {
			mapSegment = newMapSegment(LAND_MAX_HEIGHT, LAND_MAX_WIDTH);
			return;
		}
		List<Land> neighbours = (List<Land>) landEndpoint.findNeighbours(borderLand.getId());
		int minX = Integer.MIN_VALUE;
		int minY = Integer.MIN_VALUE;
		int maxX = Integer.MAX_VALUE;
		int maxY = Integer.MAX_VALUE;
		
		// TODO
		//pamietaj o passage'ach!!!
		for (Land land : neighbours) {

		}

		if (minX == Integer.MIN_VALUE || minY == Integer.MIN_VALUE || maxX == Integer.MAX_VALUE || maxY == Integer.MAX_VALUE) {
			mapSegment = newMapSegment(LAND_MAX_HEIGHT, LAND_MAX_WIDTH);
			return;
		}
		mapSegment = newMapSegment(maxY - minY, maxX - minX);
	}

	private void populateMapSegment() {
		int startX = borderLandPassage == null ? mapSegment[0].length / 2 : borderLandPassage.getX();
		int startY = borderLandPassage == null ? mapSegment.length / 2 : borderLandPassage.getY();
		minX = maxX = startX;
		minY = maxY = startY;
		populateMapSegmentInternal(startX, startY, INITIAL_CONTINUE_RATE);
	}

	private void populateMapSegmentInternal(int x, int y, double minResultToContinue) {
		if (minResultToContinue <= 0 || x < 0 || y < 0 || x >= mapSegment[0].length || y >= mapSegment.length || mapSegment[y][x] != 0) {
			return;
		}
		double nextField = random.nextDouble();
		if (nextField > minResultToContinue) {
			return;
		}
		mapSegment[y][x] = GRASS;
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
		
		log.info(WorldGenerationUtils.mapToString(mapSegment));
		populateMapSegmentInternal(x + 1, y, minResultToContinue - CONTINUE_RATE_DROP);
		populateMapSegmentInternal(x, y + 1, minResultToContinue - CONTINUE_RATE_DROP);
		populateMapSegmentInternal(x - 1, y, minResultToContinue - CONTINUE_RATE_DROP);
		populateMapSegmentInternal(x, y - 1, minResultToContinue - CONTINUE_RATE_DROP);
	}

	private int[][] newMapSegment(int height, int width) {
		int[][] mapSegment = new int[height][];
		for (int i = 0; i < height; ++i) {
			mapSegment[i] = new int[width];
		}
		return mapSegment;
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
