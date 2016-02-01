package com.explorersguild.init;

import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;

import com.explorersguild.endpoints.map.FieldTypeEndpoint;
import com.explorersguild.endpoints.map.LandEndpoint;
import com.explorersguild.endpoints.players.FactionEndpoint;
import com.explorersguild.endpoints.players.ItemTypeEndpoint;
import com.explorersguild.endpoints.players.UnitTypeEndpoint;
import com.explorersguild.entities.map.FieldType;
import com.explorersguild.entities.players.Faction;
import com.explorersguild.entities.players.ItemType;
import com.explorersguild.entities.players.ItemType.ItemClass;
import com.explorersguild.entities.players.UnitType;
import com.explorersguild.shared.SharedConstants;
import com.explorersguild.shared.TimeUtils;
import com.explorersguild.utils.EMF;
import com.explorersguild.utils.Statistics;
import com.explorersguild.world.WorldGenerator.GenerationOutcome;
import com.explorersguild.world.WorldGeneratorFactory;

public class ApplicationInitializer implements ServletContextListener {

	public static final long BASIC_NONPASSABLE_ID = 110L;
	public static final long BASIC_NONPASSABLE_ID2 = 111L;
	public static final long EXTRA_NONPASSABLE_ID = 120L;
	public static final long EXTRA_NONPASSABLE_ID2 = 121L;
	public static final long BASIC_PASSABLE_ID = 100L;
	public static final long BASIC_ROAD_ID = 1L;

	public static final boolean QUOTA_SAVING_MODE = true;

	private final Log log = LogFactory.getLog(getClass());

	private boolean cleanPlayersOnInit = false;
	private boolean cleanWorldOnInit = false;
	private boolean repopulateTypesOnInit = false;
	//used when developer needs to run some specific code with already filled datastore (eg. to fix a bug)
	private boolean runDevPluginOnInit = false;

	private int landsGeneratedOnInit = 10;

	public static final long HUMANS_ID = 1L;
	public static final long MONSTERS_ID = 2L;

	private final FactionEndpoint factionEndpoint = new FactionEndpoint();
	private final FieldTypeEndpoint fieldTypeEndpoint = new FieldTypeEndpoint();
	private final UnitTypeEndpoint unitTypeEndpoint = new UnitTypeEndpoint();
	private final LandEndpoint landEndpoint = new LandEndpoint();
	private final ItemTypeEndpoint itemEndpoint = new ItemTypeEndpoint();

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		log.info("Application is starting...");

		if (cleanPlayersOnInit) {
			clearPlayers();
		}
		if (cleanWorldOnInit) {
			clearWorld();
		}
		if (repopulateTypesOnInit) {
			clearTypes();
		}
		insertTypes();

