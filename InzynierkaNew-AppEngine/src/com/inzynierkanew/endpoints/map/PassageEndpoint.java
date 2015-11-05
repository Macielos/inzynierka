package com.inzynierkanew.endpoints.map;

import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Named;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.datanucleus.query.JPACursorHelper;
import com.inzynierkanew.entities.map.Passage;
import com.inzynierkanew.utils.EMF;

@Api(name = "passageendpoint", namespace = @ApiNamespace(ownerDomain = "inzynierkanew.com", ownerName = "inzynierkanew.com", packagePath = "entities.map"))
public class PassageEndpoint {

	/**
	 * This method lists all the entities inserted in datastore.
	 * It uses HTTP GET method and paging support.
	 *
	 * @return A CollectionResponse class containing the list of all entities
	 * persisted and a cursor to the next page.
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	@ApiMethod(name = "listPassage")
	public CollectionResponse<Passage> listPassage(@Nullable @Named("cursor") String cursorString, @Nullable @Named("limit") Integer limit) {

		EntityManager mgr = null;
		Cursor cursor = null;
		List<Passage> execute = null;

		try {
			mgr = getEntityManager();
			Query query = mgr.createQuery("select from Passage as Passage");
			if (cursorString != null && cursorString != "") {
				cursor = Cursor.fromWebSafeString(cursorString);
				query.setHint(JPACursorHelper.CURSOR_HINT, cursor);
			}

			if (limit != null) {
				query.setFirstResult(0);
				query.setMaxResults(limit);
			}

			execute = (List<Passage>) query.getResultList();
			cursor = JPACursorHelper.getCursor(execute);
			if (cursor != null)
				cursorString = cursor.toWebSafeString();

			// Tight loop for fetching all entities from datastore and accomodate
			// for lazy fetch.
			for (Passage obj : execute)
				;
		} finally {
			mgr.close();
		}

		return CollectionResponse.<Passage> builder().setItems(execute).setNextPageToken(cursorString).build();
	}

	/**
	 * This method gets the entity having primary key id. It uses HTTP GET method.
	 *
	 * @param id the primary key of the java bean.
	 * @return The entity with primary key id.
	 */
	@ApiMethod(name = "getPassage")
	public Passage getPassage(@Named("id") Long id) {
		EntityManager mgr = getEntityManager();
		Passage passage = null;
		try {
			passage = mgr.find(Passage.class, id);
		} finally {
			mgr.close();
		}
		return passage;
	}

	/**
	 * This inserts a new entity into App Engine datastore. If the entity already
	 * exists in the datastore, an exception is thrown.
	 * It uses HTTP POST method.
	 *
	 * @param passage the entity to be inserted.
	 * @return The inserted entity.
	 */
	@ApiMethod(name = "insertPassage")
	public Passage insertPassage(Passage passage) {
		EntityManager mgr = getEntityManager();
		try {
			if (containsPassage(passage)) {
				throw new EntityExistsException("Object already exists");
			}
			mgr.persist(passage);
		} finally {
			mgr.close();
		}
		return passage;
	}

	/**
	 * This method is used for updating an existing entity. If the entity does not
	 * exist in the datastore, an exception is thrown.
	 * It uses HTTP PUT method.
	 *
	 * @param passage the entity to be updated.
	 * @return The updated entity.
	 */
	@ApiMethod(name = "updatePassage")
	public Passage updatePassage(Passage passage) {
		EntityManager mgr = getEntityManager();
		try {
			if (!containsPassage(passage)) {
				throw new EntityNotFoundException("Object does not exist");
			}
			mgr.persist(passage);
		} finally {
			mgr.close();
		}
		return passage;
	}

	/**
	 * This method removes the entity with primary key id.
	 * It uses HTTP DELETE method.
	 *
	 * @param id the primary key of the entity to be deleted.
	 */
	@ApiMethod(name = "removePassage")
	public void removePassage(@Named("id") Long id) {
		EntityManager mgr = getEntityManager();
		try {
			Passage passage = mgr.find(Passage.class, id);
			mgr.remove(passage);
		} finally {
			mgr.close();
		}
	}

	private boolean containsPassage(Passage passage) {
		EntityManager mgr = getEntityManager();
		boolean contains = true;
		try {
			Passage item = mgr.find(Passage.class, passage.getKey());
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
