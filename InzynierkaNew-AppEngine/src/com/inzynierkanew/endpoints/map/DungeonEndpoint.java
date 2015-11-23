package com.inzynierkanew.endpoints.map;

import com.inzynierkanew.entities.map.Dungeon;
import com.inzynierkanew.utils.EMF;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.datanucleus.query.JPACursorHelper;

import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Named;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.persistence.EntityManager;
import javax.persistence.Query;

@Api(name = "dungeonendpoint", namespace = @ApiNamespace(ownerDomain = "inzynierkanew.com", ownerName = "inzynierkanew.com", packagePath = "entities.map") )
public class DungeonEndpoint {

	/**
	 * This method lists all the entities inserted in datastore.
	 * It uses HTTP GET method and paging support.
	 *
	 * @return A CollectionResponse class containing the list of all entities
	 * persisted and a cursor to the next page.
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	@ApiMethod(name = "listDungeon", path = "listDungeon/{landId}")
	public CollectionResponse<Dungeon> listDungeon(@Nullable @Named("cursor") String cursorString,
			@Nullable @Named("limit") Integer limit, @Named("landId") Long landId) {

		EntityManager mgr = null;
		Cursor cursor = null;
		List<Dungeon> execute = null;

		try {
			mgr = getEntityManager();
			String queryString = "select from Dungeon as Dungeon" + (landId == null ? "" : " where Dungeon.landId = "+landId);
			Query query = mgr.createQuery(queryString);
			if (cursorString != null && cursorString != "") {
				cursor = Cursor.fromWebSafeString(cursorString);
				query.setHint(JPACursorHelper.CURSOR_HINT, cursor);
			}

			if (limit != null) {
				query.setFirstResult(0);
				query.setMaxResults(limit);
			}

			execute = (List<Dungeon>) query.getResultList();
			cursor = JPACursorHelper.getCursor(execute);
			if (cursor != null)
				cursorString = cursor.toWebSafeString();

			// Tight loop for fetching all entities from datastore and accomodate
			// for lazy fetch.
			for (Dungeon obj : execute)
				;
		} finally {
			mgr.close();
		}

		return CollectionResponse.<Dungeon> builder().setItems(execute).setNextPageToken(cursorString).build();
	}

	/**
	 * This method gets the entity having primary key id. It uses HTTP GET method.
	 *
	 * @param id the primary key of the java bean.
	 * @return The entity with primary key id.
	 */
	@ApiMethod(name = "getDungeon", path = "getDungeon/{id}")
	public Dungeon getDungeon(@Named("id") Long id) {
		EntityManager mgr = getEntityManager();
		Dungeon dungeon = null;
		try {
			dungeon = mgr.find(Dungeon.class, id);
		} finally {
			mgr.close();
		}
		return dungeon;
	}

	/**
	 * This inserts a new entity into App Engine datastore. If the entity already
	 * exists in the datastore, an exception is thrown.
	 * It uses HTTP POST method.
	 *
	 * @param dungeon the entity to be inserted.
	 * @return The inserted entity.
	 */
	@ApiMethod(name = "insertDungeon")
	public Dungeon insertDungeon(Dungeon dungeon) {
		EntityManager mgr = getEntityManager();
		try {
			if (containsDungeon(dungeon)) {
				throw new EntityExistsException("Object already exists");
			}
			mgr.persist(dungeon);
		} finally {
			mgr.close();
		}
		return dungeon;
	}

	/**
	 * This method is used for updating an existing entity. If the entity does not
	 * exist in the datastore, an exception is thrown.
	 * It uses HTTP PUT method.
	 *
	 * @param dungeon the entity to be updated.
	 * @return The updated entity.
	 */
	@ApiMethod(name = "updateDungeon")
	public Dungeon updateDungeon(Dungeon dungeon) {
		EntityManager mgr = getEntityManager();
		try {
			if (!containsDungeon(dungeon)) {
				throw new EntityNotFoundException("Object does not exist");
			}
			mgr.persist(dungeon);
		} finally {
			mgr.close();
		}
		return dungeon;
	}

	/**
	 * This method removes the entity with primary key id.
	 * It uses HTTP DELETE method.
	 *
	 * @param id the primary key of the entity to be deleted.
	 */
	@ApiMethod(name = "removeDungeon")
	public void removeDungeon(@Named("id") Long id) {
		EntityManager mgr = getEntityManager();
		try {
			Dungeon dungeon = mgr.find(Dungeon.class, id);
			mgr.remove(dungeon);
		} finally {
			mgr.close();
		}
	}

	private boolean containsDungeon(Dungeon dungeon) {
		EntityManager mgr = getEntityManager();
		boolean contains = true;
		try {
			Dungeon item = mgr.find(Dungeon.class, dungeon.getId());
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
