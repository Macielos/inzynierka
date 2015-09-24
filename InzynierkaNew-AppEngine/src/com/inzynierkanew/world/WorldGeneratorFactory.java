package com.inzynierkanew.world;

import java.util.List;

import javax.persistence.EntityManager;

import com.inzynierkanew.entities.map.Land;
import com.inzynierkanew.utils.EMF;


public class WorldGeneratorFactory {
	
	public static synchronized void fireWorldGeneration(){
		new WorldGenerator().generateLand();
	}
	
	public static synchronized Land findLandForNewPlayer(){
		EntityManager entityManager = EMF.get().createEntityManager();
		List<Land> lands = entityManager.createQuery("select from Land as Land").setMaxResults(1).getResultList();
		return lands.isEmpty() ? null : lands.get(0);
	}
	
}
