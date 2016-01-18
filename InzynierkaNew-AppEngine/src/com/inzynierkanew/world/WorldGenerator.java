package com.inzynierkanew.world;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;

import com.inzynierkanew.endpoints.map.DungeonEndpoint;
import com.inzynierkanew.endpoints.map.FieldTypeEndpoint;
import com.inzynierkanew.endpoints.map.LandEndpoint;
import com.inzynierkanew.endpoints.map.PassageEndpoint;
import com.inzynierkanew.endpoints.map.TownEndpoint;
import com.inzynierkanew.endpoints.players.FactionEndpoint;
import com.inzynierkanew.endpoints.players.HeroEndpoint;
import com.inzynierkanew.endpoints.players.PlayerEndpoint;
import com.inzynierkanew.endpoints.players.UnitTypeEndpoint;
import com.inzynierkanew.entities.map.Dungeon;
import com.inzynierkanew.entities.map.Land;
import com.inzynierkanew.entities.map.Passage;
import com.inzynierkanew.entities.map.Town;
import com.inzynierkanew.entities.players.Faction;
import com.inzynierkanew.entities.players.Hero;
import com.inzynierkanew.entities.players.UnitType;
import com.inzynierkanew.shared.SharedConstants;
import com.inzynierkanew.utils.Point;
import com.inzynierkanew.utils.RandomUtils;
import com.inzynierkanew.utils.Statistics;
import com.inzynierkanew.utils.WorldGenerationUtils;

public class WorldGenerator {

	// CONSTANTS
	private static final double INITIAL_CONTINUE_RATE = 4.0;
	private static final double CONTINUE_RATE_DROP = 0.1;

	public static final int LAND_MAX_HEIGHT = (int) (2 * INITIAL_CONTINUE_RATE / CONTINUE_RATE_DROP);
	public static final int LAND_MAX_WIDTH = LAND_MAX_HEIGHT;

	private static final double CHANCE_FOR_TOWN = 0.75;
	private static final double CHANCE_FOR_EXTRA_DUNGEON = 0.6;

	private static final int MIN_ACCEPTABLE_PINNACLE_WIDTH = 3;

	private static final int MIN_PASSAGES = 3;
	private static final int MIN_PASSAGE_GAP = 2;
	private static final int PASSAGE_MAX_RETRIES = 500;
	private static final double CHANCE_FOR_PASSAGE = 0.12;

	private static final int TOWN_MAX_RETRIES = 100;

	private static final int CROSSROAD_MIN_GAP = 5;
	private static final int CROSSROAD_GAP_DIFF = 3;

	private static final int ARMY_MIN_STRENGTH = 400;
	private static final int ARMY_INCREASE_PER_LEVEL = 250;

	public static final int EMPTY = 0;
	public static final int EXISTING_LAND = -1;
	public static final int EXISTING_LAND_PASSAGE = -2;
	public static final int CROSSROAD = -3;
	public static final int OVERLAPPING = -4;

	public static final int UP = 1;
	public static final int DOWN = 2;
	public static final int LEFT = 3;
	public static final int RIGHT = 4;

	public static final long MAP_SEGMENT_FACTOR = 1000000000000L;

	public static final int SUGGESTED_LEVEL_MIN_DIFF_ALLOWED = 3;
	public static final int SUGGESTED_LEVEL_MAX_DIFF_ALLOWED = 8;

	private static final double ARMY_RANDOM_FACTOR = 0.5;

	private static final double CHANCE_FOR_BEGINNER_LEVEL = 0.2;
	private static final double NEXT_UNIT_CHANCE_CHANGE = 0.6;
	private static final double TOWN_ARMY_FACTOR = 2.8;
	private static final double DUNGEON_ARMY_FACTOR = 1.0;

	private static final Comparator<UnitType> unitComparator = new Comparator<UnitType>() {

		@Override
		public int compare(UnitType o1, UnitType o2) {
			double unit1Strength = o1.calcUnitStrength();
			double unit2Strength = o2.calcUnitStrength();
			if (unit1Strength < unit2Strength) {
				return -1;
			}
			if (unit1Strength > unit2Strength) {
				return 1;
			}
			return 0;
		}
	};
	private static final double CHANCE_TO_RANDOMIZE = 0.2;

	private static final double RANDOMIZE_INIT_CONTINUE_RATE = 1.4;
	private static final double RANDOMIZE_CONTINUE_RATE_DROP = 0.25;

	// LOG
	private Log log = LogFactory.getLog(getClass());

	// ENDPOINTS
	private final LandEndpoint landEndpoint;
	private final DungeonEndpoint dungeonEndpoint;
	private final PlayerEndpoint playerEndpoint;
	private final PassageEndpoint passageEndpoint;
	private final FieldTypeEndpoint fieldTypeEndpoint;
	private final UnitTypeEndpoint unitTypeEndpoint;
	private final FactionEndpoint factionEndpoint;
	private final TownEndpoint townEndpoint;
	private final HeroEndpoint heroEndpoint;

	private int PASSABLE;
	private int NON_PASSABLE;
	private int ROAD;
	private int PASSAGE;
	private int TOWN;
	private int DUNGEON;

	private Map<Long, List<UnitType>> dungeonUnitTypesByFactions;
	private Map<Long, List<UnitType>> townUnitTypesByFactions;
	private Map<Long, Faction> factions = new HashMap<>();

	private Set<Integer> roadFieldTypes;

	// EXISTING ELEMENTS
	private Land borderLand = null;
	private Passage borderLandPassage = null;

	private List<Land> neighbours;
	// private List<Point> neighbourFreePassagePoints = new ArrayList<>();

	// TEMP PRODUCTS
	private int[][] mapSegment;

	private int width;
	private int height;

	// private Point startBorderPoint;
	private List<Point> borderPoints = new ArrayList<>();
	private Set<Point> borderPointSet = new HashSet<>();

	private List<Point> candidatesForPassage = new ArrayList<>();

	private List<Passage> passages = new ArrayList<>();
	private Set<Point> passagePoints = new HashSet<>();

	private Point townPoint = null;

	private Set<GraphEdge> edges = new HashSet<>();
	private Set<Point> crossroads = new HashSet<>();
	private Set<Point> dungeons = new HashSet<>();

	// EXTREME POINTS OF GENERATED LAND
	private int minX;
	private int minY;
	private int maxX;
	private int maxY;

	// COORDINATES OF MAP SEGMENT TOP-LEFT CORNER IN WORLD MAP
	// global = local + mapSegmentCorner
	// local = global - mapSegmentCorner
	private int mapSegmentMinX;
	private int mapSegmentMinY;

	private int suggestedLevel;

	private Map<Long, Land> neighbourLandsToUpdate = new HashMap<>();
	private Map<Long, Passage> neighbourPassagesToConnectToLand = new HashMap<>();
	private Map<Long, Passage> neighbourPassagesToTransformIntoPortals = new HashMap<>();

	private final Random random;
	private final long seed;

	public WorldGenerator() {
		this(new Random().nextLong());
	}

	public WorldGenerator(long seed) {
		this(seed, new LandEndpoint(), new DungeonEndpoint(), new PassageEndpoint(), new TownEndpoint(),
				new PlayerEndpoint(), new HeroEndpoint(), new FieldTypeEndpoint(), new UnitTypeEndpoint(),
				new FactionEndpoint());
	}

	public WorldGenerator(long seed, LandEndpoint landEndpoint, DungeonEndpoint dungeonEndpoint,
			PassageEndpoint passageEndpoint, TownEndpoint townEndpoint, PlayerEndpoint playerEndpoint,
			HeroEndpoint heroEndpoint, FieldTypeEndpoint fieldTypeEndpoint, UnitTypeEndpoint unitTypeEndpoint,
			FactionEndpoint factionEndpoint) {
		this.seed = seed;
		this.random = new Random(seed);
		this.landEndpoint = landEndpoint;
		this.dungeonEndpoint = dungeonEndpoint;
		this.passageEndpoint = passageEndpoint;
		this.townEndpoint = townEndpoint;
		this.playerEndpoint = playerEndpoint;
		this.heroEndpoint = heroEndpoint;
		this.fieldTypeEndpoint = fieldTypeEndpoint;
		this.unitTypeEndpoint = unitTypeEndpoint;
		this.factionEndpoint = factionEndpoint;
	}

