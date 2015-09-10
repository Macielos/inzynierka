package com.inzynierka.endpoints.map;

import javax.inject.Named;
import javax.persistence.EntityManager;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.inzynierka.backend.EntityManagerFactoryProvider;
import com.inzynierka.entities.map.Land;

@Api(name = "landendpoint", namespace = @ApiNamespace(ownerDomain = "inzynierka.com", ownerName = "inzynierka.com", packagePath = "entities.map"))
public class LandEndpoint {

	/**
	 * This method gets the entity having primary key id. It uses HTTP GET method.
	 *
	 * @param id the primary key of the java bean.
	 * @return The entity with primary key id.
	 */
	@ApiMethod(name = "getLand")
	public Land getLand(@Named("id") Long id) {
		EntityManager mgr = getEntityManager();
		Land land = null;
		try {
			land = mgr.find(Land.class, id);
		} finally {
			mgr.close();
		}
		return land;
	}

	private static EntityManager getEntityManager() {
		return EntityManagerFactoryProvider.get().createEntityManager();
	}

}
