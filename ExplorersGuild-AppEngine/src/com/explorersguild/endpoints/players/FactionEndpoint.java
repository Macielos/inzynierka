package com.explorersguild.endpoints.players;

import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Named;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.explorersguild.entities.players.Faction;
import com.explorersguild.utils.EMF;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.datanucleus.query.JPACursorHelper;

@Api(name = "factionendpoint", namespace = @ApiNamespace(ownerDomain = "explorersguild.com", ownerName = "explorersguild.com", packagePath = "entities.players"))
public class FactionEndpoint {

	/**
	 * This method lists all the entities inserted in datastore.
	 * It uses HTTP GET method and paging support.
	 *
	 * @return A CollectionResponse class containing the list of all entities
	 * persisted and a cursor to the next page.
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	@ApiMethod(name = "listFaction")
	public CollectionResponse<Faction> listFaction(@Nullable @Named("cursor") String cursorString, @Nullable @Named("limit") Integer limit) {

		EntityManager mgr = null;
		Cursor cursor = null;
		List<Faction> execute = null;

		try {
			mgr = getEntityManager();
			Query query = mgr.createQuery("select from Faction as Faction");
			if (cursorString != null && cursorString != "") {
				cursor = Cursor.fromWebSafeString(cursorString);
				query.setHint(JPACursorHelper.CURSOR_HINT, cursor);
			}

			if (limit != null) {
				query.setFirstResult(0);
				query.setMaxResults(limit);
			}

			execute = (List<Faction>) query.getResultList();
			cursor = JPACursorHelper.getCursor(execute);
			if (cursor != null)
				cursorString = cursor.toWebSafeString();

			// Tight loop for fetching all entities from datastore and accomodate
			// for lazy fetch.
			for (Faction obj : execute)
				;
		} finally {
			mgr.close();
		}

		return CollectionResponse.<Faction> builder().setItems(execute).setNextPageToken(cursorString).build();
	}
	
	@SuppressWarnings({ "unchecked", "unused" })
	@ApiMethod(name = "getFactionsForTowns", path = "collectionresponse_faction_towns")
	public CollectionResponse<Faction> getFactionsForTowns(@Nullable @Named("cursor") String cursorString, @Nullable @Named("limit") Integer limit) {

		EntityManager mgr = null;
		Cursor cursor = null;
		List<Faction> execute = null;

		try {
			mgr = getEntityManager();
			Query query = mgr.createQuery("select from Faction as Faction where appearsInTowns = true");
			if (cursorString != null && cursorString != "") {
				cursor = Cursor.fromWebSafeString(cursorString);
				query.setHint(JPACursorHelper.CURSOR_HINT, cursor);
			}

			if (limit != null) {
				query.setFirstResult(0);
				query.setMaxResults(limit);
			}

			execute = (List<Faction>) query.getResultList();
			cursor = JPACursorHelper.getCursor(execute);
			if (cursor != null)
				cursorString = cursor.toWebSafeString();

			// Tight loop for fetching all entities from datastore and accomodate
			// for lazy fetch.
			for (Faction obj : execute)
				;
		} finally {
			mgr.close();
		}

		return CollectionResponse.<Faction> builder().setItems(execute).setNextPageToken(cursorString).build();
	}
	
	@SuppressWarnings({ "unchecked", "unused" })
	@ApiMethod(name = "getFactionsForDungeons", path = "collectionresponse_faction_dungeons")
	public CollectionResponse<Faction> getFactionsForDungeons(@Nullable @Named("cursor") String cursorString, @Nullable @Named("limit") Integer limit) {

		EntityManager mgr = null;
		Cursor cursor = null;
		List<Faction> execute = null;

		try {
			mgr = getEntityManager();
			Query query = mgr.createQuery("select from Faction as Faction where appearsInDungeons = true");
			if (cursorString != null && cursorString != "") {
				cursor = Cursor.fromWebSafeString(cursorString);
				query.setHint(JPACursorHelper.CURSOR_HINT, cursor);
			}

			if (limit != null) {
				query.setFirstResult(0);
				query.setMaxResults(limit);
			}

			execute = (List<Faction>) query.getResultList();
			cursor = JPACursorHelper.getCursor(execute);
			if (cursor != null)
				cursorString = cursor.toWebSafeString();

			// Tight loop for fetching all entities from datastore and accomodate
			// for lazy fetch.
			for (Faction obj : execute)
				;
		} finally {
			mgr.close();
		}

		return CollectionResponse.<Faction> builder().setItems(execute).setNextPageToken(cursorString).build();
	}

	/**
	 * This method gets the entity having primary key id. It uses HTTP GET method.
	 *
	 * @param id the primary key of the java bean.
	 * @return The entity with primary key id.
	 */
	@ApiMethod(name = "getFaction")
	public Faction getFaction(@Named("id") Long id) {
		EntityManager mgr = getEntityManager();
		Faction faction = null;
		try {
			faction = mgr.find(Faction.class, id);
		} finally {
			mgr.close();
		}
		return faction;
	}

	/**
	 * This inserts a new entity into App Engine datastore. If the entity already
	 * exists in the datastore, an exception is thrown.
	 * It uses HTTP POST method.
	 *
	 * @param faction the entity to be inserted.
	 * @return The inserted entity.
	 */
	@ApiMethod(name = "insertFaction")
	public Faction insertFaction(Faction faction) {
		EntityManager mgr = getEntityManager();
		try {
			if (containsFaction(faction)) {
				throw new EntityExistsException("Object already exists");
			}
			mgr.persist(faction);
		} finally {
			mgr.close();
		}
		return faction;
	}

	private boolean containsFaction(Faction faction) {
		EntityManager mgr = getEntityManager();
		boolean contains = true;
		try {
			Faction item = mgr.find(Faction.class, faction.getId());
			if (item == null) {
				contains = false;
			}
		} finally {
			mgr.close();
		}
		return contains;
	}

	private static EntityManager getEntityManager() {
		return EMF.get().createEntityManager();
	}

}
