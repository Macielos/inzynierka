package com.inzynierkanew.world;

import java.util.List;

import javax.persistence.EntityManager;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;

import com.inzynierkanew.dumps.WorldDump;
import com.inzynierkanew.endpoints.map.LandEndpoint;
import com.inzynierkanew.entities.map.Land;
import com.inzynierkanew.utils.DatastoreUtils;
import com.inzynierkanew.utils.EMF;


public class WorldGeneratorFactory {
	
	//private static final int MIN_LANDS = 3;
	//private static final double LANDS_PER_PLAYER = 0.25;
	private static final int COUNT = 2;

	private static final Log log = LogFactory.getLog(WorldGeneratorFactory.class);
	
	public static synchronized void fireWorldGeneration(){
		
		for(int i=0; i<COUNT; ++i){
//			try {
//				Thread.sleep(5000L);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			new WorldDump(new LandEndpoint()).dump();
			new WorldGenerator().generateAndPersistLand();		
		} 
	}
}