	public WorldGenerator(int[][] mapSegment, LandEndpoint landEndpoint, DungeonEndpoint dungeonEndpoint,
			PassageEndpoint passageEndpoint, TownEndpoint townEndpoint, PlayerEndpoint playerEndpoint,
			HeroEndpoint heroEndpoint, FieldTypeEndpoint fieldTypeEndpoint, UnitTypeEndpoint unitTypeEndpoint,
			FactionEndpoint factionEndpoint) {
		this.seed = new Random().nextLong();
		this.random = new Random(seed);
		this.mapSegment = mapSegment;
		this.landEndpoint = landEndpoint;
		this.dungeonEndpoint = dungeonEndpoint;
		this.passageEndpoint = passageEndpoint;
		this.townEndpoint = townEndpoint;
		this.playerEndpoint = playerEndpoint;
		this.heroEndpoint = heroEndpoint;
		this.fieldTypeEndpoint = fieldTypeEndpoint;
		this.unitTypeEndpoint = unitTypeEndpoint;
		this.factionEndpoint = factionEndpoint;
		height = mapSegment.length;
		width = mapSegment[0].length;
	}

	public void generateAndPersistLand() throws WorldGenerationException {
		try {
			Land land = generateLand();
			land = landEndpoint.insertLand(land);
			prepareAndPersistDungeons(land);
			boolean hasFreePassage;
			for (Passage passage : neighbourPassagesToConnectToLand.values()) {
				passage.setNextLandId(land.getId());
				passageEndpoint.updatePassage(passage);
			}
			for (Passage passage : neighbourPassagesToTransformIntoPortals.values()) {
				passageEndpoint.updatePassage(passage);
			}
			for (Land toUpdate : neighbourLandsToUpdate.values()) {
				hasFreePassage = hasFreePassage(toUpdate);
				if (hasFreePassage != toUpdate.hasFreePassage()) {
					toUpdate.setHasFreePassage(hasFreePassage);
					landEndpoint.updateLand(toUpdate);
				}
			}
		} catch (WorldGenerationException e) {
			log.error("Failed to generate new land with seed " + seed + ". Error is displayed below: ");
			log.error(e, e);
		}
	}

	public Land generateLand() throws WorldGenerationException {
		log.info("Generating land with seed " + seed + "...");
		initFields();
		prepareMapSegment();
		// log.info(WorldGenerationUtils.mapToString(mapSegment));
		findBorderLand();
		placeNeighbours();
		log.info(WorldGenerationUtils.mapToString(mapSegment));
		// selectFreePassages();
		populateMapSegment();
		log.info(WorldGenerationUtils.mapToString(mapSegment));
		smoothEdges();
		log.info(WorldGenerationUtils.mapToString(mapSegment));
		reduceMapSegment();
		prepareGroundForNeighbourPassages();
		prepareBorders();
		createPassages();
		// log.info(WorldGenerationUtils.mapToString(mapSegment));
		createTown();
		// log.info(WorldGenerationUtils.mapToString(mapSegment));
		buildRoads();
		// log.info(WorldGenerationUtils.mapToString(mapSegment));
		calcSuggestedLevel();
		loadUnitAndMapData();
		placeDungeons();
		randomizeTerrain();
		adjustRoads();
		log.info(WorldGenerationUtils.mapToString(mapSegment));
		Land land = toLand();
		log.info("Successfully generated land with seed " + seed);
		return land;
	}

	public void initFields() {
		PASSABLE = (int) fieldTypeEndpoint.findByName("Grass").getId();
		NON_PASSABLE = (int) fieldTypeEndpoint.findByName("Mountains").getId();
		ROAD = 1;
		PASSAGE = (int) fieldTypeEndpoint.findByName("Passage").getId();
		TOWN = (int) fieldTypeEndpoint.findByName("Town").getId();
		DUNGEON = (int) fieldTypeEndpoint.findByName("Dungeon").getId();
		
		roadFieldTypes = new HashSet<>(
				Arrays.asList(1, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, PASSAGE, TOWN, DUNGEON));
	}

	public void loadUnitAndMapData() {

		List<Faction> factionsForDungeons = (List<Faction>) factionEndpoint.getFactionsForDungeons(null, null)
				.getItems();
		dungeonUnitTypesByFactions = new HashMap<>(factionsForDungeons.size());
		for (Faction faction : factionsForDungeons) {
			dungeonUnitTypesByFactions.put(faction.getId(), new ArrayList<UnitType>());
			factions.put(faction.getId(), faction);
		}

		List<Faction> factionsForTowns = (List<Faction>) factionEndpoint.getFactionsForTowns(null, null).getItems();
		townUnitTypesByFactions = new HashMap<>(factionsForTowns.size());
		for (Faction faction : factionsForTowns) {
			townUnitTypesByFactions.put(faction.getId(), new ArrayList<UnitType>());
			factions.put(faction.getId(), faction);
		}

		List<UnitType> unitTypes = (List<UnitType>) unitTypeEndpoint.listUnitType(null, null).getItems();
		List<UnitType> unitTypesByFaction;
		for (UnitType unitType : unitTypes) {
			if (unitType.getMinLandLevel() <= suggestedLevel) {
				unitTypesByFaction = dungeonUnitTypesByFactions.get(unitType.getFactionId());
				if (unitTypesByFaction != null) {
					unitTypesByFaction.add(unitType);
				}
				unitTypesByFaction = townUnitTypesByFactions.get(unitType.getFactionId());
				if (unitTypesByFaction != null) {
					unitTypesByFaction.add(unitType);
				}
			}
		}
		for (List<UnitType> unitTypeList : dungeonUnitTypesByFactions.values()) {
			Collections.sort(unitTypeList, unitComparator);
		}
		for (List<UnitType> unitTypeList : townUnitTypesByFactions.values()) {
			Collections.sort(unitTypeList, unitComparator);
		}
	}

	private void prepareMapSegment() {
		mapSegment = newMapSegment(LAND_MAX_HEIGHT, LAND_MAX_WIDTH);
		height = LAND_MAX_HEIGHT;
		width = LAND_MAX_WIDTH;
	}

	private void findBorderLand() throws WorldGenerationException {
		// TODO tylko aktywni gracze posiadający herosa z currentLandId
		List<Hero> heroes = (List<Hero>) heroEndpoint.listHero(null, null).getItems();
		List<Land> landsWithFreePassage = (List<Land>) landEndpoint.findLandsWithFreePassages().getItems();
		Statistics<Long> playerPresenceInLands = new Statistics<>(landsWithFreePassage.size());
		Set<Long> landIds = new HashSet<>(landsWithFreePassage.size());
		for (Land land : landsWithFreePassage) {
			landIds.add(land.getId());
			playerPresenceInLands.add(land.getId(), 0);
		}
		for (Hero hero : heroes) {
			if (landIds.contains(hero.getCurrentLandId())) {
				playerPresenceInLands.increment(hero.getCurrentLandId());
			}
		}
		Long bestLandId = playerPresenceInLands.getMax();
		if (bestLandId != null) {
			for (Land land : landsWithFreePassage) {
				if (land.getId() == bestLandId) {
					borderLand = land;
					break;
				}
			}
			for (Passage passage : borderLand.getPassages()) {
				if (passage.getNextLandId() == null && !passage.isPortal()) {
					borderLandPassage = passage;
					break;
				}
			}
		}
		if (borderLand != null) {
			neighbourLandsToUpdate.put(borderLand.getId(), borderLand);
		}
		if (borderLandPassage != null) {
			neighbourPassagesToConnectToLand.put(borderLandPassage.getKey().getId(), borderLandPassage);
		}
		if (borderLand != null && borderLandPassage == null) {
			throwException("Possible incorrect lands in datastore. Land " + borderLand.getId()
					+ " is selected as having free passage, although it has none");
		}
		setBoundaries();
		String info = "Selected borderland " + (borderLand == null ? null : borderLand.getId()) + ""
				+ " and free passage " + (borderLandPassage == null ? null : borderLandPassage.getKey());
		if (borderLandPassage != null) {
			info += "\nFree passage global coordinates: "
					+ new Point(borderLandPassage.getX(), borderLandPassage.getY());
			info += "\nFree passage local coordinates: "
					+ new Point(borderLandPassage.getX() - mapSegmentMinX, borderLandPassage.getY() - mapSegmentMinY);
		}
	}

