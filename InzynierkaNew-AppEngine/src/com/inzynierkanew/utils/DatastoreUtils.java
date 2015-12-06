package com.inzynierkanew.utils;

import java.util.Collection;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;

import com.inzynierkanew.entities.map.Land;
import com.inzynierkanew.entities.map.Passage;

public abstract class DatastoreUtils {

	private DatastoreUtils(){
		
	}
	
	public static void fetchLand(Land land){
		boolean freePassageFound = false;
		Log log = LogFactory.getLog(DatastoreUtils.class);
		for(Passage passage: land.getPassages()){
			if(passage.getNextLandId()==null && !passage.isPortal()){
				freePassageFound = true;
				break;
			}
		}
		if(freePassageFound!=land.hasFreePassage()){
			log.error("land "+land.getId()+", "+land.hasFreePassage());
			for(Passage passage: land.getPassages()){
				log.error("  passage "+passage.getKey().getId()+", "+passage.getNextLandId());
			}
			//throw new RuntimeException("Land has no free passages, but has flag!");
		}
	}
	
	public static void fetchLands(Collection<Land> lands){
		for(Land land: lands){
			fetchLand(land);
		}
	}
}
