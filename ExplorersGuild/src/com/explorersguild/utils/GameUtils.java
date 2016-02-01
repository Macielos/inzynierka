package com.explorersguild.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.explorersguild.entities.players.itemtypeendpoint.model.ItemType;
import com.explorersguild.entities.players.unittypeendpoint.model.UnitType;
import com.explorersguild.model.BattleUnit;
import com.explorersguild.model.Unit;

public abstract class GameUtils {

	private static final Random random = new Random();
		
	private static Comparator<ItemType> itemComparator = new Comparator<ItemType>() {
		@Override
		public int compare(ItemType lhs, ItemType rhs) {
			long diff = lhs.getId() - rhs.getId();
			if(diff > 0){
				return 1;
			}
			if(diff < 0){
				return -1;
			}
			return 0;
		}
	};
	
	private GameUtils(){
		
	}
	
	public static double randomizedFactor(double deviation){
		return 1 - deviation + random.nextDouble()*2*deviation;
	}
	
	public static List<Unit> armyToUnitList(List<Integer> army, Map<Integer, UnitType> unitTypes) {
		if(army == null){
			return new ArrayList<>(0);
		}
		List<Unit> availableUnits = new ArrayList<>(army.size() / 2);
		for (int i = 0; i < army.size(); i += 2) {
			availableUnits.add(new Unit(unitTypes.get(army.get(i)), army.get(i + 1)));
		}
		return availableUnits;
	}
	
	public static List<BattleUnit> armyToBattleUnitList(List<Integer> army, Map<Integer, UnitType> unitTypes, int initialPosition) {
		if(army == null){
			return new ArrayList<>(0);
		}
		List<BattleUnit> availableUnits = new ArrayList<>(army.size() / 2);
		for (int i = 0; i < army.size(); i += 2) {
			availableUnits.add(new BattleUnit(unitTypes.get(army.get(i)), army.get(i + 1), initialPosition));
		}
		return availableUnits;
	}
	
	public static List<Integer> unitListToArmy(List<Unit> units) {
		List<Integer> army = new ArrayList<>(units.size()*2);
		for(Unit unit: units){
			army.add(unit.getUnitType().getId().intValue());
			army.add(unit.getCount());
		}
		return army;
	
	}
	
	public static boolean isArmyEmpty(List<Integer> army){
		if(army==null){
			return true;
		}
		if(army.isEmpty()){
			return true;
		}
		for(int i=1; i<army.size(); i+=2){
			if(army.get(i)>0){
				return false;
			}
		}
		return true;
	}

	public static List<ItemType> itemIdsToItems(List<Integer> itemIds, Map<Integer, ItemType> itemMap) {
		List<ItemType> items = new ArrayList<>();
		ItemType item;
		for(Integer i: itemIds){
			item = itemMap.get(i);
			if(item!=null){
				items.add(item);
			}
		}
		return items;
	}
	
	public static void sortItems(List<ItemType> items){
		Collections.sort(items, itemComparator);
	}
	
	public static ArrayList<Unit> copyUnits(List<Unit> units){
		ArrayList<Unit> copy = new ArrayList<>(units.size());
		for(Unit unit: units){
			copy.add(new Unit(unit.getUnitType(), unit.getCount()));
		}
		return copy;
	}	
	
	public static ArrayList<BattleUnit> copyBattleUnits(List<BattleUnit> units){
		ArrayList<BattleUnit> copy = new ArrayList<>(units.size());
		for(BattleUnit unit: units){
			copy.add(new BattleUnit(unit.getUnitType(), unit.getCount(), unit.getPosition()));
		}
		return copy;
	}

	public static List<Unit> battleUnitsToUnits(List<BattleUnit> playerArmy) {
		return new ArrayList<Unit>(playerArmy);
	}
	
	

}