	private void setBoundaries() throws WorldGenerationException {
		if (borderLand == null) {
			mapSegmentMinX = 0;
			mapSegmentMinY = 0;
		} else {
			switch (borderLandPassage.getDirection()) {
			case UP:
				mapSegmentMinX = borderLandPassage.getX() - LAND_MAX_WIDTH / 2;
				mapSegmentMinY = borderLandPassage.getY() - LAND_MAX_HEIGHT * 7 / 8;
				break;
			case DOWN:
				mapSegmentMinX = borderLandPassage.getX() - LAND_MAX_WIDTH / 2;
				mapSegmentMinY = borderLandPassage.getY() - LAND_MAX_HEIGHT / 8;
				break;
			case LEFT:
				mapSegmentMinX = borderLandPassage.getX() - LAND_MAX_WIDTH * 7 / 8;
				mapSegmentMinY = borderLandPassage.getY() - LAND_MAX_HEIGHT / 2;
				break;
			case RIGHT:
				mapSegmentMinX = borderLandPassage.getX() - LAND_MAX_WIDTH / 8;
				mapSegmentMinY = borderLandPassage.getY() - LAND_MAX_HEIGHT / 2;
				break;
			default:
				throwException("Wrong direction of border land passage: " + borderLandPassage.getDirection());
			}
		}
	}

	private void placeNeighbours() {
		int minX = mapSegmentMinX;
		int minY = mapSegmentMinY;
		int maxX = minX + LAND_MAX_WIDTH;
		int maxY = minY + LAND_MAX_HEIGHT;

		neighbours = (borderLand == null) ? new ArrayList<Land>(0)
				: (List<Land>) landEndpoint
						.findLandsInNeighbourhood(WorldGenerationUtils.calcMapSegment(minX, minY, maxX, maxY))
						.getItems();

		for (Land neighbour : neighbours) {
			// log.info("Neighbour "+neighbour.getId()+":
			// "+(neighbour.hasFreePassage() ? "hasFreePassage" : "full"));
			// for(Passage passage: neighbour.getPassages()){
			// log.info(" Passage "+passage.getKey().getId()+":
			// free="+(passage.getNextLandId()==null));
			// }
			placeLandOnMapSegment(neighbour);
		}
		// log.info(WorldGenerationUtils.mapToString(mapSegment));
	}

	// TODO wyjebac
	/*
	 * private void selectFreePassages() { int localX; int localY; Point
	 * candidateForPassagePoint; for (Land neighbour : neighbours) { for
	 * (Passage passage : neighbour.getPassages()) { localX = passage.getX() -
	 * mapSegmentMinX; localY = passage.getY() - mapSegmentMinY;
	 * candidateForPassagePoint = getPassageExitPoint(passage); if
	 * (passage.getNextLandId() == null &&
	 * withinMapSegment(candidateForPassagePoint.x, candidateForPassagePoint.y))
	 * { if (getNeighbourCountOfType(candidateForPassagePoint.x -
	 * mapSegmentMinX, candidateForPassagePoint.y - mapSegmentMinY,
	 * NON_PASSABLE) == 3) { neighbourFreePassagePoints.add(new Point(localX,
	 * localY)); } else { passage.setPortal(true);
	 * neighbourPassagesToUpdate.put(passage.getKey().getId(), passage); } } } }
	 * }
	 */

	private void placeLandOnMapSegment(Land land) {
		int localMinX = land.getMinX() - mapSegmentMinX;
		int localMinY = land.getMinY() - mapSegmentMinY;

		int field;
		for (int j = 0; j < land.getHeight(); ++j) {
			for (int i = 0; i < land.getWidth(); ++i) {
				if (withinMapSegment(localMinX + i, localMinY + j)) {
					field = (int) land.getFields()[j * land.getWidth() + i];
					if (field != EMPTY) {
						if (field == PASSAGE) {
							mapSegment[localMinY + j][localMinX + i] = EXISTING_LAND_PASSAGE;
						} else {
							mapSegment[localMinY + j][localMinX + i] = EXISTING_LAND;
						}
					}
				}
			}
		}
	}

	// int[] fields = new int[mapSegment.length * mapSegment[0].length];
	// for (int i = 0; i < mapSegment[0].length; ++i) {
	// for (int j = 0; j < mapSegment.length; ++j) {
	// fields[j * mapSegment[0].length + i] = mapSegment[j][i];
	// }
	// }

	private void populateMapSegment() throws WorldGenerationException {
		boolean startFromPassage = false;
		Point startPoint = selectStartPoint();
		if (!hasType(startPoint.x, startPoint.y, EMPTY)) {
			int borderLandPassageLocalX = borderLandPassage.getX() - mapSegmentMinX;
			int borderLandPassageLocalY = borderLandPassage.getY() - mapSegmentMinY;
			startPoint = getNeighbourFieldOfType(borderLandPassageLocalX, borderLandPassageLocalY, EMPTY);
			startFromPassage = true;
		}

		if (startPoint == null) {
			throwException("Failed to select start point for map generation");
		}
		minX = maxX = startPoint.x;
		minY = maxY = startPoint.y;
		populateMapSegmentInternal(startPoint.x, startPoint.y, INITIAL_CONTINUE_RATE + (startFromPassage ? 0.0 : 1.2));
	}

	private Point selectStartPoint() throws WorldGenerationException {
		if (borderLand == null) {
			return new Point(width / 2, height / 2);
		}
		int borderLandPassageLocalX = borderLandPassage.getX() - mapSegmentMinX;
		int borderLandPassageLocalY = borderLandPassage.getY() - mapSegmentMinY;

		int minDistance = (int) ((INITIAL_CONTINUE_RATE - 1.2) / CONTINUE_RATE_DROP);

		Point startPoint = null;
		switch (borderLandPassage.getDirection()) {
		case UP:
			startPoint = new Point(borderLandPassageLocalX, borderLandPassageLocalY - minDistance);
			break;
		case DOWN:
			startPoint = new Point(borderLandPassageLocalX, borderLandPassageLocalY + minDistance);
			break;
		case LEFT:
			startPoint = new Point(borderLandPassageLocalX - minDistance, borderLandPassageLocalY);
			break;
		case RIGHT:
			startPoint = new Point(borderLandPassageLocalX + minDistance, borderLandPassageLocalY);
			break;
		default:
			throwException("Incorrect land passage: wrong direction: " + borderLandPassage.getDirection()
					+ ", passage: " + borderLandPassage);
		}
		return startPoint;
	}

