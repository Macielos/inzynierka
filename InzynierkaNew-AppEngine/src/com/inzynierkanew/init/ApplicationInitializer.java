package com.inzynierkanew.init;

import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;

import com.inzynierkanew.endpoints.map.FieldTypeEndpoint;
import com.inzynierkanew.endpoints.map.LandEndpoint;
import com.inzynierkanew.endpoints.players.FactionEndpoint;
import com.inzynierkanew.endpoints.players.ItemEndpoint;
import com.inzynierkanew.endpoints.players.UnitTypeEndpoint;
import com.inzynierkanew.entities.map.FieldType;
import com.inzynierkanew.entities.players.Faction;
import com.inzynierkanew.entities.players.Item;
import com.inzynierkanew.entities.players.Item.ItemClass;
import com.inzynierkanew.entities.players.UnitType;
import com.inzynierkanew.utils.EMF;
import com.inzynierkanew.world.WorldGeneratorFactory;

public class ApplicationInitializer implements ServletContextListener {

	private final Log log = LogFactory.getLog(getClass());

	private boolean cleanPlayersOnInit = false;
	private boolean cleanWorldOnInit = false;
	private boolean repopulateTypesOnInit = true;
	private int landsGeneratedOnInit = 20;

	public static final long HUMANS_ID = 1L;
	public static final long MONSTERS_ID = 2L;

	private final FactionEndpoint factionEndpoint = new FactionEndpoint();
	private final FieldTypeEndpoint fieldTypeEndpoint = new FieldTypeEndpoint();
	private final UnitTypeEndpoint unitTypeEndpoint = new UnitTypeEndpoint();
	private final LandEndpoint landEndpoint = new LandEndpoint();
	private final ItemEndpoint itemEndpoint = new ItemEndpoint();

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		log.info("Application is starting...");

		// Dungeon newDungeon = dungeonEndpoint.insertDungeon(new Dungeon(-2,
		// -3, 0, 1L, null));
		// Dungeon checkDungeon =
		// dungeonEndpoint.getDungeon(newDungeon.getKey().getId());
		// log.info("new: "+newDungeon);
		// log.info("check: "+checkDungeon);

		if (cleanPlayersOnInit) {
			clearPlayers();
		}
		if (cleanWorldOnInit) {
			clearWorld();
		}
		if (repopulateTypesOnInit) {
			clearTypes();
			insertTypes();
		}

