package com.inzynierkanew.endpoints.map;

import java.beans.Expression;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Named;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.datanucleus.query.JPACursorHelper;
import com.inzynierkanew.entities.map.Dungeon;
import com.inzynierkanew.entities.map.Land;
import com.inzynierkanew.entities.map.Passage;
import com.inzynierkanew.utils.EMF;

@Api(name = "landendpoint", namespace = @ApiNamespace(ownerDomain = "inzynierkanew.com", ownerName = "inzynierkanew.com", packagePath = "entities.map"))
public class LandEndpoint {

	/**
	 * This method lists all the entities inserted in datastore.
	 * It uses HTTP GET method and paging support.
	 *
	 * @return A CollectionResponse class containing the list of all entities
	 * persisted and a cursor to the next page.
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	@ApiMethod(name = "listLand")
	public CollectionResponse<Land> listLand(@Nullable @Named("cursor") String cursorString, @Nullable @Named("limit") Integer limit) {

		EntityManager mgr = null;
		Cursor cursor = null;
		List<Land> execute = null;

		try {
			mgr = getEntityManager();
			Query query = mgr.createQuery("select from Land as Land");
			if (cursorString != null && cursorString != "") {
				cursor = Cursor.fromWebSafeString(cursorString);
				query.setHint(JPACursorHelper.CURSOR_HINT, cursor);
			}

			if (limit != null) {
				query.setFirstResult(0);
				query.setMaxResults(limit);
			}

			execute = (List<Land>) query.getResultList();
			cursor = JPACursorHelper.getCursor(execute);
			if (cursor != null)
				cursorString = cursor.toWebSafeString();

			// Tight loop for fetching all entities from datastore and accomodate
			// for lazy fetch.
			for (Land obj : execute);
		} finally {
			mgr.close();
		}

		return CollectionResponse.<Land> builder().setItems(execute).setNextPageToken(cursorString).build();
	}

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
			land.getTown();
			land.getFields();
			for (Dungeon obj : land.getDungeons());
			for (Passage obj : land.getPassages());
		} finally {
			mgr.close();
		}
		return land;
	}
	
	@SuppressWarnings({ "unchecked", "unused" })
	@ApiMethod(name = "findNeighbours")
	public CollectionResponse<Land> findNeighbours(@Named("cursor") long landId) {
		EntityManager mgr = null;
		
		Land land = getLand(landId);
		if(land==null){
			return null;
		}
		List<Long> passageIds = new ArrayList<>(land.getPassages().size());
		for(Passage passage: land.getPassages()){
			passageIds.add(passage.getNextLandId());
		}
		
		List<Land> neighbours = null;
		try {
			mgr = getEntityManager();
			CriteriaBuilder builder = mgr.getCriteriaBuilder();
			CriteriaQuery criteriaQuery = builder.createQuery(Land.class);
			criteriaQuery.select(criteriaQuery.from(Land.class));
			neighbours = mgr.createQuery(criteriaQuery).getResultList();
			for (Land obj : neighbours);
		} finally {
			mgr.close();
		}
		return CollectionResponse.<Land> builder().setItems(neighbours)./*setNextPageToken(cursorString).*/build();
	}
	
	/**
	 * This inserts a new entity into App Engine datastore. If the entity already
	 * exists in the datastore, an exception is thrown.
	 * It uses HTTP POST method.
	 *
	 * @param land the entity to be inserted.
	 * @return The inserted entity.
	 */
	@ApiMethod(name = "insertLand")
	public Land insertLand(Land land) {
		EntityManager mgr = getEntityManager();
		try {
			if (containsLand(land)) {
				throw new EntityExistsException("Object already exists");
			}
			mgr.persist(land);
		} finally {
			mgr.close();
		}
		return land;
	}

	/**
	 * This method is used for updating an existing entity. If the entity does not
	 * exist in the datastore, an exception is thrown.
	 * It uses HTTP PUT method.
	 *
	 * @param land the entity to be updated.
	 * @return The updated entity.
	 */
	@ApiMethod(name = "updateLand")
	public Land updateLand(Land land) {
		EntityManager mgr = getEntityManager();
		try {
			if (!containsLand(land)) {
				throw new EntityNotFoundException("Object does not exist");
			}
			mgr.persist(land);
		} finally {
			mgr.close();
		}
		return land;
	}

	/**
	 * This method removes the entity with primary key id.
	 * It uses HTTP DELETE method.
	 *
	 * @param id the primary key of the entity to be deleted.
	 */
	@ApiMethod(name = "removeLand")
	public void removeLand(@Named("id") Long id) {
		EntityManager mgr = getEntityManager();
		try {
			Land land = mgr.find(Land.class, id);
			mgr.remove(land);
		} finally {
			mgr.close();
		}
	}

	private boolean containsLand(Land land) {
		EntityManager mgr = getEntityManager();
		boolean contains = true;
		try {
			Land item = mgr.find(Land.class, land.getId());
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
