package com.explorersguild.endpoints.map;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;
import javax.inject.Named;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;

import com.explorersguild.entities.map.Land;
import com.explorersguild.utils.EMF;
import com.explorersguild.world.WorldGenerator;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.datanucleus.query.JPACursorHelper;

@Api(name = "landendpoint", namespace = @ApiNamespace(ownerDomain = "explorersguild.com", ownerName = "explorersguild.com", packagePath = "entities.map") )
public class LandEndpoint {

	/**
	 * This method lists all the entities inserted in datastore. It uses HTTP
	 * GET method and paging support.
	 *
	 * @return A CollectionResponse class containing the list of all entities
	 *         persisted and a cursor to the next page.
	 */
	@SuppressWarnings({ "unchecked" })
	@ApiMethod(name = "listLand")
	public CollectionResponse<Land> listLand(@Nullable @Named("cursor") String cursorString,
			@Nullable @Named("limit") Integer limit) {

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

		} finally {
			mgr.close();
		}

		return CollectionResponse.<Land> builder().setItems(execute).setNextPageToken(cursorString).build();
	}

	/**
	 * This method gets the entity having primary key id. It uses HTTP GET
	 * method.
	 *
	 * @param id
	 *            the primary key of the java bean.
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

	public CollectionResponse<Land> findLandsInNeighbourhood(@Named("mapSegment") long mapSegment) {
		EntityManager mgr = null;
		List<Land> lands = new ArrayList<>();
		try {
			mgr = getEntityManager();
			for (long l : getNeighbourMapSegments(mapSegment)) {
				lands.addAll(findLandsInTheNeighbourhoodInternal(mgr, l));
			}
			return CollectionResponse.<Land> builder().setItems(lands).build();
		} finally {
			mgr.close();
		}
	}

	private Collection<Land> findLandsInTheNeighbourhoodInternal(EntityManager mgr, long mapSegment) {
		return mgr.createQuery("select from Land as Land where Land.mapSegment = " + mapSegment).getResultList();
	}

	public Land findLandForNewPlayer() {
		EntityManager entityManager = null;
		try {
			entityManager = EMF.get().createEntityManager();
			Random random = new Random();
			List<Land> lands = entityManager
					.createQuery("select from Land as Land where Land.hasTown = true and Land.suggestedLevel = 1")
					.getResultList();
			if (!lands.isEmpty()) {
				return lands.get(random.nextInt(lands.size()));
			}

			lands = entityManager
					.createQuery("select from Land as Land where Land.hasTown = true order by Land.suggestedLevel")
					.setMaxResults(5).getResultList();
			return lands.isEmpty() ? null : lands.get(random.nextInt(lands.size()));
		} finally {
			entityManager.close();
		}
	}

	private long[] getNeighbourMapSegments(long mapSegment) {
		return new long[] { mapSegment, mapSegment + 1, mapSegment + WorldGenerator.MAP_SEGMENT_FACTOR,
				mapSegment + WorldGenerator.MAP_SEGMENT_FACTOR + 1 };
	}

	public CollectionResponse<Land> findLandsWithFreePassages() {
		EntityManager mgr = null;
		String queryString = "select from Land as Land where Land.hasFreePassage = true";
		try {
			mgr = getEntityManager();
			Query query = mgr.createQuery(queryString);
			List<Land> lands = (List<Land>) query.getResultList();
			return CollectionResponse.<Land> builder().setItems(lands).build();
		} finally {
			mgr.close();
		}
	}

	/**
	 * This inserts a new entity into App Engine datastore. If the entity
	 * already exists in the datastore, an exception is thrown. It uses HTTP
	 * POST method.
	 *
	 * @param land
	 *            the entity to be inserted.
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
	 * This method is used for updating an existing entity. If the entity does
	 * not exist in the datastore, an exception is thrown. It uses HTTP PUT
	 * method.
	 *
	 * @param land
	 *            the entity to be updated.
	 * @return The updated entity.
	 */
	@ApiMethod(name = "updateLand")
	public Land updateLand(Land land) {
		EntityManager mgr = getEntityManager();
		try {
			if (!containsLand(land)) {
				throw new EntityNotFoundException("Object does not exist");
			}
			mgr.merge(land);
		} finally {
			mgr.close();
		}
		return land;
	}

	/**
	 * This method removes the entity with primary key id. It uses HTTP DELETE
	 * method.
	 *
	 * @param id
	 *            the primary key of the entity to be deleted.
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
		if (land.getId() == null) {
			return false;
		}
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
