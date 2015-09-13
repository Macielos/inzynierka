package com.inzynierkanew.backend;

import javax.persistence.EntityManager;

import com.inzynierkanew.EMF;
import com.inzynierkanew.entities.map.Field;
import com.inzynierkanew.entities.map.Land;


public class WorldGenerator {
	
	public static synchronized void fireWorldUpdate(){
		generateLand();
	}

	private static void generateLand(){
		EntityManager entityManager = EMF.get().createEntityManager();
		Land land = new Land(new Field[]{
				new Field(0, 0, 1), 
				new Field(0, 1, 1),
				new Field(1, 0, 1),
				new Field(1, 1, 2),
		});
		try {
			entityManager.persist(land);
		} finally {
			entityManager.close();
		}
	}

}
