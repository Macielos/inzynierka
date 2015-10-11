package com.inzynierkanew.utils;

import java.util.Collection;

import com.inzynierkanew.entities.map.Dungeon;
import com.inzynierkanew.entities.map.Land;
import com.inzynierkanew.entities.map.Passage;

public abstract class DatastoreUtils {

	private DatastoreUtils(){
		
	}
	
	public static void fetchLand(Land land){
		land.getTown();
		for(Passage passage: land.getPassages());
		for(Dungeon dungeon: land.getDungeons());
	}
	
	public static void fetchLands(Collection<Land> lands){
		for(Land land: lands){
			fetchLand(land);
		}
	}
}