	private void populateMapSegmentInternal(int x, int y, double minResultToContinue) {
		if (minResultToContinue <= 0 || x < 0 || y < 0 || x >= width || y >= height || mapSegment[y][x] != EMPTY) {
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

		for (Point point : getOrderedPointsForMapPopulation(x, y,
				borderLandPassage == null ? 0 : borderLandPassage.getDirection())) {
			populateMapSegmentInternal(point.x, point.y, minResultToContinue - CONTINUE_RATE_DROP);
		}

		// log.info(WorldGenerationUtils.mapToString(mapSegment));
		// populateMapSegmentInternal(x + 1, y, minResultToContinue -
		// CONTINUE_RATE_DROP);
		// populateMapSegmentInternal(x, y + 1, minResultToContinue -
		// CONTINUE_RATE_DROP);
		// populateMapSegmentInternal(x - 1, y, minResultToContinue -
		// CONTINUE_RATE_DROP);
		// populateMapSegmentInternal(x, y - 1, minResultToContinue -
		// CONTINUE_RATE_DROP);
	}

	private List<Point> getOrderedPointsForMapPopulation(int x, int y, int direction) {
		switch (direction) {
		case UP:
			return Arrays.asList(new Point(x, y + 1), new Point(x - 1, y), new Point(x + 1, y), new Point(x, y - 1));
		case DOWN:
			return Arrays.asList(new Point(x, y - 1), new Point(x - 1, y), new Point(x + 1, y), new Point(x, y + 1));
		case LEFT:
			return Arrays.asList(new Point(x + 1, y), new Point(x, y + 1), new Point(x, y - 1), new Point(x - 1, y));
		case RIGHT:
			return Arrays.asList(new Point(x - 1, y), new Point(x, y + 1), new Point(x, y - 1), new Point(x + 1, y));
		default:
			return Arrays.asList(new Point(x, y + 1), new Point(x - 1, y), new Point(x, y - 1), new Point(x + 1, y));
		}
	}

	private void reduceMapSegment() {
		width = maxX - minX + 1;
		height = maxY - minY + 1;
		int[][] reducedMapSegment = newMapSegment(height, width);

		for (int i = 0; i < width; ++i) {
			for (int j = 0; j < height; ++j) {
				reducedMapSegment[j][i] = mapSegment[j + minY][i + minX];
			}
		}
		mapSegment = reducedMapSegment;
		mapSegmentMinX += minX;
		mapSegmentMinY += minY;
		minX = 0;
		minY = 0;
		maxX = width - 1;
		maxY = height - 1;
	}

	public void smoothEdges() {
		smoothEdges(NON_PASSABLE, EMPTY);
		smoothEdges(EMPTY, NON_PASSABLE);
	}

	private void smoothEdges(int typeToErase, int replacementType) {
		boolean logSmoothing = false;

		boolean continueSmoothing = true;
		while (continueSmoothing) {
			continueSmoothing = false;
			continueSmoothing |= erasePinnaclesVertically(typeToErase, replacementType, 0, width, 1);
			if (logSmoothing) {
				log.info("Smoothed vertically");
				log.info(WorldGenerationUtils.mapToString(mapSegment));
			}
			continueSmoothing |= erasePinnaclesHorizontally(typeToErase, replacementType, 0, height, 1);
			if (logSmoothing) {
				log.info("Smoothed horizontally");
				log.info(WorldGenerationUtils.mapToString(mapSegment));
			}
			continueSmoothing |= erasePinnaclesVertically(typeToErase, replacementType, width - 1, 0, -1);
			if (logSmoothing) {
				log.info("Smoothed vertically backwards");
				log.info(WorldGenerationUtils.mapToString(mapSegment));
			}
			continueSmoothing |= erasePinnaclesHorizontally(typeToErase, replacementType, height - 1, 0, -1);
			if (logSmoothing) {
				log.info("Smoothed horizontally backwards");
				log.info(WorldGenerationUtils.mapToString(mapSegment));
			}
			// continueSmoothing = smoothedVertically || smoothedHorizontally;
		}
	}

	private boolean erasePinnaclesVertically(int typeToErase, int replacementType, int xStart, int xEnd, int xStep) {
		return erasePinnaclesVertically(typeToErase, replacementType, xStart, xEnd, xStep, 0, height, 1);
	}

	private boolean erasePinnaclesVertically(int typeToErase, int replacementType, int xStart, int xEnd, int xStep,
			int yStart, int yEnd, int yStep) {
		boolean replacementTypeFound;
		int foundAreaSize;
		boolean continueSmoothing = false;

		for (int i = xStart; i < xEnd; i += xStep) {
			foundAreaSize = 0;
			replacementTypeFound = false;
			for (int j = yStart; j < yEnd; j += yStep) {
				if (hasType(i, j, replacementType, EXISTING_LAND, EXISTING_LAND_PASSAGE)) {
					replacementTypeFound = true;
				}
				if (mapSegment[j][i] == typeToErase && replacementTypeFound) {
					++foundAreaSize;
				} else if (foundAreaSize > 0) {
					if (foundAreaSize < MIN_ACCEPTABLE_PINNACLE_WIDTH) {
						continueSmoothing = true;
						for (int k = 1; k <= foundAreaSize; ++k) {
							mapSegment[j - k * yStep][i] = replacementType;
						}
					}
					foundAreaSize = 0;
				} else {
					foundAreaSize = 0;
				}
			}
		}
		// log.info(WorldGenerationUtils.mapToString(mapSegment));
		return continueSmoothing;
	}

	private boolean erasePinnaclesHorizontally(int typeToErase, int replacementType, int yStart, int yEnd, int yStep) {
		return erasePinnaclesHorizontally(typeToErase, replacementType, yStart, yEnd, yStep, 0, width, 1);
	}

	private boolean erasePinnaclesHorizontally(int typeToErase, int replacementType, int yStart, int yEnd, int yStep,
			int xStart, int xEnd, int xStep) {
		boolean replacementTypeFound;
		int foundAreaSize;
		boolean continueSmoothing = false;
		for (int j = yStart; j < yEnd; j += yStep) {
			foundAreaSize = 0;
			replacementTypeFound = false;
			for (int i = xStart; i < xEnd; i += xStep) {
				if (hasType(i, j, replacementType, EXISTING_LAND, EXISTING_LAND_PASSAGE)) {
					replacementTypeFound = true;
				}
				if (mapSegment[j][i] == typeToErase && replacementTypeFound) {
					++foundAreaSize;
				} else if (foundAreaSize > 0) {
					if (foundAreaSize < MIN_ACCEPTABLE_PINNACLE_WIDTH) {
						continueSmoothing = true;
						for (int k = 1; k <= foundAreaSize; ++k) {
							mapSegment[j][i - k * xStep] = replacementType;
						}
					}
					foundAreaSize = 0;
				} else {
					foundAreaSize = 0;
				}
			}
		}
		// log.info(WorldGenerationUtils.mapToString(mapSegment));
		return continueSmoothing;
	}

	/*
	 * private void erasePinnaclesVertically(int typeToErase, int
	 * replacementType) {
	 * 
	 * boolean replacementTypeFound; int foundAreaSize; for (int i = 0; i <
	 * width / 2; ++i) { foundAreaSize = 0; replacementTypeFound = false; for
	 * (int j = 0; j < height; ++j) { if (hasType(i, j, replacementType,
	 * EXISTING_LAND, EXISTING_LAND_PASSAGE)) { replacementTypeFound = true; }
	 * if (mapSegment[j][i] == typeToErase && j > 0 && replacementTypeFound) {
	 * ++foundAreaSize; } else if (foundAreaSize > 0 && foundAreaSize <
	 * MIN_ACCEPTABLE_PINNACLE_WIDTH) { for (int k = 1; k <= foundAreaSize; ++k)
	 * { mapSegment[j - k][i] = replacementType; } foundAreaSize = 0; } else {
	 * foundAreaSize = 0; } } } //
	 * log.info(WorldGenerationUtils.mapToString(mapSegment)); }
	 * 
	 * private void erasePinnaclesVerticallyBackwards(int typeToErase, int
	 * replacementType) { boolean replacementTypeFound; int foundAreaSize; for
	 * (int i = width - 1; i >= width / 2; --i) { foundAreaSize = 0;
	 * replacementTypeFound = false; for (int j = height - 1; j >= height; --j)
	 * { if (hasType(i, j, replacementType, EXISTING_LAND,
	 * EXISTING_LAND_PASSAGE)) { replacementTypeFound = true; } if
	 * (mapSegment[j][i] == typeToErase && j < height - 1 &&
	 * replacementTypeFound) { ++foundAreaSize; } else if (foundAreaSize > 0 &&
	 * foundAreaSize < MIN_ACCEPTABLE_PINNACLE_WIDTH) { for (int k = 1; k <=
	 * foundAreaSize; ++k) { mapSegment[j + k][i] = replacementType; }
	 * foundAreaSize = 0; } else { foundAreaSize = 0; } } } //
	 * log.info(WorldGenerationUtils.mapToString(mapSegment)); }
	 * 
	 * private void erasePinnaclesHorizontally(int typeToErase, int
	 * replacementType) { boolean replacementTypeFound; int foundAreaSize; for
	 * (int j = 0; j < height; ++j) { foundAreaSize = 0; replacementTypeFound =
	 * false; for (int i = 0; i < width / 2; ++i) { if (hasType(i, j,
	 * replacementType, EXISTING_LAND, EXISTING_LAND_PASSAGE)) {
	 * replacementTypeFound = true; } if (mapSegment[j][i] == typeToErase && i >
	 * 0 && replacementTypeFound) { ++foundAreaSize; } else if (foundAreaSize >
	 * 0 && foundAreaSize < MIN_ACCEPTABLE_PINNACLE_WIDTH) { for (int k = 1; k
	 * <= foundAreaSize; ++k) { mapSegment[j][i - k] = replacementType; }
	 * foundAreaSize = 0; } else { foundAreaSize = 0; } } } //
	 * log.info(WorldGenerationUtils.mapToString(mapSegment)); }
	 * 
	 * private void erasePinnaclesHorizontallyBackwards(int typeToErase, int
	 * replacementType) { boolean replacementTypeFound; int foundAreaSize; for
	 * (int j = height - 1; j >= 0; --j) { foundAreaSize = 0;
	 * replacementTypeFound = false; for (int i = width - 1; i >= width / 2;
	 * --i) { if (hasType(i, j, replacementType, EXISTING_LAND,
	 * EXISTING_LAND_PASSAGE)) { replacementTypeFound = true; } if
	 * (mapSegment[j][i] == typeToErase && i < width - 1 &&
	 * replacementTypeFound) { ++foundAreaSize; } else if (mapSegment[j][i] ==
	 * replacementType && foundAreaSize > 0 && foundAreaSize <
	 * MIN_ACCEPTABLE_PINNACLE_WIDTH) { for (int k = 1; k <= foundAreaSize; ++k)
	 * { mapSegment[j][i + k] = replacementType; } foundAreaSize = 0; } else {
	 * foundAreaSize = 0; } } } //
	 * log.info(WorldGenerationUtils.mapToString(mapSegment)); }
	 */
	private void prepareGroundForNeighbourPassages() {
		Point newPassage;
		for (Land land : neighbours) {
			for (Passage passage : land.getPassages()) {
				if (passage.getNextLandId() == null) {
					newPassage = getPassageLocalExitPoint(passage);
					if (newPassage != null) {
						buildLandAroundExistingPassage(passage, newPassage);
					}
				}
			}
		}
	}

	private void prepareBorders() throws WorldGenerationException {
		Point point;
		for (int i = 0; i < width; ++i) {
			for (int j = 0; j < height; ++j) {
				if (isBorder(i, j)) {
					point = new Point(i, j);
					borderPoints.add(point);
					borderPointSet.add(point);
					if (isSuitableForPassage(i, j)) {
						candidatesForPassage.add(point);
					}
				}
			}
		}
	}

	public static int[][] newMapSegment(int height, int width) {
		int[][] mapSegment = new int[height][];
		for (int i = 0; i < height; ++i) {
			mapSegment[i] = new int[width];
		}
		return mapSegment;
	}

	private int getNeighbourCountOfType(int x, int y, int... types) {
		int neighbourLandFields = 0;
		for (Point point : neighbourPoints(x, y)) {
			if (withinMapSegment(point.x, point.y) && oneOf(mapSegment[point.y][point.x], types)) {
				++neighbourLandFields;
			}
		}
		return neighbourLandFields;
	}

	private int getNeighbourCountOfTypeCrossingIncluded(int x, int y, int... types) {
		int neighbourLandFields = 0;
		for (Point point : neighbourPointsCrossingIncluded(x, y)) {
			if (withinMapSegment(point.x, point.y) && oneOf(mapSegment[point.y][point.x], types)) {
				++neighbourLandFields;
			}
		}
		return neighbourLandFields;
	}

	private boolean oneOf(int x, int... options) {
		for (int option : options) {
			if (x == option) {
				return true;
			}
		}
		return false;
	}

	private boolean isSuitableForPassage(int x, int y) {
		return x > 0 && y > 0 && x < width - 1 && y < height - 1 && matchesPattern(x, y,
				new int[][] { new int[] { EMPTY, EMPTY, EMPTY }, new int[] { NON_PASSABLE, NON_PASSABLE, NON_PASSABLE },
						new int[] { NON_PASSABLE, NON_PASSABLE, NON_PASSABLE } },
				new int[][] { new int[] { EMPTY, NON_PASSABLE, NON_PASSABLE },
						new int[] { EMPTY, NON_PASSABLE, NON_PASSABLE },
						new int[] { EMPTY, NON_PASSABLE, NON_PASSABLE } },
				new int[][] { new int[] { NON_PASSABLE, NON_PASSABLE, EMPTY },
						new int[] { NON_PASSABLE, NON_PASSABLE, EMPTY },
						new int[] { NON_PASSABLE, NON_PASSABLE, EMPTY } },
				new int[][] { new int[] { NON_PASSABLE, NON_PASSABLE, NON_PASSABLE },
						new int[] { NON_PASSABLE, NON_PASSABLE, NON_PASSABLE }, new int[] { EMPTY, EMPTY, EMPTY } });
	}

	private boolean matchesPattern(int x, int y, int[][]... patterns) {
		for (int[][] pattern : patterns) {
			if (matchesPattern(x, y, pattern)) {
				return true;
			}
		}
		return false;
	}

	private boolean matchesPattern(int x, int y, int[][] pattern) {
		for (int i = -1; i <= 1; ++i) {
			for (int j = -1; j <= 1; ++j) {
				if (!withinMapSegment(x + i, y + j) || mapSegment[y + j][x + i] != pattern[j + 1][i + 1]) {
					return false;
				}
			}
		}
		return true;
	}

	private boolean hasType(int x, int y, int... types) {
		if (!withinMapSegment(x, y)) {
			return false;
		}
		for (int type : types) {
			if (mapSegment[y][x] == type) {
				return true;
			}
		}
		return false;
	}

	private Point getPassageLocalExitPoint(Passage passage) {
		int x = passage.getX() - mapSegmentMinX;
		int y = passage.getY() - mapSegmentMinY;
		switch (passage.getDirection()) {
		case UP:
			return new Point(x, y - 1);
		case DOWN:
			return new Point(x, y + 1);
		case LEFT:
			return new Point(x - 1, y);
		case RIGHT:
			return new Point(x + 1, y);
		default:
			return null;
		}
	}

	private boolean withinMapSegment(Point point) {
		return withinMapSegment(point.x, point.y);
	}

	private boolean withinMapSegment(int x, int y) {
		return x >= 0 && y >= 0 && x < width && y < height;
	}

	private boolean isBorder(int x, int y) {
		if (!withinMapSegment(x, y)) {
			return false;
		}
		if (hasType(x, y, EMPTY, EXISTING_LAND, EXISTING_LAND_PASSAGE)) {
			return false;
		}
		// if (x == 0 || y == 0 || x == width - 1 || y == height - 1) {
		// return true;
		// }
		if (hasType(x, y - 1, EMPTY, EXISTING_LAND, EXISTING_LAND_PASSAGE)) {
			return true;
		}
		if (hasType(x, y + 1, EMPTY, EXISTING_LAND, EXISTING_LAND_PASSAGE)) {
			return true;
		}
		if (hasType(x + 1, y, EMPTY, EXISTING_LAND, EXISTING_LAND_PASSAGE)) {
			return true;
		}
		if (hasType(x - 1, y, EMPTY, EXISTING_LAND, EXISTING_LAND_PASSAGE)) {
			return true;
		}
		return false;
	}

	private void createPassages() throws WorldGenerationException {
		Point newPassage;
		for (Land neighbourLand : neighbours) {
			for (Passage neighbourPassage : neighbourLand.getPassages()) {
				if (neighbourPassage.getNextLandId() == null) {
					newPassage = getPassageLocalExitPoint(neighbourPassage);
					if (newPassage != null && withinMapSegment(newPassage)) {
						if (getNeighbourCountOfType(newPassage.x, newPassage.y, NON_PASSABLE) == 3) {
							mapSegment[newPassage.y][newPassage.x] = PASSAGE;

							passages.add(new Passage(newPassage.x + mapSegmentMinX, newPassage.y + mapSegmentMinY,
									PASSAGE, opposite(neighbourPassage.getDirection()), neighbourLand.getId(),
									neighbourPassage.getX(), neighbourPassage.getY()));
							passagePoints.add(newPassage);

							neighbourPassage.setNextX(newPassage.x + mapSegmentMinX);
							neighbourPassage.setNextY(newPassage.y + mapSegmentMinY);
							excludePassageNeighbourPoints(newPassage.x, newPassage.y);
							neighbourPassagesToConnectToLand.put(neighbourPassage.getKey().getId(), neighbourPassage);
						} else {
							neighbourPassage.setPortal(true);
							neighbourPassagesToTransformIntoPortals.put(neighbourPassage.getKey().getId(),
									neighbourPassage);
						}
						neighbourLandsToUpdate.put(neighbourLand.getId(), neighbourLand);
					}
				}
			}
		}

		if (candidatesForPassage.isEmpty() && passages.isEmpty()) {
			throwException("No points suitable for passage found in the generated land");
		}

		int maxPassageCount = MIN_PASSAGES + (int) ((double) candidatesForPassage.size() * CHANCE_FOR_PASSAGE);
		int x;
		int y;
		for (int i = 0; i < PASSAGE_MAX_RETRIES; ++i) {
			if (candidatesForPassage.isEmpty()) {
				break;
			}
			newPassage = candidatesForPassage.get(random.nextInt(candidatesForPassage.size()));
			x = newPassage.x;
			y = newPassage.y;
			if (mapSegment[y][x] == NON_PASSABLE && getNeighbourFieldOfTypeCrossingIncluded(x, y, PASSAGE) == null) {
				mapSegment[y][x] = PASSAGE;
				excludePassageNeighbourPoints(x, y);
				passages.add(new Passage(x + mapSegmentMinX, y + mapSegmentMinY, PASSAGE, directionForPassage(x, y)));
				passagePoints.add(newPassage);
			}
			if (passages.size() >= maxPassageCount) {
				break;
			}
		}

		// boolean suitableForPassage;
		// for (int i = 0; i < borderPoints.size(); i += (MIN_PASSAGE_GAP +
		// random.nextInt(PASSAGE_GAP_DIFF))) {
		// suitableForPassage = false;
		// while (!suitableForPassage && i < borderPoints.size()) {
		// newPassage = borderPoints.get(i);
		// suitableForPassage = isSuitableForPassage(newPassage.x, newPassage.y)
		// && !pointsExcludedFromPlacingPassages.contains(newPassage);
		// ++i;
		// }
		// if (newPassage != null && isSuitableForPassage(newPassage.x,
		// newPassage.y)
		// && !pointsExcludedFromPlacingPassages.contains(newPassage)) {
		// passages.add(new Passage(newPassage.x+mapSegmentMinX,
		// newPassage.y+mapSegmentMinY, PASSAGE,
		// directionForPassage(newPassage.x, newPassage.y)));
		// passagePoints.add(newPassage);
		// mapSegment[newPassage.y][newPassage.x] = PASSAGE;
		// }
		// }
	}

	private void excludePassageNeighbourPoints(int x, int y) {
		for (int i = -MIN_PASSAGE_GAP; i <= MIN_PASSAGE_GAP; ++i) {
			for (int j = -MIN_PASSAGE_GAP; j <= MIN_PASSAGE_GAP; ++j) {
				if (i != 0 || j != 0) {
					candidatesForPassage.remove(new Point(x + i, y + j));
				}
			}
		}
	}

	private void buildLandAroundExistingPassage(Passage existingPassage, Point newPassage) {
		for (Point point : getPassageNearbyPointsForBuild(existingPassage, newPassage)) {
			if (withinMapSegment(point.x, point.y) && mapSegment[point.y][point.x] == EMPTY) {
				mapSegment[point.y][point.x] = NON_PASSABLE;
			}
		}
		for (Point point : getPassageNearbyPointsForErasure(existingPassage, newPassage)) {
			if (withinMapSegment(point.x, point.y) && mapSegment[point.y][point.x] == NON_PASSABLE) {
				mapSegment[point.y][point.x] = EMPTY;
			}
		}
	}

	private List<Point> getPassageNearbyPointsForBuild(Passage existingPassage, Point newPassage) {
		List<Point> points = new ArrayList<>(9);
		int x = newPassage.x;
		int y = newPassage.y;
		for (int i = -1; i <= 1; ++i) {
			for (int j = 0; j <= 2; ++j) {
				switch (existingPassage.getDirection()) {
				case UP:
					points.add(new Point(x + i, y - j));
					break;
				case DOWN:
					points.add(new Point(x + i, y + j));
					break;
				case LEFT:
					points.add(new Point(x - j, y + i));
					break;
				case RIGHT:
					points.add(new Point(x + j, y + i));
					break;
				}
			}
		}
		return points;
	}

	private List<Point> getPassageNearbyPointsForErasure(Passage existingPassage, Point newPassage) {
		List<Point> points = new ArrayList<>(9);
		int x = newPassage.x;
		int y = newPassage.y;
		for (int i = -1; i <= 1; ++i) {
			for (int j = -1; j <= -3; --j) {
				switch (existingPassage.getDirection()) {
				case UP:
					points.add(new Point(x + i, y - j));
					break;
				case DOWN:
					points.add(new Point(x + i, y + j));
					break;
				case LEFT:
					points.add(new Point(x - j, y + i));
					break;
				case RIGHT:
					points.add(new Point(x + j, y + i));
					break;
				}
			}
		}
		return points;
	}

	private int directionForPassage(int x, int y) {
		if (isFieldEmpty(x, y + 1)) {
			return DOWN;
		}
		if (isFieldEmpty(x, y - 1)) {
			return UP;
		}
		if (isFieldEmpty(x + 1, y)) {
			return RIGHT;
		}
		if (isFieldEmpty(x - 1, y)) {
			return LEFT;
		}
		return -1;
	}

	private int opposite(int direction) {
		switch (direction) {
		case UP:
			return DOWN;
		case DOWN:
			return UP;
		case LEFT:
			return RIGHT;
		case RIGHT:
			return LEFT;
		default:
			return -1;
		}
	}

	private boolean inRange(Point point1, Point point2, int maxRange) {
		return inRange(point1.x, point1.y, point2.x, point2.y, maxRange);
	}

	private boolean inRange(int x1, int y1, int x2, int y2, int maxRange) {
		return Math.abs(x1 - x2) + Math.abs(y1 - y2) <= maxRange;
	}

	private Point getNeighbourFieldOfType(int x, int y, int... fieldTypes) {
		for (Point point : neighbourPoints(x, y)) {
			if (withinMapSegment(point.x, point.y) && hasType(point.x, point.y, fieldTypes)) {
				return point;
			}
		}
		return null;
	}

	private Point getNonBorderNeighbourFieldOfType(int x, int y, int... fieldTypes) {
		for (Point point : neighbourPoints(x, y)) {
			if (withinMapSegment(point.x, point.y) && !isBorder(point.x, point.y)
					&& hasType(point.x, point.y, fieldTypes)) {
				return point;
			}
		}
		return null;
	}

	private Point getNeighbourFieldOfTypeCrossingIncluded(int x, int y, int fieldType) {
		for (Point point : neighbourPointsCrossingIncluded(x, y)) {
			if (withinMapSegment(point.x, point.y) && mapSegment[point.y][point.x] == fieldType) {
				return point;
			}
		}
		return null;
	}

	private void createTown() {
		double createTown = random.nextDouble();
		if (createTown <= CHANCE_FOR_TOWN || borderLand == null) {
			int x;
			int y;
			for (int i = 0; i < TOWN_MAX_RETRIES; ++i) {
				x = random.nextInt(width);
				y = random.nextInt(height);
				if (mapSegment[y][x] == NON_PASSABLE && !isBorder(x, y)
						&& getNeighbourFieldOfTypeCrossingIncluded(x, y, PASSAGE) == null) {
					mapSegment[y][x] = TOWN;
					townPoint = new Point(x, y);
					break;
				}
			}
		}
	}

	private void buildRoads() throws WorldGenerationException {
		selectCrossRoads();
		// log.info(WorldGenerationUtils.mapToString(mapSegment));
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
		int x;
		int y;
		for (int i = 0; i < width; i = i + CROSSROAD_MIN_GAP) {
			for (int j = 0; j < height; j = j + CROSSROAD_MIN_GAP) {
				x = i + random.nextInt(CROSSROAD_GAP_DIFF);
				y = j + random.nextInt(CROSSROAD_GAP_DIFF);
				if (isFieldValid(x, y, false)) {
					crossroads.add(new Point(x, y));
					// temp
					mapSegment[y][x] = CROSSROAD;
				}
			}
		}

		// log.info(WorldGenerationUtils.mapToString(mapSegment));

		if (townPoint != null) {
			crossroads.add(townPoint);
		}
		crossroads.addAll(passagePoints);
	}

	private void connectCrossRoads() throws WorldGenerationException {
		int i = CROSSROAD_MIN_GAP;
		GraphBuilder graphBuilder = new GraphBuilder(crossroads);
		while (connectCrossRoadsInternal(graphBuilder, i++)) {
			if (i > height + width) {
				throwException("Connecting roads went too far. Distance checked is: " + i + ". Aborting.");
			}
		}
		edges = graphBuilder.getEdgesIfConnected();
	}

	private boolean connectCrossRoadsInternal(GraphBuilder graphBuilder, int maxDistance) {
		Point[] crossRoadArray = crossroads.toArray(new Point[crossroads.size()]);
		// log.info("connectCrossRoadsInternal - iteration for max distance = "
		// + maxDistance);
		Point point1;
		Point point2;
		for (int i = 0; i < crossRoadArray.length; ++i) {
			for (int j = i + 1; j < crossRoadArray.length; ++j) {
				point1 = crossRoadArray[i];
				point2 = crossRoadArray[j];
				if (inRange(point1, point2, maxDistance)
						&& (!passagePoints.contains(point1) || !passagePoints.contains(point2))) {
					graphBuilder.addConnection(point1, point2);
				}
			}
		}
		return !graphBuilder.isConnected();
	}

	private void fillInRoadsOnMap() throws WorldGenerationException {
		printGraph();
		/*
		 * log.info("Crossroads:"); for (Point point: crossRoads){
		 * log.info(point); }
		 */
		for (GraphEdge edge : edges) {
			// log.info(edge);
			// if(buildRoadSimple(edge, false)){
			// buildRoadSimple(edge, true);
			// } else {
			buildRoadRecursive(edge);
			// }
			// log.info(WorldGenerationUtils.mapToString(mapSegment, true));
		}
		// log.info(WorldGenerationUtils.mapToString(mapSegment, true));

	}

	// private boolean buildRoadSimple(GraphEdge edge, boolean updateMap) {
	// int x = edge.point1.x;
	// int y = edge.point1.y;
	// int finishX = edge.point2.x;
	// int finishY = edge.point2.y;
	// int diffX;
	// int diffY;
	// boolean moving;
	// if (updateMap) {
	// //
	// log.info("Building road from "+edge.point1+" to "+edge.point2+" using
	// simple algorithm");
	// }
	// while ((x != finishX || y != finishY) && withinMapSegment(x, y)) {
	// if (mapSegment[y][x] == EMPTY) {
	// log.error("Road has gone to empty space! Point is: (" + x + ", " + y +
	// ")");
	// return false;
	// }
	// if (mapSegment[y][x] == EXISTING_LAND) {
	// log.error("Road has gone to an existing land! Point is: (" + x + ", " + y
	// + ")");
	// return false;
	// }
	// if (mapSegment[y][x] != DUNGEON && mapSegment[y][x] != TOWN &&
	// mapSegment[y][x] != PASSAGE && mapSegment[y][x] != CROSSROAD) {
	// if (updateMap) {
	// mapSegment[y][x] = ROAD;
	// }
	// }
	// diffX = finishX - x;
	// diffY = finishY - y;
	// moving = false;
	// if (Math.abs(diffX) > Math.abs(diffY)) {
	// if (x < finishX && isFieldValid(x + 1, y, true)) {
	// ++x;
	// moving = true;
	// } else if (x > finishX && isFieldValid(x - 1, y, true)) {
	// --x;
	// moving = true;
	// } else if (y < finishY && isFieldValid(x, y + 1, true)) {
	// ++y;
	// moving = true;
	// } else if (y > finishY && isFieldValid(x, y - 1, true)) {
	// --y;
	// moving = true;
	// }
	// } else {
	// if (y < finishY && isFieldValid(x, y + 1, true)) {
	// ++y;
	// moving = true;
	// } else if (y > finishY && isFieldValid(x, y - 1, true)) {
	// --y;
	// moving = true;
	// } else if (x < finishX && isFieldValid(x + 1, y, true)) {
	// ++x;
	// moving = true;
	// } else if (x > finishX && isFieldValid(x - 1, y, true)) {
	// --x;
	// moving = true;
	// }
	// }
	// if (!moving) {
	// log.error("Cannot move! Interrupted! Point is: (" + x + ", " + y + ")");
	// return false;
	// }
	// if (!withinMapSegment(x, y)) {
	// log.error("Road has gone beyond map borders! Point is: (" + x + ", " + y
	// + ")");
	// return false;
	// }
	// }
	// // log.info(WorldGenerationUtils.mapToString(mapSegment));
	// return true;
	// }

	public void buildRoadRecursive(GraphEdge edge) throws WorldGenerationException {
		// log.info("Building road from " + edge.point1 + " to " + edge.point2 +
		// " using recursive algorithm");
		List<Point> path = new ArrayList<Point>();
		boolean found = buildRoadRecursiveInternal(edge.point1, edge.point2, null, new HashSet<Point>(), path);
		if (!found) {
			throwException("Path " + edge + " not found!");
		}
		// log.info("Path found! ");
		for (Point point : path) {
			// log.info(point);
			if (!hasType(point.x, point.y, DUNGEON, TOWN, PASSAGE, CROSSROAD)) {
				mapSegment[point.y][point.x] = ROAD;
			}
			int x = point.x;
			int y = point.y;
			if (path.contains(new Point(x + 1, y)) && path.contains(new Point(x, y + 1))
					&& path.contains(new Point(x + 1, y + 1))) {
				log.warn("'Square' detected: " + point + ", " + new Point(x + 1, y) + ", " + new Point(x, y + 1) + ", "
						+ new Point(x + 1, y + 1));
			}
		}
	}

	private boolean buildRoadRecursiveInternal(Point current, Point destination, Point previous,
			Set<Point> visitedPoints, List<Point> path) {

		if (current.x == destination.x && current.y == destination.y) {
			return path.isEmpty();
		}

		visitedPoints.add(current);

		List<Point> orderedNeighbourPoints = getOrderedNeighbourPoints(current, destination, previous, visitedPoints);

		boolean found = false;
		for (Point next : orderedNeighbourPoints) {
			found = buildRoadRecursiveInternal(next, destination, current, visitedPoints, path);
			if (found) {
				path.add(next);
				return true;
			}
		}
		return false;

	}

	private List<Point> getOrderedNeighbourPoints(Point current, Point destination, Point previous,
			Set<Point> visitedPoints) {
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
			if (isFieldValid(point.x, point.y, true) && !point.equals(previous) && !visitedPoints.contains(point)) {
				orderedNeighbourPoints.add(point);
			}
		}
		return orderedNeighbourPoints;
	}

