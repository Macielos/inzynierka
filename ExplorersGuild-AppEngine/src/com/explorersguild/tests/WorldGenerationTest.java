package com.explorersguild.tests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.explorersguild.endpoints.map.DungeonEndpoint;
import com.explorersguild.endpoints.map.FieldTypeEndpoint;
import com.explorersguild.endpoints.map.LandEndpoint;
import com.explorersguild.endpoints.map.PassageEndpoint;
import com.explorersguild.endpoints.map.TownEndpoint;
import com.explorersguild.endpoints.players.FactionEndpoint;
import com.explorersguild.endpoints.players.HeroEndpoint;
import com.explorersguild.endpoints.players.PlayerEndpoint;
import com.explorersguild.endpoints.players.UnitTypeEndpoint;
import com.explorersguild.entities.map.Land;
import com.explorersguild.entities.players.Faction;
import com.explorersguild.entities.players.Hero;
import com.explorersguild.entities.players.Player;
import com.explorersguild.entities.players.UnitType;
import com.explorersguild.utils.Point;
import com.explorersguild.utils.WorldGenerationUtils;
import com.explorersguild.world.WorldGenerationFailureException;
import com.explorersguild.world.WorldGenerationSevereException;
import com.explorersguild.world.WorldGenerator;
import com.explorersguild.world.WorldGenerator.GenerationOutcome;
import com.google.api.server.spi.response.CollectionResponse;

public class WorldGenerationTest {

	private Log log = LogFactory.getLog(getClass());

	private static Map<Long, Land> lands = new HashMap<>();

	@Test
	public void testLandGenerationNoExistingLands() throws WorldGenerationFailureException, WorldGenerationSevereException {
		WorldGenerator generator = newWorldGenerator();
		Land land = generator.generateLand();
		validateLand(land);
	}

	@Test
	public void testLandGenerationMultiGen() throws WorldGenerationFailureException, WorldGenerationSevereException {
		Random random = new Random();
		for(int i=0; i<10; ++i){
			long x = random.nextLong();

			WorldGenerator generator = newWorldGeneratorMultiGen(x);
			Land land = generator.generateLand();
			validateLand(land);
			log.info("\n\n\n\n\n");
		}
		lands.clear();
	}
	
	private void validateLand(Land land) {
		Assert.assertEquals(land.getFields().length, land.getHeight()*land.getWidth());
		Assert.assertEquals(land.getTownId()!=null, land.hasTown());
	}

	public static WorldGenerator newWorldGenerator() {
		return newWorldGenerator(new Random().nextLong());
	}

	public static WorldGenerator newWorldGenerator(long seed) {
		return new WorldGenerator(seed, mockLandEndpoint(), mockDungeonEndpoint(), mockPassageEndpoint(),
				mockTownEndpoint(), mockPlayerEndpoint(), mockHeroEndpoint(), mockFieldTypeEndpoint(),
				mockUnitTypeEndpoint(), mockFactionEndpoint());
	}

	public static WorldGenerator newWorldGenerator(int[][] mapSegment) {
		return new WorldGenerator(mapSegment, mockLandEndpoint(), mockDungeonEndpoint(), mockPassageEndpoint(),
				mockTownEndpoint(), mockPlayerEndpoint(), mockHeroEndpoint(), mockFieldTypeEndpoint(),
				mockUnitTypeEndpoint(), mockFactionEndpoint());
	}
	
	private WorldGenerator newWorldGeneratorMultiGen(long seed) {
		return new WorldGenerator(seed, mockLandEndpointMultipleGenerationOrders(), mockDungeonEndpoint(), mockPassageEndpoint(),
				mockTownEndpoint(), mockPlayerEndpoint(), mockHeroEndpoint(), mockFieldTypeEndpoint(),
				mockUnitTypeEndpoint(), mockFactionEndpoint());
	}

