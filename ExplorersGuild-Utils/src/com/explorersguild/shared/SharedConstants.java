package com.explorersguild.shared;

public class SharedConstants {

	public static final int MAX_HERO_LEVEL = 100;
	public static final int BASE_XP_PER_LEVEL = 1000;
	public static final int NEXT_LEVEL_FACTOR = 500;
	
	public static final int BASE_GOLD_PER_VICTORY = 800;
	public static final double GOLD_XP_MODIFIER = 0.5;
	public static final double GOLD_RANDOM_FACTOR = 0.5;
	
	public static final double CHANCE_FOR_ITEM = 0.7;
	public static final int LEVELS_PER_NEXT_ITEM_LEVEL = 5;
	public static final int LEVELS_PER_NEXT_ITEM_SLOT = 5;
	public static final int MAX_EQUIPPED_ITEMS = 6;
	public static final int INVENTORY_SIZE = 30;
	
	public static final int INITIAL_SKILL_VALUE = 10;
	public static final int INITAL_SKILL_POINTS = 25;
	public static final int SKILL_POINTS_PER_LEVEL = 10;
	public static final int HERO_ITEM_LEVEL_DIFF = 5;
	
	public static final int HERO_INITIAL_GOLD = 10000;
	
	public static final int LOOT_MAX_SIZE = 3;
	public static final int MAX_ARMY_SIZE = 6;
	
	public static final int HERO_STRENGTH_DAMAGE_BONUS = 1;
	public static final int HERO_AGILITY_DAMAGE_BONUS = 1;
	public static final int HERO_INTELLIGENCE_DAMAGE_BONUS = 4;
	
	public static final String STANDARD = "STANDARD";
	public static final String MAGICAL = "MAGICAL";
	public static final String LEGENDARY = "LEGENDARY";
	
	public static final double STANDARD_ITEM_STOCK_FACTOR = 1.0;
	public static final double MAGICAL_ITEM_STOCK_FACTOR = 0.4;
	public static final double LEGENDARY_ITEM_STOCK_FACTOR = 0.1;
	
	public static final double ITEM_SELL_MODIFIER = 0.6;
	
	public static final String SEPARATOR = ",";
	
	public static final String ACTION = "action";
	public static final int NULL_ACTION = -1;
	public static final int ARRIVE = 0;
	public static final int MOVE = 1;
	public static final int DEPART = 2;
	
	public static final String HERO_ID = "heroId";
	public static final String CURRENT_LAND_ID = "currentLandId";
	public static final String NEW_X = "newX";
	public static final String NEW_Y = "newY";
	
	public static final Long TOWN_ID = 2L;
	public static final Long DUNGEON_ID = 3L;
	public static final Long PASSAGE_HORIZONTAL_ID = 4L;
	public static final Long PASSAGE_VERTICAL_ID = 5L;
	public static final Long PORTAL_ID = 6L;
	public static final Long ACTIVE_PORTAL_ID = 7L;
	
	public static final int UP = 1;
	public static final int DOWN = 2;
	public static final int LEFT = 3;
	public static final int RIGHT = 4;
	
	public static final int DAILY_REWARD = 5000;
	
}
