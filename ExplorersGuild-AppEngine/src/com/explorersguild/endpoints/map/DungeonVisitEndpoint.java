package com.explorersguild.endpoints.map;

import java.util.List;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.explorersguild.entities.map.DungeonVisit;
import com.explorersguild.utils.EMF;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.appengine.api.datastore.Cursor;

@Api(name = "dungeonvisitendpoint", namespace = @ApiNamespace(ownerDomain = "explorersguild.com", ownerName = "explorersguild.com", packagePath = "entities.map") )
public class DungeonVisitEndpoint {

	@SuppressWarnings({ "unchecked", "unused" })
	@ApiMethod(name = "listDungeonVisitsByIds")
	public CollectionResponse<DungeonVisit> listDungeonVisitsByIds(@Named("heroId") Long heroId,
			@Named("relatedEntityId") Long relatedEntityId, @Named("byLand") Boolean byLand) {

		EntityManager mgr = null;
		Cursor cursor = null;
		List<DungeonVisit> execute = null;

		try {
			mgr = getEntityManager();
			String queryString = "select from DungeonVisit as DungeonVisit where DungeonVisit.heroId = " + heroId;
			if (byLand) {
				queryString += " and DungeonVisit.landId = " + relatedEntityId;
			} else {
				queryString += " and DungeonVisit.dungeonId = " + relatedEntityId;
			}
			Query query = mgr.createQuery(queryString);
			execute = (List<DungeonVisit>) query.getResultList();

			for (DungeonVisit obj : execute)
				;
		} finally {
			mgr.close();
		}

		return CollectionResponse.<DungeonVisit> builder().setItems(execute).build();
	}

	/**
	 * This inserts a new entity into App Engine datastore. If the entity
	 * already exists in the datastore, an exception is thrown. It uses HTTP
	 * POST method.
	 *
	 * @param dungeonvisit
	 *            the entity to be inserted.
	 * @return The inserted entity.
	 */
	@ApiMethod(name = "saveDungeonVisit")
	public DungeonVisit saveDungeonVisit(DungeonVisit dungeonvisit) {
		EntityManager mgr = null;
		DungeonVisit newDungeonVisit;
		try {
			mgr = getEntityManager();
			List<DungeonVisit> dungeonVisits = mgr
					.createQuery("select from DungeonVisit as DungeonVisit where DungeonVisit.heroId = "
							+ dungeonvisit.getHeroId() + " and DungeonVisit.dungeonId = " + dungeonvisit.getDungeonId())
					.getResultList();
			if (dungeonVisits.isEmpty()) {
				newDungeonVisit = dungeonvisit;
			} else {
				newDungeonVisit = dungeonVisits.get(0);
				newDungeonVisit.setArmy(dungeonvisit.getArmy());
			}
			mgr.merge(newDungeonVisit);
		} finally {
			mgr.close();
		}
		return newDungeonVisit;
	}

	/**
	 * This method removes the entity with primary key id. It uses HTTP DELETE
	 * method.
	 *
	 * @param id
	 *            the primary key of the entity to be deleted.
	 */
	@ApiMethod(name = "removeDungeonVisit")
	public void removeDungeonVisit(@Named("id") Long id) {
		EntityManager mgr = getEntityManager();
		try {
			DungeonVisit dungeonvisit = mgr.find(DungeonVisit.class, id);
			mgr.remove(dungeonvisit);
		} finally {
			mgr.close();
		}
	}

	private static EntityManager getEntityManager() {
		return EMF.get().createEntityManager();
	}

}
