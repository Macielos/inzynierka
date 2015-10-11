package com.inzynierkanew.tests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.junit.Assert;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.google.api.server.spi.response.CollectionResponse;
import com.inzynierkanew.endpoints.map.LandEndpoint;
import com.inzynierkanew.endpoints.map.PassageEndpoint;
import com.inzynierkanew.endpoints.players.PlayerEndpoint;
import com.inzynierkanew.entities.map.Dungeon;
import com.inzynierkanew.entities.map.Land;
import com.inzynierkanew.entities.map.Passage;
import com.inzynierkanew.entities.players.Hero;
import com.inzynierkanew.entities.players.Player;
import com.inzynierkanew.utils.Point;
import com.inzynierkanew.utils.WorldGenerationUtils;
import com.inzynierkanew.world.GraphEdge;
import com.inzynierkanew.world.WorldGenerationException;
import com.inzynierkanew.world.WorldGenerator;
import com.inzynierkanew.world.WorldGeneratorFactory;

public class WorldGenerationTest {

	private Log log = LogFactory.getLog(getClass());
	
	private static Map<Long, Land> lands = new HashMap<>();
	
	//@org.junit.Test
	public void testLandGenerationNoExistingLands() throws WorldGenerationException{
		/*
		 * NIE DZIA£A 
		 * 3.0
		 * SEVERE: com.inzynierkanew.world.WorldGenerationException: Path GraphEdge [13, 32] - [8, 27] not found!
			Seed: 2408747665701923831
			Map Segment: 
		 * 
		 * 
		 * 
		 */
		
		
		//while(true){
		long x = new Random().nextLong();
		
		LandEndpoint landEndpoint = mockLandEndpoint();
		PlayerEndpoint playerEndpoint = mockPlayerEndpoint();
		PassageEndpoint passageEndpoint = mockPassageEndpoint();
		WorldGenerator generator = new WorldGenerator(x, landEndpoint, playerEndpoint, passageEndpoint); //new Random(-2343597961835384248L));
		generator.generateLand();
		/*	try {
				Thread.sleep(4000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
		//}
		
		//saved seeds:
		//2.0, 0.1
		// 4951068435187737292L - connecting crossroads trwa³o za d³ugo
		// 531034766426565621L - po prawej tworzy siê square
		// -8633507051595676011L - by³y problemy z dopasowaniem wielkoœci landu do istniej¹cego s¹siedniego passage'u
		//-8287553692781528699L - by³y problemy z granicami przy generowaniu landu z istniej¹cym s¹siadem
		//3.0, 0.1
		//-1017751249576539139L - po lewej droga i crossroads wjecha³y na border pointy

	}
	
	//@org.junit.Test
	public void testLandGenerationSingleNeighbour() throws WorldGenerationException{
		long x = new Random().nextLong();//1234567890L;
		LandEndpoint landEndpoint = mockLandEndpointSingleNeighbour();
		PlayerEndpoint playerEndpoint = mockPlayerEndpoint();
		PassageEndpoint passageEndpoint = mockPassageEndpoint();
		WorldGenerator generator = new WorldGenerator(x, landEndpoint, playerEndpoint, passageEndpoint); //new Random(-2343597961835384248L));
		generator.generateLand();
	}
	
	//@org.junit.Test
	public void testLandGenerationMultipleNeighbours() throws WorldGenerationException{
		long x = -5210437816999656336L; //new Random().nextLong();//1234567890L;
		//dziala: 7143712281646963543
		//path not found: -5210437816999656336
		LandEndpoint landEndpoint = mockLandEndpointMultipleNeighbours();
		PlayerEndpoint playerEndpoint = mockPlayerEndpoint();
		PassageEndpoint passageEndpoint = mockPassageEndpoint();
		WorldGenerator generator = new WorldGenerator(x, landEndpoint, playerEndpoint, passageEndpoint); //new Random(-2343597961835384248L));
		generator.generateLand();
	}
	
	//@org.junit.Test
	public void testLandGenerationMultipleGenerationOrders() throws WorldGenerationException{
		final int COUNT = 10;
		
		LandEndpoint landEndpoint = mockLandEndpointMultipleNeighbours();
		PlayerEndpoint playerEndpoint = mockPlayerEndpoint();
		PassageEndpoint passageEndpoint = mockPassageEndpoint();
		for(int i=0; i<COUNT; ++i){
			long seed = new Random().nextLong();//1234567890L;
			new WorldGenerator(seed, landEndpoint, playerEndpoint, passageEndpoint).generateAndPersistLand();	
		}
	}
	
	@org.junit.Test
	public void testMethodParameter(){
		AtomicBoolean parameter = new AtomicBoolean(false);
		internal(parameter);
		Assert.assertTrue(parameter.get());
	}
	
	private void internal(AtomicBoolean parameter){
		parameter.set(true);
	}
	
	//@org.junit.Test
	public void testRoadBuilding() throws WorldGenerationException{
		int[][] mapSegment = WorldGenerator.newMapSegment(8, 8);
		LandEndpoint landEndpoint = mockLandEndpoint();
		PlayerEndpoint playerEndpoint = mockPlayerEndpoint();
		PassageEndpoint passageEndpoint = mockPassageEndpoint();
		for(int i=0; i<8; ++i){
			for(int j=0; j<8; ++j){
				mapSegment[j][i] = 2;
			}
		}
		mapSegment[2][3] = 0;
		mapSegment[1][4] = 0;
		mapSegment[0][4] = 0;
		mapSegment[2][5] = 0;
		WorldGenerator generator = new WorldGenerator(mapSegment, landEndpoint, playerEndpoint, passageEndpoint);
		generator.buildRoadRecursive(new GraphEdge(new Point(0, 2), new Point(7, 2)));
		log.info(WorldGenerationUtils.mapToString(mapSegment));
	}
	
	//@org.junit.Test
	public void testContainsPoints(){
		Set<Point> points = new HashSet<>(2);
		points.add(new Point(1, 1));
		points.add(new Point(2, 4));
		points.add(new Point(2, 4));
		Assert.assertEquals(2, points.size());
		Assert.assertTrue(points.contains(new Point(1, 1)));
		Assert.assertTrue(points.contains(new Point(2, 4)));
		Assert.assertFalse(points.contains(new Point(2, 1)));
	}
	
	//@org.junit.Test
	public void testMapToString() {
		int[][] map = new int[][]{
				new int[]{
						1, 2, 3
				}, 
				new int[]{
						6, 5, 4
				}
		};
		log.info(WorldGenerationUtils.mapToString(map));
	}
	
	//@org.junit.Test
	public void testCalcMapSegment(){
		long result = WorldGenerationUtils.calcMapSegment(100, 400, 200, 500);
		log.info(result);
		//Assert.assertEquals(1000000000004L, result);
	}
	
	private static Land newLand(){
		Land land = new Land();
		land.setId(1L);
		land.setTown(null);
		land.setFields(new int[]{
				0, 0, 2, 7, 2, 2, 2, 2, 0, 0, 
				0, 9, 2, 6, 2, 2, 2, 2, 2, 0, 
				2, 6, 6, 6, 2, 2, 2, 2, 2, 0, 
				2, 2, 2, 2, 2, 2, 2, 2, 2, 0, 
				2, 2, 2, 0, 0, 0, 2, 2, 2, 0, 
		});
		land.setPassages(Arrays.asList(new Passage(8, 10, WorldGenerator.PASSAGE, WorldGenerator.UP)));
		land.setDungeons(Arrays.asList(new Dungeon(6, 11, WorldGenerator.DUNGEON)));
		land.setMinX(5);
		land.setMaxX(14);
		land.setMinY(10);
		land.setMaxY(14);
		land.setHasFreePassage(true);
		land.setMapSegment(WorldGenerationUtils.calcMapSegment(land));
		return land;
	}
	
	private static Land newLand2(){
		Land land = new Land();
		land.setId(1L);
		land.setTown(null);
		land.setFields(new int[]{
				0, 0, 2, 7, 2,
				0, 9, 2, 6, 2, 
				2, 6, 6, 6, 2, 
				2, 6, 2, 2, 2, 
				2, 6, 2, 2, 2, 
				2, 6, 2, 2, 2, 
				2, 6, 2, 2, 0, 
				2, 6, 2, 2, 0, 
				2, 6, 2, 0, 0, 
				2, 6, 2, 0, 0
		});
		land.setPassages(Arrays.asList(new Passage(3, 0, WorldGenerator.PASSAGE, WorldGenerator.UP)));
		land.setDungeons(Arrays.asList(new Dungeon(1, 1, WorldGenerator.DUNGEON)));
		land.setMinX(0);
		land.setMaxX(4);
		land.setMinY(0);
		land.setMaxY(9);
		land.setHasFreePassage(true);
		land.setMapSegment(WorldGenerationUtils.calcMapSegment(land));
		return land;
	}
	
	private static Player newPlayer(){
		Player player = new Player();
		Hero hero = new Hero();
		hero.setCurrentLandId(1L);
		player.setHero(hero);
		return player;
	}
	
	public static LandEndpoint mockLandEndpoint(){
		LandEndpoint landEndpoint = Mockito.mock(LandEndpoint.class);
		Mockito.
			when(landEndpoint.findLandsInNeighbourhood(Mockito.anyLong())).
			thenReturn(CollectionResponse.<Land> builder().setItems(new ArrayList<Land>(0)).build());
		Mockito.
			when(landEndpoint.findLandsWithFreePassages()).
			thenReturn(CollectionResponse.<Land> builder().setItems(new ArrayList<Land>(0)).build());
		return landEndpoint;
	}
	
	public static LandEndpoint mockLandEndpointSingleNeighbour(){
		LandEndpoint landEndpoint = Mockito.mock(LandEndpoint.class);
		Mockito.
			when(landEndpoint.findLandsInNeighbourhood(Mockito.anyLong())).
			thenReturn(CollectionResponse.<Land> builder().setItems(Arrays.asList(newLand())).build());
		Mockito.
			when(landEndpoint.findLandsWithFreePassages()).
			thenReturn(CollectionResponse.<Land> builder().setItems(Arrays.asList(newLand())).build());
		return landEndpoint;
	}
	
	public static LandEndpoint mockLandEndpointMultipleNeighbours() {
		LandEndpoint landEndpoint = Mockito.mock(LandEndpoint.class);
		Mockito.
			when(landEndpoint.findLandsInNeighbourhood(Mockito.anyLong())).
			thenReturn(CollectionResponse.<Land> builder().setItems(Arrays.asList(newLand(), newLand2())).build());
		Mockito.
			when(landEndpoint.findLandsWithFreePassages()).
			thenReturn(CollectionResponse.<Land> builder().setItems(Arrays.asList(newLand())).build());
		return landEndpoint;
	}
	
	public static LandEndpoint mockLandEndpointLandsForDump() {
		LandEndpoint landEndpoint = Mockito.mock(LandEndpoint.class);
		Mockito.
			when(landEndpoint.listLand(Mockito.anyString(), Mockito.anyInt())).
			thenReturn(CollectionResponse.<Land> builder().setItems(Arrays.asList(newLand(), newLand2())).build());
		return landEndpoint;
	}
	
	public static LandEndpoint mockLandEndpointMultipleGenerationOrders() {
		LandEndpoint landEndpoint = Mockito.mock(LandEndpoint.class);
		Mockito.
		when(landEndpoint.insertLand(Mockito.any(Land.class))).
		thenAnswer(new Answer<Land>() {
			@Override
			public Land answer(InvocationOnMock invocation) throws Throwable {
				Land land = (Land) invocation.getArguments()[0];
				lands.put((long)lands.size(), land);
				return land;
			}
		});
		Mockito.
		when(landEndpoint.updateLand(Mockito.any(Land.class))).
		thenAnswer(new Answer<Land>() {
			@Override
			public Land answer(InvocationOnMock invocation) throws Throwable {
				Land land = (Land) invocation.getArguments()[0];
				lands.put((long)lands.size(), land);
				return land;
			}
		});
		Mockito.
			when(landEndpoint.findLandsInNeighbourhood(Mockito.anyLong())).
			thenReturn(CollectionResponse.<Land> builder().setItems(lands.values()).build());
		Mockito.
			when(landEndpoint.findLandsWithFreePassages()).
			thenReturn(CollectionResponse.<Land> builder().setItems(findLandsWithFreePassage()).build());
		return landEndpoint;
	}
	
	private static List<Land> findLandsWithFreePassage(){
		List<Land> landsWithFreePassage = new ArrayList<>();
		for(Land land: lands.values()){
			if(land.hasFreePassage()){
				landsWithFreePassage.add(land);
			}
		}
		return landsWithFreePassage;
	}
	
	public static PlayerEndpoint mockPlayerEndpoint(){
		PlayerEndpoint playerEndpoint = Mockito.mock(PlayerEndpoint.class);
		Mockito.
			when(playerEndpoint.listPlayer(null, null)).
			thenReturn(CollectionResponse.<Player> builder().setItems(Arrays.asList(newPlayer())).build());
		return playerEndpoint;
	}
	
	public static PassageEndpoint mockPassageEndpoint(){
		PassageEndpoint passageEndpoint = Mockito.mock(PassageEndpoint.class);
		return passageEndpoint;
	}
}
