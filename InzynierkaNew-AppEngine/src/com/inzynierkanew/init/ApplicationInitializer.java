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

import com.inzynierkanew.endpoints.general.PropertyEndpoint;
import com.inzynierkanew.endpoints.map.DungeonEndpoint;
import com.inzynierkanew.endpoints.map.FieldTypeEndpoint;
import com.inzynierkanew.endpoints.map.LandEndpoint;
import com.inzynierkanew.endpoints.map.TownEndpoint;
import com.inzynierkanew.endpoints.players.FactionEndpoint;
import com.inzynierkanew.endpoints.players.HeroEndpoint;
import com.inzynierkanew.endpoints.players.PlayerEndpoint;
import com.inzynierkanew.endpoints.players.UnitTypeEndpoint;
import com.inzynierkanew.entities.map.FieldType;
import com.inzynierkanew.entities.players.Faction;
import com.inzynierkanew.entities.players.Item;
import com.inzynierkanew.entities.players.Item.ItemClass;
import com.inzynierkanew.entities.players.UnitType;
import com.inzynierkanew.utils.EMF;
import com.inzynierkanew.utils.SharedConstants;
import com.inzynierkanew.utils.WorldGenerationUtils;
import com.inzynierkanew.world.WorldGenerator;
import com.inzynierkanew.world.WorldGeneratorFactory;

public class ApplicationInitializer implements ServletContextListener {

	private final Log log = LogFactory.getLog(getClass());

	private boolean cleanDatastoreOnInit = true;
	private boolean repopulatePropertiesOnInit = false;
	private boolean repopulateTypesOnInit = false;
	private int landsGeneratedOnInit = 3;

	public static final long HUMANS_ID = 1L;
	public static final long MONSTERS_ID = 2L;
	
	private final FactionEndpoint factionEndpoint = new FactionEndpoint();
	private final FieldTypeEndpoint fieldTypeEndpoint = new FieldTypeEndpoint();
	private final UnitTypeEndpoint unitTypeEndpoint = new UnitTypeEndpoint();
	private final LandEndpoint landEndpoint = new LandEndpoint();
	private final TownEndpoint townEndpoint = new TownEndpoint();
	private final DungeonEndpoint dungeonEndpoint = new DungeonEndpoint();
	private final PlayerEndpoint playerEndpoint = new PlayerEndpoint();
	private final HeroEndpoint heroEndpoint = new HeroEndpoint();
	private final PropertyEndpoint propertyEndpoint = new PropertyEndpoint();

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		log.info("Application is starting...");
				
		//Dungeon newDungeon = dungeonEndpoint.insertDungeon(new Dungeon(-2, -3, 0, 1L, null));
		//Dungeon checkDungeon = dungeonEndpoint.getDungeon(newDungeon.getKey().getId());
		//log.info("new: "+newDungeon);
		//log.info("check: "+checkDungeon);
		
		if (cleanDatastoreOnInit) {
			clearDatastore();
		}
		if (repopulatePropertiesOnInit) {
			clearProperties();
			insertProperties();
		}
		if (repopulateTypesOnInit) {
			clearTypes();
			insertTypes();
		}

		// HERO.setGold
		// log.info("Giving gold to players...");
		// for(Player player: playerEndpoint.listPlayer(null, null).getItems()){
		// player.setGold(player.getGold()+50000000);
		// playerEndpoint.updatePlayer(player);
		// }

		// for(Hero hero: heroEndpoint.listHero(null, null).getItems()){
		// hero.setExperience(0);
		// heroEndpoint.updateHero(hero);
		// }

//		for (Dungeon dungeon : dungeonEndpoint.listDungeon(null, null).getItems()) {
//			dungeon.setFactionId(MONSTERS_ID);
//			dungeonEndpoint.updateDungeon(dungeon);
//		}

		// for(Town town: townEndpoint.listTown(null, null).getItems()){
		// log.info(town);
		// log.info("\n");
		// }

		WorldGenerationUtils.init(createPrintableSymbols());
//		try {
//			Thread.sleep(5000L);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

