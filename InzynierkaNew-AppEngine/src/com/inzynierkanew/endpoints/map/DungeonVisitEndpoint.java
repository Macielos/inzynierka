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
import com.inzynierkanew.entities.map.DungeonVisit;
import com.inzynierkanew.utils.EMF;

@Api(name = "dungeonvisitendpoint", namespace = @ApiNamespace(ownerDomain = "inzynierkanew.com", ownerName = "inzynierkanew.com", packagePath = "entities.map") )
public class DungeonVisitEndpoint {

	/**
	 * This method lists all the entities inserted in datastore.
	 * It uses HTTP GET method and paging support.
	 *
	 * @return A CollectionResponse class containing the list of all entities
	 * persisted and a cursor to the next page.
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	@ApiMethod(name = "listDungeonVisitsByIds")
	public CollectionResponse<DungeonVisit> listDungeonVisitsByIds( @Named("heroId") Long heroId, 
			@Named("relatedEntityId") Long relatedEntityId,
			@Named("byLand") Boolean byLand) {

		EntityManager mgr = null;
		Cursor cursor = null;
		List<DungeonVisit> execute = null;

		try {
			mgr = getEntityManager();
			String queryString = "select from DungeonVisit as DungeonVisit where DungeonVisit.heroId = "+heroId;
			if(byLand){
				queryString+=" and DungeonVisit.landId = "+relatedEntityId;
			} else {
				queryString+=" and DungeonVisit.dungeonId = "+relatedEntityId;
			} 
			Query query = mgr.createQuery(queryString);
			execute = (List<DungeonVisit>) query.getResultList();

			for (DungeonVisit obj : execute);
		} finally {
			mgr.close();
		}

		return CollectionResponse.<DungeonVisit> builder().setItems(execute).build();
	}

	/**
	 * This inserts a new entity into App Engine datastore. If the entity already
	 * exists in the datastore, an exception is thrown.
	 * It uses HTTP POST method.
	 *
	 * @param dungeonvisit the entity to be inserted.
	 * @return The inserted entity.
	 */
	@ApiMethod(name = "insertDungeonVisit")
	public DungeonVisit insertDungeonVisit(DungeonVisit dungeonvisit) {
		EntityManager mgr = getEntityManager();
		try {
			if (containsDungeonVisit(dungeonvisit)) {
				throw new EntityExistsException("Object already exists");
			}
			mgr.persist(dungeonvisit);
		} finally {
			mgr.close();
		}
		return dungeonvisit;
	}

	/**
	 * This method is used for updating an existing entity. If the entity does not
	 * exist in the datastore, an exception is thrown.
	 * It uses HTTP PUT method.
	 *
	 * @param dungeonvisit the entity to be updated.
	 * @return The updated entity.
	 */
	@ApiMethod(name = "updateDungeonVisit")
	public DungeonVisit updateDungeonVisit(DungeonVisit dungeonvisit) {
		EntityManager mgr = getEntityManager();
		try {
			if (!containsDungeonVisit(dungeonvisit)) {
				throw new EntityNotFoundException("Object does not exist");
			}
			mgr.persist(dungeonvisit);
		} finally {
			mgr.close();
		}
		return dungeonvisit;
	}

	/**
	 * This method removes the entity with primary key id.
	 * It uses HTTP DELETE method.
	 *
	 * @param id the primary key of the entity to be deleted.
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

	private boolean containsDungeonVisit(DungeonVisit dungeonvisit) {
		if(dungeonvisit == null || dungeonvisit.getId()==null){
			return false;
		}
		EntityManager mgr = getEntityManager();
		boolean contains = true;
		try {
			DungeonVisit item = mgr.find(DungeonVisit.class, dungeonvisit.getId());
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