	private void placeDungeons() {
		int neighbourRoadFieldCount;
		double chanceForExtraDungeon;
		Point neighbourFreePoint;
		for (Point point : crossroads) {
			if (mapSegment[point.y][point.x] == CROSSROAD) {
				mapSegment[point.y][point.x] = ROAD;
				if (!isBorder(point.x, point.y)
						&& getNeighbourCountOfTypeCrossingIncluded(point.x, point.y, TOWN, DUNGEON, PASSAGE) == 0) {
					neighbourRoadFieldCount = getNeighbourCountOfType(point.x, point.y, ROAD, CROSSROAD);
					switch (neighbourRoadFieldCount) {
					case 1:
						dungeons.add(point);
						mapSegment[point.y][point.x] = DUNGEON;
						break;
					case 2:
					case 3:
						chanceForExtraDungeon = random.nextDouble();
						if (chanceForExtraDungeon < CHANCE_FOR_EXTRA_DUNGEON) {
							neighbourFreePoint = getNonBorderNeighbourFieldOfType(point.x, point.y, NON_PASSABLE);
							if (neighbourFreePoint != null) {
								dungeons.add(neighbourFreePoint);
								mapSegment[neighbourFreePoint.y][neighbourFreePoint.x] = DUNGEON;
							}
						}
					}
				}
			}
		}
	}

