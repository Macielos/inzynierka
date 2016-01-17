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
import com.inzynierkanew.entities.map.Town;
import com.inzynierkanew.utils.EMF;

@Api(name = "townendpoint", namespace = @ApiNamespace(ownerDomain = "inzynierkanew.com", ownerName = "inzynierkanew.com", packagePath = "entities.map"))
public class TownEndpoint {

	/**
	 * This method lists all the entities inserted in datastore.
	 * It uses HTTP GET method and paging support.
	 *
	 * @return A CollectionResponse class containing the list of all entities
	 * persisted and a cursor to the next page.
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	@ApiMethod(name = "listTown")
	public CollectionResponse<Town> listTown(@Nullable @Named("cursor") String cursorString, @Nullable @Named("limit") Integer limit) {

		EntityManager mgr = null;
		Cursor cursor = null;
		List<Town> execute = null;

		try {
			mgr = getEntityManager();
			Query query = mgr.createQuery("select from Town as Town");
			if (cursorString != null && cursorString != "") {
				cursor = Cursor.fromWebSafeString(cursorString);
				query.setHint(JPACursorHelper.CURSOR_HINT, cursor);
			}

			if (limit != null) {
				query.setFirstResult(0);
				query.setMaxResults(limit);
			}

			execute = (List<Town>) query.getResultList();
			cursor = JPACursorHelper.getCursor(execute);
			if (cursor != null)
				cursorString = cursor.toWebSafeString();

			// Tight loop for fetching all entities from datastore and accomodate
			// for lazy fetch.
			for (Town obj : execute)
				;
		} finally {
			mgr.close();
		}

		return CollectionResponse.<Town> builder().setItems(execute).setNextPageToken(cursorString).build();
	}

	/**
	 * This method gets the entity having primary key id. It uses HTTP GET method.
	 *
	 * @param id the primary key of the java bean.
	 * @return The entity with primary key id.
	 */
	@ApiMethod(name = "getTown")
	public Town getTown(@Named("id") Long id) {
		EntityManager mgr = getEntityManager();
		Town town = null;
		try {
			
//			for(Town townFromList: listTown(null, null).getItems()){
//				if(townFromList.getKey().getId()==id){
//					town = townFromList;
//					break;
//				}
//			}
	
//			Key key = KeyFactory.createKey(Town.class.getSimpleName(), id);
			
			town = mgr.find(Town.class, id);
		} finally {
			mgr.close();
		}
		return town;
	}
	
	/**
	 * This inserts a new entity into App Engine datastore. If the entity already
	 * exists in the datastore, an exception is thrown.
	 * It uses HTTP POST method.
	 *
	 * @param town the entity to be inserted.
	 * @return The inserted entity.
	 */
	@ApiMethod(name = "insertTown")
	public Town insertTown(Town town) {
		EntityManager mgr = getEntityManager();
		try {
			if (containsTown(town)) {
				throw new EntityExistsException("Object already exists");
			}
			mgr.persist(town);
		} finally {
			mgr.close();
		}
		return town;
	}

	/**
	 * This method is used for updating an existing entity. If the entity does not
	 * exist in the datastore, an exception is thrown.
	 * It uses HTTP PUT method.
	 *
	 * @param town the entity to be updated.
	 * @return The updated entity.
	 */
	@ApiMethod(name = "updateTown")
	public Town updateTown(Town town) {
		EntityManager mgr = getEntityManager();
		try {
			if (!containsTown(town)) {
				throw new EntityNotFoundException("Object does not exist");
			}
			mgr.persist(town);
		} finally {
			mgr.close();
		}
		return town;
	}

	/**
	 * This method removes the entity with primary key id.
	 * It uses HTTP DELETE method.
	 *
	 * @param id the primary key of the entity to be deleted.
	 */
	@ApiMethod(name = "removeTown")
	public void removeTown(@Named("id") Long id) {
		EntityManager mgr = getEntityManager();
		try {
			Town town = mgr.find(Town.class, id);
			mgr.remove(town);
		} finally {
			mgr.close();
		}
	}

	private boolean containsTown(Town town) {
		if(town==null || town.getId()==null){
			return false;
		}
		EntityManager mgr = getEntityManager();
		boolean contains = true;
		try {
			Town item = mgr.find(Town.class, town.getId());
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