	@Test
	public void testLandGenerationSingleNeighbour() throws WorldGenerationFailureException, WorldGenerationSevereException {
		long x = new Random().nextLong();// 1234567890L;
		WorldGenerationUtils.init(WorldGenerationUtils.createPrintableSymbols());
		WorldGenerator generator = newWorldGenerator(x); 
		Land land = generator.generateLand();
		validateLand(land);
	}

	@Test
	public void testLandGenerationMultipleNeighbours() throws WorldGenerationFailureException, WorldGenerationSevereException {
		WorldGenerator generator = newWorldGenerator(); 
		Land land = generator.generateLand();
		validateLand(land);
	}

	@Test
	public void testLandGenerationBorderlandPassage() throws WorldGenerationFailureException, WorldGenerationSevereException {
		WorldGenerator generator = newWorldGenerator();
		Land land = generator.generateLand();
		validateLand(land);
	}

	@Test
	public void testLandGenerationMultipleGenerationOrders() throws WorldGenerationFailureException, WorldGenerationSevereException {
		final int COUNT = 10;
		for (int i = 0; i < COUNT; ++i) {
			GenerationOutcome outcome = newWorldGenerator().generateAndPersistLand();
			log.info(i+": "+outcome);
			Assert.assertTrue(outcome!=GenerationOutcome.SEVERE);
		}
	}

	@Test
	public void testContainsPoints() {
		Set<Point> points = new HashSet<>(2);
		points.add(new Point(1, 1));
		points.add(new Point(2, 4));
		points.add(new Point(2, 4));
		Assert.assertEquals(2, points.size());
		Assert.assertTrue(points.contains(new Point(1, 1)));
		Assert.assertTrue(points.contains(new Point(2, 4)));
		Assert.assertFalse(points.contains(new Point(2, 1)));
	}

	@Test
	public void testMapToString() {
		int[][] map = new int[][] { new int[] { 1, 2, 3 }, new int[] { 6, 5, 4 } };
		String s = WorldGenerationUtils.mapToString(map);
		Assert.assertNotNull(s);
	}

	@Test
	public void testCalcMapSegment() {
		long result = WorldGenerationUtils.calcMapSegment(100, 400, 200, 500);
		log.info(result);
		Assert.assertEquals(1000000000004L, result);
	}

	private static Land newLand() {
		Land land = new Land();
		land.setId(1L);
		land.setTownId(null);
		land.setFields(new int[] { 0, 0, 2, 7, 2, 2, 2, 2, 0, 0, 0, 9, 2, 6, 2, 2, 2, 2, 2, 0, 2, 6, 6, 6, 2, 2, 2, 2,
				2, 0, 2, 2, 2, 2, 2, 2, 2, 2, 2, 0, 2, 2, 2, 0, 0, 0, 2, 2, 2, 0, });
		land.setMinX(5);
		land.setMaxX(14);
		land.setMinY(10);
		land.setMaxY(14);
		land.setHasFreePassage(true);
		land.setMapSegment(WorldGenerationUtils.calcMapSegment(land));
		return land;
	}

	private static Land newLand2() {
		Land land = new Land();
		land.setId(1L);
		land.setTownId(null);
		land.setFields(new int[] { 0, 0, 2, 7, 2, 0, 9, 2, 6, 2, 2, 6, 6, 6, 2, 2, 6, 2, 2, 2, 2, 6, 2, 2, 2, 2, 6, 2,
				2, 2, 2, 6, 2, 2, 0, 2, 6, 2, 2, 0, 2, 6, 2, 0, 0, 2, 6, 2, 0, 0 });
		land.setMinX(0);
		land.setMaxX(4);
		land.setMinY(0);
		land.setMaxY(9);
		land.setHasFreePassage(true);
		land.setMapSegment(WorldGenerationUtils.calcMapSegment(land));
		return land;
	}

	private static Player newPlayer() {
		Player player = new Player();
		// Hero hero = new Hero(0, 0, 1L, 100L, 10, 10, 10);
		// player.setHeroId(hero.getId());
		return player;
	}