	private Land toLand() {
		Land land = new Land();
		land.setMinX(minX + mapSegmentMinX);
		land.setMinY(minY + mapSegmentMinY);
		land.setMaxX(maxX + mapSegmentMinX);
		land.setMaxY(maxY + mapSegmentMinY);

		int[] fields = new int[height * width];
		for (int j = 0; j < height; ++j) {
			for (int i = 0; i < width; ++i) {
				fields[j * width + i] = mapSegment[j][i] == EXISTING_LAND || mapSegment[j][i] == EXISTING_LAND_PASSAGE
						? EMPTY : mapSegment[j][i];
			}
		}
		land.setFields(fields);
		Long factionId = randomKey(townUnitTypesByFactions);
		if (townPoint != null) {
			Town town = (townPoint == null ? null
					: new Town(townPoint.x + mapSegmentMinX, townPoint.y + mapSegmentMinY, TOWN, "Aringrad", factionId,
							createTownArmy(factionId)));
			townEndpoint.insertTown(town);
			land.setTownId(town.getId());
		}
		land.setPassages(passages);
		;
		land.setHasFreePassage(hasFreePassage(passages));
		land.setHasTown(land.getTownId() != null);
		land.setMapSegment(WorldGenerationUtils.calcMapSegment(land));
		land.setSuggestedLevel(suggestedLevel);
		return land;
	}