		if (landEndpoint.listLand(null, 1).getItems().isEmpty()) {
			log.info("Firing world generation...");
			try {
				for (int i = 0; i < landsGeneratedOnInit; ++i) {
					WorldGeneratorFactory.fireWorldGeneration();
				}
			} catch (Exception e) {
				log.error(e, e);
			}
		}
		log.info("Application started");
	}

	private void clearPlayers() {
		clearEntities("Player", "PlayerSession", "Hero", "DungeonVisit", "TownVisit");
	}

	private void clearWorld() {
		clearEntities("Land", "Passage", "Dungeon", "Town", "DungeonVisit", "TownVisit");
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
				new UnitType(1L, "Goblin", 100, "goblin", monsters.getId(), 3, 6, 20, 6, true, 6, 1),
				new UnitType(2L, "Orc", 400, "orc", monsters.getId(), 10, 15, 80, 5, false, 0, 1),
				new UnitType(3L, "Troll", 2000, "troll", monsters.getId(), 30, 100, 400, 3, false, 0, 11),
				new UnitType(101L, "Swordsman", 300, "swordsman", humans.getId(), 8, 12, 60, 5, false, 0, 1),
				new UnitType(102L, "Archer", 450, "archer", humans.getId(), 6, 10, 40, 5, true, 8, 1),
				new UnitType(103L, "Knight", 1500, "knight", humans.getId(), 18, 36, 200, 7, false, 0, 11));

		List<FieldType> fieldTypes = Arrays.asList(
				new FieldType(2L, "Passage", true, "passage"), new FieldType(3L, "Town", true, "town"),
				new FieldType(4L, "Dungeon", true, "dungeon"), new FieldType(5L, "Portal", true, "portal"),
				
				new FieldType(21L, "RoadHorizontal", true, "road_horizontal"),
				new FieldType(22L, "RoadVertical", true, "road_vertical"),
				new FieldType(23L, "RoadEndUp", true, "road_end_up"),
				new FieldType(24L, "RoadEndDown", true, "road_end_down"),
				new FieldType(25L, "RoadEndLeft", true, "road_end_left"),
				new FieldType(26L, "RoadEndRight", true, "road_end_right"),
				new FieldType(27L, "RoadLeftUp", true, "road_left_up"),
				new FieldType(28L, "RoadLeftDown", true, "road_left_down"),
				new FieldType(29L, "RoadRightUp", true, "road_right_up"),
				new FieldType(30L, "RoadRightDown", true, "road_right_down"),
				new FieldType(31L, "RoadTUp", true, "road_t_up"),
				new FieldType(32L, "RoadTDown", true, "road_t_down"),
				new FieldType(33L, "RoadTLeft", true, "road_t_left"),
				new FieldType(34L, "RoadTRight", true, "road_t_right"),
				new FieldType(35L, "RoadX", true, "road_x"),

				new FieldType(100L, "Grass", true, "grass"), new FieldType(110L, "Mountains", false, "mountains"));

		List<Item> items = Arrays
				.asList(new Item(1L, "Sword of Might +5", ItemClass.STANDARD, 1, "item_sword", 650, 5, 0, 0),
						new Item(2L, "Sword of Might +10", ItemClass.STANDARD, 6, "item_sword", 950, 10, 0, 0),
						new Item(3L, "Sword of Might +20", ItemClass.STANDARD, 11, "item_sword", 1350, 20, 0, 0),
						new Item(11L, "Dagger of Swiftness +5", ItemClass.STANDARD, 1, "item_dagger", 600, 0, 5, 0),
						new Item(12L, "Dagger of Swiftness +10", ItemClass.STANDARD, 6, "item_dagger", 1000, 0, 10, 0),
						new Item(13L, "Dagger of Swiftness +20", ItemClass.STANDARD, 11, "item_dagger", 1300, 0, 20, 0),
						new Item(21L, "Staff of Enlightment +5", ItemClass.STANDARD, 1, "item_staff", 750, 0, 0, 5),
						new Item(22L, "Staff of Enlightment +10", ItemClass.STANDARD, 6, "item_staff", 1050, 0, 0, 10),
						new Item(23L, "Staff of Enlightment +20", ItemClass.STANDARD, 11, "item_staff", 1450, 0, 0, 20),
						new Item(101L, "Divine Shield", ItemClass.MAGICAL, 11, "item_shield_magical", 2700, 30, 35, 0),
						new Item(201L, "Sword of Punishment", ItemClass.MAGICAL, 11, "item_sword_long", 3100, 60, 10,
								5),
				new Item(301L, "Staff of Eternity", ItemClass.MAGICAL, 11, "item_staff_magical", 3500, 10, 10, 50),
				new Item(401L, "Ring of Fortitude", ItemClass.MAGICAL, 11, "item_ring_magical", 3800, 10, 25, 25),
				new Item(1001L, "Lightbringer", ItemClass.LEGENDARY, 11, "item_sword_holy", 11000, 160, 20, 80));

		for (Faction faction : factions) {
			factionEndpoint.insertFaction(faction);
		}

		for (UnitType unitType : unitTypes) {
			unitTypeEndpoint.insertUnitType(unitType);
		}

		for (FieldType fieldType : fieldTypes) {
			fieldTypeEndpoint.insertFieldType(fieldType);
		}
		for (Item item : items) {
			itemEndpoint.insertItem(item);
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		log.info("Application stopped");
	}

}