	public static LandEndpoint mockLandEndpoint() {
		LandEndpoint landEndpoint = Mockito.mock(LandEndpoint.class);
		Mockito.when(landEndpoint.findLandsInNeighbourhood(Mockito.anyLong()))
				.thenReturn(CollectionResponse.<Land> builder().setItems(new ArrayList<Land>(0)).build());
		Mockito.when(landEndpoint.findLandsWithFreePassages())
				.thenReturn(CollectionResponse.<Land> builder().setItems(new ArrayList<Land>(0)).build());
		Mockito.when(landEndpoint.insertLand(Mockito.any(Land.class))).thenAnswer(new Answer<Land>() {

			@Override
			public Land answer(InvocationOnMock invocation) throws Throwable {
				Land land = (Land) invocation.getArguments()[0];
				lands.put((long)lands.size(), land);
				return land;
			}
		});
		return landEndpoint;
	}

	public static LandEndpoint mockLandEndpointSingleNeighbour() {
		LandEndpoint landEndpoint = Mockito.mock(LandEndpoint.class);
		Mockito.when(landEndpoint.findLandsInNeighbourhood(Mockito.anyLong()))
				.thenReturn(CollectionResponse.<Land> builder().setItems(Arrays.asList(newLand())).build());
		Mockito.when(landEndpoint.findLandsWithFreePassages())
				.thenReturn(CollectionResponse.<Land> builder().setItems(Arrays.asList(newLand())).build());
		return landEndpoint;
	}

	public static LandEndpoint mockLandEndpointMultipleNeighbours() {
		LandEndpoint landEndpoint = Mockito.mock(LandEndpoint.class);
		Mockito.when(landEndpoint.findLandsInNeighbourhood(Mockito.anyLong()))
				.thenReturn(CollectionResponse.<Land> builder().setItems(Arrays.asList(newLand(), newLand2())).build());
		Mockito.when(landEndpoint.findLandsWithFreePassages())
				.thenReturn(CollectionResponse.<Land> builder().setItems(Arrays.asList(newLand())).build());
		return landEndpoint;
	}

	public static LandEndpoint mockLandEndpointLandsForDump() {
		LandEndpoint landEndpoint = Mockito.mock(LandEndpoint.class);
		Mockito.when(landEndpoint.listLand(Mockito.anyString(), Mockito.anyInt()))
				.thenReturn(CollectionResponse.<Land> builder().setItems(Arrays.asList(newLand(), newLand2())).build());
		return landEndpoint;
	}

	public static LandEndpoint mockLandEndpointMultipleGenerationOrders() {
		LandEndpoint landEndpoint = Mockito.mock(LandEndpoint.class);
		Mockito.when(landEndpoint.insertLand(Mockito.any(Land.class))).thenAnswer(new Answer<Land>() {
			@Override
			public Land answer(InvocationOnMock invocation) throws Throwable {
				Land land = (Land) invocation.getArguments()[0];
				lands.put((long) lands.size(), land);
				return land;
			}
		});
		Mockito.when(landEndpoint.updateLand(Mockito.any(Land.class))).thenAnswer(new Answer<Land>() {
			@Override
			public Land answer(InvocationOnMock invocation) throws Throwable {
				Land land = (Land) invocation.getArguments()[0];
				lands.put((long) lands.size(), land);
				return land;
			}
		});
		Mockito.when(landEndpoint.findLandsInNeighbourhood(Mockito.anyLong()))
				.thenReturn(CollectionResponse.<Land> builder().setItems(lands.values()).build());
		Mockito.when(landEndpoint.findLandsWithFreePassages())
				.thenReturn(CollectionResponse.<Land> builder().setItems(findLandsWithFreePassage()).build());
		return landEndpoint;
	}

	private static List<Land> findLandsWithFreePassage() {
		List<Land> landsWithFreePassage = new ArrayList<>();
		for (Land land : lands.values()) {
			if (land.hasFreePassage()) {
				landsWithFreePassage.add(land);
			}
		}
		return landsWithFreePassage;
	}