	private void randomizeTerrain() {
		double randomize;
		for (Point point : crossroads) {
			if (!dungeons.contains(point)) {
				randomize = random.nextDouble();
				if (randomize < CHANCE_TO_RANDOMIZE) {
					randomizeTerrainInternal(point.x, point.y, RANDOMIZE_INIT_CONTINUE_RATE);
				}
			}
		}
	}

	private void randomizeTerrainInternal(int x, int y, double minResultToContinue) {
		if (minResultToContinue <= 0 || x < 0 || y < 0 || x >= width || y >= height) {
			return;
		}
		double nextField = random.nextDouble();
		if (nextField > minResultToContinue) {
			return;
		}
		if (mapSegment[y][x] == NON_PASSABLE && !isBorder(x, y)) {
			mapSegment[y][x] = PASSABLE;
		}

		for (Point point : getOrderedPointsForMapPopulation(x, y, -1)) {
			randomizeTerrainInternal(point.x, point.y, minResultToContinue - RANDOMIZE_CONTINUE_RATE_DROP);
		}
	}

	private void adjustRoads() {
		for (int j = 0; j < height; ++j) {
			for (int i = 0; i < width; ++i) {
				if(hasType(i, j, ROAD)){
					mapSegment[j][i] = getRoadAdjustment(i, j);
				}
			}
		}
	}
	
