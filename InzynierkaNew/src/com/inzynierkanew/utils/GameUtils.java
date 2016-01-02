package com.inzynierkanew.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import com.inzynierkanew.entities.players.itemendpoint.model.Item;
import com.inzynierkanew.entities.players.unittypeendpoint.model.UnitType;
import com.inzynierkanew.model.Unit;

public abstract class GameUtils {
	
	private static Comparator<Item> itemComparator = new Comparator<Item>() {
		@Override
		public int compare(Item lhs, Item rhs) {
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

	public static List<Item> itemIdsToItems(List<Integer> itemIds, Map<Integer, Item> itemMap) {
		List<Item> items = new ArrayList<>();
		Item item;
		for(Integer i: itemIds){
			item = itemMap.get(i);
			if(item!=null){
				items.add(item);
			}
		}
		return items;
	}
	
	public static void sortItems(List<Item> items){
		Collections.sort(items, itemComparator);
	}
}