		if (landEndpoint.listLand(null, 1).getItems().isEmpty()) {
			log.info("Firing world generation...");
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

	private void insertProperties() {
//		propertyEndpoint.insertProperty(new Property("MaxHeroLevel", "" + MAX_HERO_LEVEL));
//		propertyEndpoint.insertProperty(new Property("BaseXpPerLevel", "" + BASE_XP_PER_LEVEL));
//		propertyEndpoint.insertProperty(new Property("NextLevelFactor", "" + NEXT_LEVEL_FACTOR));
//		propertyEndpoint.insertProperty(new Property("BaseGoldPerVictory", "" + BASE_GOLD_PER_VICTORY));
//		propertyEndpoint.insertProperty(new Property("GoldXpModifier", "" + GOLD_XP_MODIFIER));
//		propertyEndpoint.insertProperty(new Property("GoldRandomFactor", "" + GOLD_RANDOM_FACTOR));
	}

	private void clearProperties() {
		clearEntities("Property");
	}

	private void clearDatastore() {
		clearEntities("Player", "PlayerSession", "Hero", "Land", "Passage", "Dungeon", "Town", "DungeonVisit");
	}

	private void clearTypes() {
		clearEntities("Faction", "UnitType", "FieldType", "Item");
	}

	private void clearEntities(String... entityNames) {
		EntityManager manager = null;
		try {
			manager = EMF.get().createEntityManager();
			for (String entityName : entityNames) {
				log.info("Deleting from table " + entityName + "...");
				manager.createQuery("delete from " + entityName + " as " + entityName).executeUpdate();
			}
		} finally {
			manager.close();
		}
	}

	private void insertTypes() {
		log.info("Initializing datastore with factions, unit and field types...");

		Faction humans = new Faction(HUMANS_ID, "Humans", true, false);
		Faction monsters = new Faction(MONSTERS_ID, "Monsters", false, true);

		List<Faction> factions = Arrays.asList(humans, monsters);

		List<UnitType> unitTypes = Arrays.asList(
				// name cost texture faction mindmg maxdmg hp speed ranged
				// missiles
				new UnitType(1L, "Goblin", 100, "goblin", monsters.getId(), 3, 6, 50, 6, true, 6), new UnitType(2L, "Orc", 400, "orc",
						monsters.getId(), 10, 15, 200, 5, false, 0), new UnitType(3L, "Troll", 2000, "troll", monsters.getId(), 30, 100,
						1000, 3, false, 0), new UnitType(101L, "Swordsman", 300, "swordsman", humans.getId(), 8, 12, 150, 5, false, 0),
				new UnitType(102L, "Archer", 450, "archer", humans.getId(), 6, 10, 100, 5, true, 8), new UnitType(103L, "Knight", 1500,
						"knight", humans.getId(), 18, 36, 500, 7, false, 0));

		List<FieldType> fieldTypes = Arrays.asList(new FieldType(1L, "Road", true, "road"), new FieldType(2L, "Passage", true, "passage"),
				new FieldType(3L, "Town", true, "town"), new FieldType(4L, "Dungeon", true, "dungeon"), new FieldType(100L, "Grass", true,
						"grass"), new FieldType(110L, "Mountains", false, "mountains"));
		
		List<Item> items = Arrays.asList(
				new Item(1L, "Sword of Might +5", ItemClass.STANDARD, 1, 5, 0, 0), 
				new Item(2L, "Sword of Might +10", ItemClass.STANDARD, 6, 10, 0, 0),
				new Item(3L, "Sword of Might +20", ItemClass.STANDARD, 11, 20, 0, 0), 
				new Item(11L, "Dagger of Swiftness +5", ItemClass.STANDARD, 1, 0, 5, 0), 
				new Item(12L, "Dagger of Swiftness +10", ItemClass.STANDARD, 6, 0, 10, 0), 
				new Item(13L, "Dagger of Swiftness +20", ItemClass.STANDARD, 11, 0, 20, 0), 
				new Item(21L, "Staff of Enlightment +5", ItemClass.STANDARD, 1, 0, 0, 5), 
				new Item(22L, "Staff of Enlightment +10", ItemClass.STANDARD, 6, 0, 0, 10), 
				new Item(23L, "Staff of Enlightment +20", ItemClass.STANDARD, 11, 0, 0, 20),
				new Item(101L, "Divine Shield", ItemClass.MAGICAL, 11, 40, 20, 0),
				new Item(102L, "Cloak of Darkness", ItemClass.MAGICAL, 11, 10, 10, 50),
				new Item(103L, "Ring of Fortitude", ItemClass.MAGICAL, 11, 10, 25, 25)
				);

		for (Faction faction: factions) {
			factionEndpoint.insertFaction(faction);
		}

		for (UnitType unitType: unitTypes) {
			unitTypeEndpoint.insertUnitType(unitType);
		}

		for (FieldType fieldType: fieldTypes) {
			fieldTypeEndpoint.insertFieldType(fieldType);
		}
		for(Item item: items){
			
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		log.info("Application is stopping...");

		log.info("Application stopped");
	}

}
