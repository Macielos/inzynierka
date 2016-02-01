package com.explorersguild.endpoints.map;

import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.explorersguild.entities.map.TownVisit;
import com.explorersguild.utils.EMF;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.datanucleus.query.JPACursorHelper;

@Api(name = "townvisitendpoint", namespace = @ApiNamespace(ownerDomain = "explorersguild.com", ownerName = "explorersguild.com", packagePath = "entities.map") )
public class TownVisitEndpoint {

	/**
	 * This method lists all the entities inserted in datastore.
	 * It uses HTTP GET method and paging support.
	 *
	 * @return A CollectionResponse class containing the list of all entities
	 * persisted and a cursor to the next page.
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	@ApiMethod(name = "listTownVisit")
	public CollectionResponse<TownVisit> listTownVisit(@Nullable @Named("cursor") String cursorString,
			@Nullable @Named("limit") Integer limit) {

		EntityManager mgr = null;
		Cursor cursor = null;
		List<TownVisit> execute = null;

		try {
			mgr = getEntityManager();
			Query query = mgr.createQuery("select from TownVisit as TownVisit");
			if (cursorString != null && cursorString != "") {
				cursor = Cursor.fromWebSafeString(cursorString);
				query.setHint(JPACursorHelper.CURSOR_HINT, cursor);
			}

			if (limit != null) {
				query.setFirstResult(0);
				query.setMaxResults(limit);
			}

			execute = (List<TownVisit>) query.getResultList();
			cursor = JPACursorHelper.getCursor(execute);
			if (cursor != null)
				cursorString = cursor.toWebSafeString();

			// Tight loop for fetching all entities from datastore and accomodate
			// for lazy fetch.
			for (TownVisit obj : execute)
				;
		} finally {
			mgr.close();
		}

		return CollectionResponse.<TownVisit> builder().setItems(execute).setNextPageToken(cursorString).build();
	}

	/**
	 * This method gets the entity having primary key id. It uses HTTP GET method.
	 *
	 * @param id the primary key of the java bean.
	 * @return The entity with primary key id.
	 */
	@ApiMethod(name = "getTownVisit")
	public TownVisit getTownVisit(@Named("townId") Long townId, @Named("heroId") Long heroId) {
		EntityManager mgr = getEntityManager();
		TownVisit townvisit = null;
		try {
			List<TownVisit> results = mgr.createQuery("select from TownVisit as TownVisit where TownVisit.townId = "+townId+" and TownVisit.heroId = "+heroId).getResultList();
			if(!results.isEmpty()){
				townvisit = results.get(0);
			}
		} finally {
			mgr.close();
		}
		return townvisit;
	}

	/**
	 * This inserts a new entity into App Engine datastore. If the entity already
	 * exists in the datastore, an exception is thrown.
	 * It uses HTTP POST method.
	 *
	 * @param townvisit the entity to be inserted.
	 * @return The inserted entity.
	 */
	@ApiMethod(name = "saveTownVisit")
	public TownVisit saveTownVisit(TownVisit townvisit) {
		EntityManager mgr = null;
		TownVisit newTownVisit;
		try {
			mgr = getEntityManager();
			List<TownVisit> townVisits = mgr
					.createQuery("select from TownVisit as TownVisit where TownVisit.heroId = "
							+ townvisit.getHeroId() + " and TownVisit.townId = " + townvisit.getTownId())
					.getResultList();
			if (townVisits.isEmpty()) {
				newTownVisit = townvisit;
			} else {
				newTownVisit = townVisits.get(0);
				newTownVisit.setArmy(townvisit.getArmy());
			}
			mgr.merge(newTownVisit);
		} finally {
			mgr.close();
		}
		return newTownVisit;
	}

	/**
	 * This method removes the entity with primary key id.
	 * It uses HTTP DELETE method.
	 *
	 * @param id the primary key of the entity to be deleted.
	 */
	@ApiMethod(name = "removeTownVisit")
	public void removeTownVisit(@Named("id") Long id) {
		EntityManager mgr = getEntityManager();
		try {
			TownVisit townvisit = mgr.find(TownVisit.class, id);
			mgr.remove(townvisit);
		} finally {
			mgr.close();
		}
	}

	private static EntityManager getEntityManager() {
		return EMF.get().createEntityManager();
	}

}
