package com.explorersguild.endpoints.map;

import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Named;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.explorersguild.entities.map.FieldType;
import com.explorersguild.utils.EMF;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.datanucleus.query.JPACursorHelper;

@Api(name = "fieldtypeendpoint", namespace = @ApiNamespace(ownerDomain = "explorersguild.com", ownerName = "explorersguild.com", packagePath = "entities.map"))
public class FieldTypeEndpoint {

	/**
	 * This method lists all the entities inserted in datastore.
	 * It uses HTTP GET method and paging support.
	 *
	 * @return A CollectionResponse class containing the list of all entities
	 * persisted and a cursor to the next page.
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	@ApiMethod(name = "listFieldType")
	public CollectionResponse<FieldType> listFieldType(@Nullable @Named("cursor") String cursorString,
			@Nullable @Named("limit") Integer limit) {

		EntityManager mgr = null;
		Cursor cursor = null;
		List<FieldType> execute = null;

		try {
			mgr = getEntityManager();
			Query query = mgr.createQuery("select from FieldType as FieldType");
			if (cursorString != null && cursorString != "") {
				cursor = Cursor.fromWebSafeString(cursorString);
				query.setHint(JPACursorHelper.CURSOR_HINT, cursor);
			}

			if (limit != null) {
				query.setFirstResult(0);
				query.setMaxResults(limit);
			}

			execute = (List<FieldType>) query.getResultList();
			cursor = JPACursorHelper.getCursor(execute);
			if (cursor != null)
				cursorString = cursor.toWebSafeString();

			// Tight loop for fetching all entities from datastore and accomodate
			// for lazy fetch.
			for (FieldType obj : execute)
				;
		} finally {
			mgr.close();
		}

		return CollectionResponse.<FieldType> builder().setItems(execute).setNextPageToken(cursorString).build();
	}

	/**
	 * This method gets the entity having primary key id. It uses HTTP GET method.
	 *
	 * @param id the primary key of the java bean.
	 * @return The entity with primary key id.
	 */
	@ApiMethod(name = "getFieldType")
	public FieldType getFieldType(@Named("id") Long id) {
		EntityManager mgr = getEntityManager();
		FieldType fieldtype = null;
		try {
			fieldtype = mgr.find(FieldType.class, id);
		} finally {
			mgr.close();
		}
		return fieldtype;
	}

	/**
	 * This inserts a new entity into App Engine datastore. If the entity already
	 * exists in the datastore, an exception is thrown.
	 *
	 * @param fieldtype the entity to be inserted.
	 * @return The inserted entity.
	 */
	public FieldType insertFieldType(FieldType fieldtype) {
		EntityManager mgr = getEntityManager();
		try {
			if (containsFieldType(fieldtype)) {
				throw new EntityExistsException("Object already exists");
			}
			mgr.persist(fieldtype);
		} finally {
			mgr.close();
		}
		return fieldtype;
	}
	
	private boolean containsFieldType(FieldType fieldtype) {
		EntityManager mgr = getEntityManager();
		boolean contains = true;
		try {
			FieldType item = mgr.find(FieldType.class, fieldtype.getId());
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