	private int getRoadAdjustment(int x, int y) {
		//X
		if(matchesPattern(x, y, true, true, true, true)){
			return 35;
		}
		//T
		if(matchesPattern(x, y, true, true, true, false)){
			return 31;
		}
		if(matchesPattern(x, y, false, true, true, true)){
			return 32;
		}
		if(matchesPattern(x, y, true, true, false, true)){
			return 33;
		}
		if(matchesPattern(x, y, true, false, true, true)){
			return 34;
		}
		//turns
		if(matchesPattern(x, y, true, true, false, false)){
			return 27;
		}
		if(matchesPattern(x, y, false, true, false, true)){
			return 28;
		}
		if(matchesPattern(x, y, true, false, true, false)){
			return 29;
		}
		if(matchesPattern(x, y, false, false, true, true)){
			return 30;
		}
		//straights
		if(matchesPattern(x, y, false, true, true, false)){
			return 21;
		}
		if(matchesPattern(x, y, true, false, false, true)){
			return 22;
		}
		//ends
		if(matchesPattern(x, y, true, false, false, false)){
			return 23;
		}
		if(matchesPattern(x, y, false, false, false, true)){
			return 24;
		}
		if(matchesPattern(x, y, false, true, false, false)){
			return 25;
		}
		if(matchesPattern(x, y, false, false, true, false)){
			return 26;
		}
		//"isles" - draw grass
		return 2;
	} 
	
	private boolean matchesPattern(int x, int y, boolean top, boolean left, boolean right, boolean bottom){
		return matchesFilter(x, y-1, top) && matchesFilter(x-1, y, left) && matchesFilter(x+1, y, right) && matchesFilter(x, y+1, bottom); 
	}
	
	private boolean matchesFilter(int x, int y, boolean filter){
		return withinMapSegment(x, y) && roadFieldTypes.contains(mapSegment[y][x]) == filter;
	}

	private void calcSuggestedLevel() {
		double beginnerLevel = random.nextDouble();
		if (beginnerLevel < CHANCE_FOR_BEGINNER_LEVEL) {
			suggestedLevel = 1;
			return;
		}
		double avgNeighbourLevel = 0;
		for (Land land : neighbours) {
			avgNeighbourLevel += land.getSuggestedLevel();
		}
		avgNeighbourLevel /= neighbours.size();
		int avgNeighbourLevelInt = (int) Math.round(avgNeighbourLevel) - SUGGESTED_LEVEL_MIN_DIFF_ALLOWED
				+ random.nextInt(SUGGESTED_LEVEL_MIN_DIFF_ALLOWED + SUGGESTED_LEVEL_MAX_DIFF_ALLOWED);
		suggestedLevel = Math.min(Math.max(1, avgNeighbourLevelInt), SharedConstants.MAX_HERO_LEVEL);
	}

	private void prepareAndPersistDungeons(Land land) {
		Dungeon dungeon;
		Long factionId = randomKey(dungeonUnitTypesByFactions);
		for (Point point : this.dungeons) {
			dungeon = new Dungeon(point.x + mapSegmentMinX, point.y + mapSegmentMinY, DUNGEON, land.getId(), factionId,
					createDungeonArmy(factionId));
			dungeonEndpoint.insertDungeon(dungeon);
		}
	}

	private int[] createTownArmy(Long factionId) {
		List<UnitType> unitTypesForSelectedFaction = townUnitTypesByFactions.get(factionId);
		double unitStrength;
		int totalArmyStrength = ARMY_MIN_STRENGTH + suggestedLevel * ARMY_INCREASE_PER_LEVEL;
		totalArmyStrength = (int) ((double) totalArmyStrength * TOWN_ARMY_FACTOR);
		List<Integer> army = new ArrayList<>(unitTypesForSelectedFaction.size() * 2);
		for (UnitType unitType : unitTypesForSelectedFaction) {
			army.add(unitType.getId().intValue());
			unitStrength = totalArmyStrength / unitTypesForSelectedFaction.size()
					* RandomUtils.randomizedFactor(ARMY_RANDOM_FACTOR);
			army.add(calcUnitCount(unitStrength, unitType));
		}
		int[] armyArray = new int[army.size()];
		int i = 0;
		for (Integer unit : army) {
			armyArray[i] = unit;
			++i;
		}
		return armyArray;
	}

	private Long randomKey(Map<Long, List<UnitType>> map) {
		List<Long> factions = new ArrayList<>(map.keySet());
		return factions.get(random.nextInt(factions.size()));
	}

	private int[] createDungeonArmy(Long factionId) {
		List<UnitType> unitTypesForSelectedFaction = dungeonUnitTypesByFactions.get(factionId);

		List<Integer> army = new ArrayList<>(SharedConstants.MAX_ARMY_SIZE * 2);
		double chanceForNextUnit = 1.0;
		double rand;
		UnitType nextUnitType;
		List<UnitType> armyUnitTypes = new ArrayList<>(SharedConstants.MAX_ARMY_SIZE);
		for (int i = 0; i < SharedConstants.MAX_ARMY_SIZE; ++i) {
			rand = random.nextDouble();
			if (rand < chanceForNextUnit) {
				nextUnitType = unitTypesForSelectedFaction.get(random.nextInt(unitTypesForSelectedFaction.size()));
				armyUnitTypes.add(nextUnitType);
			}
			chanceForNextUnit *= NEXT_UNIT_CHANCE_CHANGE;
		}

		double unitStrength;
		int totalArmyStrength = ARMY_MIN_STRENGTH + suggestedLevel * ARMY_INCREASE_PER_LEVEL;
		for (UnitType unitType : armyUnitTypes) {
			army.add(unitType.getId().intValue());
			unitStrength = totalArmyStrength / armyUnitTypes.size() * RandomUtils.randomizedFactor(ARMY_RANDOM_FACTOR)
					* DUNGEON_ARMY_FACTOR;
			army.add(calcUnitCount(unitStrength, unitType));
		}

		int[] armyArray = new int[army.size()];
		int i = 0;
		for (Integer unit : army) {
			armyArray[i] = unit;
			++i;
		}
		return armyArray;
	}

	private int calcUnitCount(double totalStrength, UnitType unitType) {
		return (int) Math.ceil(totalStrength / unitType.calcUnitStrength());
	}

	private boolean hasFreePassage(Land land) {
		return hasFreePassage(land.getPassages());
	}

	private boolean hasFreePassage(List<Passage> passages) {
		for (Passage passage : passages) {
			if (passage.getNextLandId() == null && !passage.isPortal()) {
				return true;
			}
		}
		return false;
	}

	private static Point[] neighbourPoints(int x, int y) {
		return new Point[] { new Point(x + 1, y), new Point(x - 1, y), new Point(x, y + 1), new Point(x, y - 1) };
	}

	private static Point[] neighbourPointsCrossingIncluded(int x, int y) {
		return new Point[] { new Point(x + 1, y), new Point(x - 1, y), new Point(x, y + 1), new Point(x, y - 1),
				new Point(x + 1, y + 1), new Point(x - 1, y + 1), new Point(x + 1, y - 1), new Point(x - 1, y - 1) };
	}

	// TODO check correctness
	private boolean isFieldValid(int x, int y, boolean acceptPassages) {
		return withinMapSegment(x, y)
				&& (!isBorder(x, y) || (acceptPassages && passagePoints.contains(new Point(x, y))))
				&& mapSegment[y][x] != EXISTING_LAND && mapSegment[y][x] != EXISTING_LAND_PASSAGE
				&& mapSegment[y][x] != EMPTY;
	}

	private boolean isFieldEmpty(int x, int y) {
		return withinMapSegment(x, y) && mapSegment[y][x] == EMPTY;
	}

	private void printGraph() {
		// log.info(WorldGenerationUtils.mapToString(mapSegment, true));
		// StringBuilder sb = new StringBuilder();
		// for (GraphEdge edge : edges) {
		// sb.append(edge + "\n");
		// }
		// log.info(sb.toString());
	}

	private void throwException(String message) throws WorldGenerationException {
		throw new WorldGenerationException(mapSegment, seed, message);
	}
	
	public int[][] getMapSegment() {
		return mapSegment;
	}

}
