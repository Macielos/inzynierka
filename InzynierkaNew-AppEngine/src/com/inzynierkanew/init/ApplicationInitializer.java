package com.inzynierkanew.init;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;

import com.inzynierkanew.endpoints.map.FieldTypeEndpoint;
import com.inzynierkanew.endpoints.map.LandEndpoint;
import com.inzynierkanew.endpoints.players.FactionEndpoint;
import com.inzynierkanew.endpoints.players.HeroEndpoint;
import com.inzynierkanew.endpoints.players.PlayerEndpoint;
import com.inzynierkanew.endpoints.players.UnitTypeEndpoint;
import com.inzynierkanew.entities.map.FieldType;
import com.inzynierkanew.entities.players.Faction;
import com.inzynierkanew.entities.players.Hero;
import com.inzynierkanew.entities.players.Player;
import com.inzynierkanew.entities.players.UnitType;
import com.inzynierkanew.utils.EMF;
import com.inzynierkanew.utils.WorldGenerationUtils;
import com.inzynierkanew.world.WorldGenerator;
import com.inzynierkanew.world.WorldGeneratorFactory;

public class ApplicationInitializer implements ServletContextListener {

	private final Log log = LogFactory.getLog(getClass());

	private boolean cleanDatastoreOnInit = false;
	private boolean cleanTypesOnInit = false;
	private int landsGeneratedOnInit = 3;

	public static final long HUMANS_ID = 1L;
	public static final long MONSTERS_ID = 2L;
	
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		log.info("Application is starting...");

		if (cleanDatastoreOnInit) {
			cleanDatastore();
		}
		
		FactionEndpoint factionEndpoint = new FactionEndpoint();
		FieldTypeEndpoint fieldTypeEndpoint = new FieldTypeEndpoint();
		UnitTypeEndpoint unitTypeEndpoint = new UnitTypeEndpoint();
		LandEndpoint landEndpoint = new LandEndpoint();
		PlayerEndpoint playerEndpoint = new PlayerEndpoint();
		HeroEndpoint heroEndpoint = new HeroEndpoint();

		if (cleanTypesOnInit) {
			cleanTypes();

			if (factionEndpoint.listFaction(null, 1).getItems().isEmpty()) {
				log.info("Initializing datastore with factions, unit and field types...");

				Faction humans = new Faction(HUMANS_ID, "Humans", true, false);
				Faction monsters = new Faction(MONSTERS_ID, "Monsters", false, true);

				List<Faction> factions = Arrays.asList(humans, monsters);

				List<UnitType> unitTypes = Arrays.asList(
						// name cost texture faction mindmg maxdmg hp speed
						// ranged missiles
						new UnitType(1L, "Goblin", 100, "goblin", monsters.getId(), 3, 6, 50, 6, true, 10), 
						new UnitType(2L, "Orc",	400, "orc", monsters.getId(), 10, 15, 200, 5, false, 0), 
						new UnitType(3L, "Troll", 2000, "troll", monsters.getId(), 30, 100, 1000, 3, false, 0),
						new UnitType(101L, "Swordsman", 300, "swordsman", humans.getId(), 3, 6, 50, 5, false, 0), 
						new UnitType(102L, "Archer", 450, "archer", humans.getId(), 3, 6, 50, 5, true, 10), 
						new UnitType(103L, "Knight", 1500, "knight", humans.getId(), 3, 6, 50, 7, false, 0));

				List<FieldType> fieldTypes = Arrays.asList(
						new FieldType(1L, "Road", true, "road"), 
						new FieldType(2L, "Passage", true, "passage"), 
						new FieldType(3L, "Town", true, "town"), 
						new FieldType(4L, "Dungeon", true, "dungeon"),
						new FieldType(100L, "Grass", true, "grass"), 
						new FieldType(110L, "Mountains", false, "mountains"));

				for (Faction faction : factions) {
					factionEndpoint.insertFaction(faction);
				}

				for (UnitType unitType : unitTypes) {
					unitTypeEndpoint.insertUnitType(unitType);
				}

				for (FieldType fieldType : fieldTypes) {
					fieldTypeEndpoint.insertFieldType(fieldType);
				}
			}
		}
		
		log.info("Giving gold to players...");
		for(Player player: playerEndpoint.listPlayer(null, null).getItems()){
			//player.setGold(player.getGold()+50000000);
			playerEndpoint.updatePlayer(player);
		}

		WorldGenerationUtils.init(createPrintableSymbols());
		try {
			Thread.sleep(5000L);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		log.info("Firing world generation...");

		if (landEndpoint.listLand(null, 1).getItems().isEmpty()) {
			for (int i = 0; i < landsGeneratedOnInit; ++i) {
				WorldGeneratorFactory.fireWorldGeneration();
			}
		}
		log.info("Application started");
	}

	private Map<Integer, String> createPrintableSymbols() {
		Map<Integer, String> printableSymbols = new HashMap<>();

		printableSymbols.put(WorldGenerator.EMPTY, " ");
		printableSymbols.put(WorldGenerator.EXISTING_LAND, "E");
		printableSymbols.put(WorldGenerator.EXISTING_LAND_PASSAGE, "I");
		printableSymbols.put(WorldGenerator.CROSSROAD, "X");
		printableSymbols.put(WorldGenerator.OVERLAPPING, "V");

		printableSymbols.put(1, "+");
		printableSymbols.put(2, "I");
		printableSymbols.put(3, "T");
		printableSymbols.put(4, "D");
		printableSymbols.put(100, ".");
		printableSymbols.put(110, "^");

		return printableSymbols;
	}

	private void cleanDatastore() {
		log.info("Cleaning world and players...");
		EntityManager manager = null;
		try {
			manager = EMF.get().createEntityManager();
			manager.createQuery("delete from Player as Player").executeUpdate();
			manager.createQuery("delete from PlayerSession as PlayerSession").executeUpdate();
			manager.createQuery("delete from Hero as Hero").executeUpdate();
			manager.createQuery("delete from Land as Land").executeUpdate();
			manager.createQuery("delete from Passage as Passage").executeUpdate();
			manager.createQuery("delete from Dungeon as Dungeon").executeUpdate();
			manager.createQuery("delete from Town as Town").executeUpdate();
		} finally {
			manager.close();
		}
	}

	private void cleanTypes() {
		log.info("Cleaning unit & field types and factions...");

		EntityManager manager = null;
		try {
			manager = EMF.get().createEntityManager();
			manager.createQuery("delete from Faction as Faction").executeUpdate();
			manager.createQuery("delete from UnitType as UnitType").executeUpdate();
			manager.createQuery("delete from FieldType as FieldType").executeUpdate();
		} finally {
			manager.close();
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		log.info("Application is stopping...");

		log.info("Application stopped");
	}

}
