package com.inzynierka.backend;

import javax.persistence.EntityManager;

import com.inzynierka.entities.map.Field;
import com.inzynierka.entities.map.Land;


public class WorldGenerator {
	
	
	
	public static synchronized void generateLand(){
		EntityManager entityManager = EntityManagerFactoryProvider.get().createEntityManager();
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