	public static PlayerEndpoint mockPlayerEndpoint() {
		PlayerEndpoint playerEndpoint = Mockito.mock(PlayerEndpoint.class);
		Mockito.when(playerEndpoint.listPlayer(null, null))
				.thenReturn(CollectionResponse.<Player> builder().setItems(Arrays.asList(newPlayer())).build());
		return playerEndpoint;
	}

	public static PassageEndpoint mockPassageEndpoint() {
		PassageEndpoint passageEndpoint = Mockito.mock(PassageEndpoint.class);
		return passageEndpoint;
	}

	public static DungeonEndpoint mockDungeonEndpoint() {
		DungeonEndpoint dungeonEndpoint = Mockito.mock(DungeonEndpoint.class);
		return dungeonEndpoint;
	}

	public static FieldTypeEndpoint mockFieldTypeEndpoint() {
		FieldTypeEndpoint fieldTypeEndpoint = Mockito.mock(FieldTypeEndpoint.class);
		return fieldTypeEndpoint;
	}

	public static UnitTypeEndpoint mockUnitTypeEndpoint() {
		UnitTypeEndpoint unitTypeEndpoint = Mockito.mock(UnitTypeEndpoint.class);
		Mockito.when(unitTypeEndpoint.listUnitType(Mockito.anyString(), Mockito.any(Integer.class)))
				.thenReturn(CollectionResponse.<UnitType> builder().setItems(unitTypes()).build());
		return unitTypeEndpoint;
	}

	public static FactionEndpoint mockFactionEndpoint() {
		FactionEndpoint factionEndpoint = Mockito.mock(FactionEndpoint.class);
		Mockito.when(factionEndpoint.getFactionsForDungeons(Mockito.anyString(), Mockito.any(Integer.class)))
				.thenReturn(
						CollectionResponse.<Faction> builder().setItems(Arrays.asList(newMonsterFaction())).build());
		Mockito.when(factionEndpoint.getFactionsForTowns(Mockito.anyString(), Mockito.any(Integer.class)))
				.thenReturn(CollectionResponse.<Faction> builder().setItems(Arrays.asList(newHumanFaction())).build());
		return factionEndpoint;
	}

	public static TownEndpoint mockTownEndpoint() {
		TownEndpoint townEndpoint = Mockito.mock(TownEndpoint.class);
		return townEndpoint;
	}

	public static HeroEndpoint mockHeroEndpoint() {
		HeroEndpoint heroEndpoint = Mockito.mock(HeroEndpoint.class);
		Mockito.when(heroEndpoint.listHero(Mockito.anyString(), Mockito.anyInt()))
				.thenReturn(CollectionResponse.<Hero> builder().setItems(new ArrayList<Hero>(0)).build());
		return heroEndpoint;
	}

	private static List<UnitType> unitTypes() {
		return Arrays.asList(
				// name cost texture faction mindmg maxdmg hp speed ranged
				// missiles
				new UnitType(1L, "Goblin", 100, "goblin.png", newMonsterFaction().getId(), 3, 6, 50, 6, true, 10, 1),
				new UnitType(2L, "Orc", 400, "orc.png", newMonsterFaction().getId(), 10, 15, 200, 5, false, 0, 1),
				new UnitType(3L, "Troll", 2000, "troll.png", newMonsterFaction().getId(), 30, 100, 1000, 3, false, 0, 11),
				new UnitType(101L, "Swordsman", 300, "swordsman.png", newHumanFaction().getId(), 3, 6, 50, 5, false, 0, 1),
				new UnitType(102L, "Archer", 450, "archer.png", newHumanFaction().getId(), 3, 6, 50, 5, true, 10, 1),
				new UnitType(103L, "Knight", 1500, "knight.png", newHumanFaction().getId(), 3, 6, 50, 7, false, 0, 11));
	}

	private static Faction newHumanFaction() {
		return new Faction(1L, "Humans", true, false);
	}

	private static Faction newMonsterFaction() {
		return new Faction(2L, "Monsters", false, true);
	}

}