		if (landEndpoint.listLand(null, 1).getItems().isEmpty()) {
			log.info("Firing world generation...");
			Statistics<GenerationOutcome> generationStatistics = new Statistics<>();
			for (GenerationOutcome outcome : GenerationOutcome.values()) {
				generationStatistics.add(outcome, 0);
			}
			long startTime = TimeUtils.now();
			GenerationOutcome outcome;
			try {
				for (int i = 0; i < landsGeneratedOnInit; ++i) {
					outcome = WorldGeneratorFactory.fireWorldGeneration();
					generationStatistics.increment(outcome);
				}
			} catch (Exception e) {
				log.error(e, e);
			}
			long lastTime = TimeUtils.timeElapsed(startTime);
			log.info("Generating " + landsGeneratedOnInit + " new land(s) took " + lastTime
					+ " milliseconds. Results are: " + generationStatistics);
		}
		if (runDevPluginOnInit) {
			runDevPlugin();
		}
		log.info("Application started");
	}

	private void runDevPlugin() {

	}

	private void clearPlayers() {
		clearEntities("Player", "PlayerSession", "Hero", "DungeonVisit", "TownVisit");
	}

	private void clearWorld() {
		clearEntities("Land", "Passage", "Dungeon", "Town", "DungeonVisit", "TownVisit");
	}

	private void clearTypes() {
		clearEntities("Faction", "UnitType", "FieldType", "ItemType");
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
				new FieldType(SharedConstants.TOWN_ID, "Town", true, "town", 1.0),
				new FieldType(SharedConstants.DUNGEON_ID, "Dungeon", true, "dungeon", 1.0),
				new FieldType(SharedConstants.PASSAGE_HORIZONTAL_ID, "Passage", true, "passage_horizontal", 1.0),
				new FieldType(SharedConstants.PASSAGE_VERTICAL_ID, "Passage", true, "passage_vertical", 1.0),
				new FieldType(SharedConstants.PORTAL_ID, "Portal", true, "portal", 1.0),
				new FieldType(SharedConstants.ACTIVE_PORTAL_ID, "Portal", true, "portal_active", 1.0),

				new FieldType(21L, "Road", true, "road_horizontal", 1.0),
				new FieldType(22L, "Road", true, "road_vertical", 1.0),
				new FieldType(23L, "Road", true, "road_end_up", 1.0),
				new FieldType(24L, "Road", true, "road_end_down", 1.0),
				new FieldType(25L, "Road", true, "road_end_left", 1.0),
				new FieldType(26L, "Road", true, "road_end_right", 1.0),
				new FieldType(27L, "Road", true, "road_left_up", 1.0),
				new FieldType(28L, "Road", true, "road_left_down", 1.0),
				new FieldType(29L, "Road", true, "road_right_up", 1.0),
				new FieldType(30L, "Road", true, "road_right_down", 1.0),
				new FieldType(31L, "Road", true, "road_t_up", 1.0),
				new FieldType(32L, "Road", true, "road_t_down", 1.0),
				new FieldType(33L, "Road", true, "road_t_left", 1.0),
				new FieldType(34L, "Road", true, "road_t_right", 1.0), 
				new FieldType(35L, "Road", true, "road_x", 1.0),

				new FieldType(BASIC_PASSABLE_ID, "Grass", true, "grass", 1.0),
				new FieldType(BASIC_NONPASSABLE_ID, "Mountains", false, "mountains", 1.0),
				new FieldType(BASIC_NONPASSABLE_ID2, "Mountains", false, "mountains2", 1.0),
				new FieldType(EXTRA_NONPASSABLE_ID, "Forest", false, "forest", 1.0),
				new FieldType(EXTRA_NONPASSABLE_ID2, "Forest", false, "forest2", 1.0));

		List<ItemType> items = Arrays
				.asList(new ItemType(1L, "Sword of Might +5", ItemClass.STANDARD, 1, "item_sword", 650, 5, 0, 0),
						new ItemType(2L, "Sword of Might +10", ItemClass.STANDARD, 6, "item_sword", 950, 10, 0, 0),
						new ItemType(3L, "Sword of Might +20", ItemClass.STANDARD, 11, "item_sword", 1350, 20, 0, 0),
						new ItemType(11L, "Dagger of Swiftness +5", ItemClass.STANDARD, 1, "item_dagger", 600, 0, 5, 0),
						new ItemType(12L, "Dagger of Swiftness +10", ItemClass.STANDARD, 6, "item_dagger", 1000, 0, 10, 0),
						new ItemType(13L, "Dagger of Swiftness +20", ItemClass.STANDARD, 11, "item_dagger", 1300, 0, 20, 0),
						new ItemType(21L, "Staff of Enlightment +5", ItemClass.STANDARD, 1, "item_staff", 750, 0, 0, 5),
						new ItemType(22L, "Staff of Enlightment +10", ItemClass.STANDARD, 6, "item_staff", 1050, 0, 0, 10),
						new ItemType(23L, "Staff of Enlightment +20", ItemClass.STANDARD, 11, "item_staff", 1450, 0, 0, 20),
						new ItemType(101L, "Divine Shield", ItemClass.MAGICAL, 11, "item_shield_magical", 2700, 30, 35, 0),
						new ItemType(201L, "Sword of Punishment", ItemClass.MAGICAL, 11, "item_sword_long", 3100, 60, 10,
								5),
				new ItemType(301L, "Staff of Eternity", ItemClass.MAGICAL, 11, "item_staff_magical", 3500, 10, 10, 50),
				new ItemType(401L, "Ring of Fortitude", ItemClass.MAGICAL, 11, "item_ring_magical", 3800, 10, 25, 25),
				new ItemType(1001L, "Lightbringer", ItemClass.LEGENDARY, 11, "item_sword_holy", 11000, 160, 20, 80));

		if (factionEndpoint.listFaction(null, 1).getItems().isEmpty()) {
			for (Faction faction : factions) {
				factionEndpoint.insertFaction(faction);
			}
		}

		if (unitTypeEndpoint.listUnitType(null, 1).getItems().isEmpty()) {

			for (UnitType unitType : unitTypes) {
				unitTypeEndpoint.insertUnitType(unitType);
			}
		}

		if (fieldTypeEndpoint.listFieldType(null, 1).getItems().isEmpty()) {
			for (FieldType fieldType : fieldTypes) {
				fieldTypeEndpoint.insertFieldType(fieldType);
			}
		}

		if (itemEndpoint.listItemType(null, 1).getItems().isEmpty()) {
			for (ItemType item : items) {
				itemEndpoint.insertItemType(item);
			}
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		log.info("Application stopped");
	}

}
