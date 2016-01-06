package com.inzynierkanew.endpoints.players;

import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Named;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.datanucleus.store.appengine.query.JPACursorHelper;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.appengine.api.datastore.Cursor;
import com.inzynierkanew.entities.players.UnitType;
import com.inzynierkanew.utils.EMF;

@Api(name = "unittypeendpoint", namespace = @ApiNamespace(ownerDomain = "inzynierkanew.com", ownerName = "inzynierkanew.com", packagePath = "entities.players"))
public class UnitTypeEndpoint {

	/**
	 * This method lists all the entities inserted in datastore.
	 * It uses HTTP GET method and paging support.
	 *
	 * @return A CollectionResponse class containing the list of all entities
	 * persisted and a cursor to the next page.
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	@ApiMethod(name = "listUnitType")
	public CollectionResponse<UnitType> listUnitType(@Nullable @Named("cursor") String cursorString, @Nullable @Named("limit") Integer limit) {

		EntityManager mgr = null;
		Cursor cursor = null;
		List<UnitType> execute = null;

		try {
			mgr = getEntityManager();
			Query query = mgr.createQuery("select from UnitType as UnitType");
			if (cursorString != null && cursorString != "") {
				cursor = Cursor.fromWebSafeString(cursorString);
				query.setHint(JPACursorHelper.CURSOR_HINT, cursor);
			}

			if (limit != null) {
				query.setFirstResult(0);
				query.setMaxResults(limit);
			}

			execute = (List<UnitType>) query.getResultList();
			cursor = JPACursorHelper.getCursor(execute);
			if (cursor != null)
				cursorString = cursor.toWebSafeString();

			// Tight loop for fetching all entities from datastore and accomodate
			// for lazy fetch.
			for (UnitType obj : execute);
		} finally {
			mgr.close();
		}

		return CollectionResponse.<UnitType> builder().setItems(execute).setNextPageToken(cursorString).build();
	}

	/**
	 * This method gets the entity having primary key id. It uses HTTP GET method.
	 *
	 * @param id the primary key of the java bean.
	 * @return The entity with primary key id.
	 */
	@ApiMethod(name = "getUnitType")
	public UnitType getUnitType(@Named("id") Long id) {
		EntityManager mgr = getEntityManager();
		UnitType unittype = null;
		try {
			unittype = mgr.find(UnitType.class, id);
		} finally {
			mgr.close();
		}
		return unittype;
	}

	/**
	 * This inserts a new entity into App Engine datastore. If the entity already
	 * exists in the datastore, an exception is thrown.
	 * It uses HTTP POST method.
	 *
	 * @param unittype the entity to be inserted.
	 * @return The inserted entity.
	 */
	@ApiMethod(name = "insertUnitType")
	public UnitType insertUnitType(UnitType unittype) {
		EntityManager mgr = getEntityManager();
		try {
			if (containsUnitType(unittype)) {
				throw new EntityExistsException("Object already exists");
			}
			mgr.persist(unittype);
		} finally {
			mgr.close();
		}
		return unittype;
	}

	private boolean containsUnitType(UnitType unittype) {
		EntityManager mgr = getEntityManager();
		if(unittype.getId()==null){
			return false;
		}
		boolean contains = true;
		try {
			UnitType item = mgr.find(UnitType.class, unittype.getId());
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
