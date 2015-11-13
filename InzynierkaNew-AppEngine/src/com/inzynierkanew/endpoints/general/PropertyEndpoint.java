package com.inzynierkanew.endpoints.general;

import com.inzynierkanew.entities.general.Property;
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

@Api(name = "propertyendpoint", namespace = @ApiNamespace(ownerDomain = "inzynierkanew.com", ownerName = "inzynierkanew.com", packagePath = "entities.general"))
public class PropertyEndpoint {

	/**
	 * This method lists all the entities inserted in datastore.
	 * It uses HTTP GET method and paging support.
	 *
	 * @return A CollectionResponse class containing the list of all entities
	 * persisted and a cursor to the next page.
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	@ApiMethod(name = "listProperty")
	public CollectionResponse<Property> listProperty(@Nullable @Named("cursor") String cursorString, @Nullable @Named("limit") Integer limit) {

		EntityManager mgr = null;
		Cursor cursor = null;
		List<Property> execute = null;

		try {
			mgr = getEntityManager();
			Query query = mgr.createQuery("select from Property as Property");
			if (cursorString != null && cursorString != "") {
				cursor = Cursor.fromWebSafeString(cursorString);
				query.setHint(JPACursorHelper.CURSOR_HINT, cursor);
			}

			if (limit != null) {
				query.setFirstResult(0);
				query.setMaxResults(limit);
			}

			execute = (List<Property>) query.getResultList();
			cursor = JPACursorHelper.getCursor(execute);
			if (cursor != null)
				cursorString = cursor.toWebSafeString();

			// Tight loop for fetching all entities from datastore and accomodate
			// for lazy fetch.
			for (Property obj : execute)
				;
		} finally {
			mgr.close();
		}

		return CollectionResponse.<Property> builder().setItems(execute).setNextPageToken(cursorString).build();
	}

	/**
	 * This method gets the entity having primary key id. It uses HTTP GET method.
	 *
	 * @param id the primary key of the java bean.
	 * @return The entity with primary key id.
	 */
	@ApiMethod(name = "getProperty")
	public Property getProperty(@Named("id") String id) {
		EntityManager mgr = getEntityManager();
		Property property = null;
		try {
			property = mgr.find(Property.class, id);
		} finally {
			mgr.close();
		}
		return property;
	}

	public Property insertProperty(Property property) {
		EntityManager mgr = getEntityManager();
		try {
			if (containsProperty(property)) {
				throw new EntityExistsException("Object already exists");
			}
			mgr.persist(property);
		} finally {
			mgr.close();
		}
		return property;
	}

	public Property updateProperty(Property property) {
		EntityManager mgr = getEntityManager();
		try {
			if (!containsProperty(property)) {
				throw new EntityNotFoundException("Object does not exist");
			}
			mgr.persist(property);
		} finally {
			mgr.close();
		}
		return property;
	}

	public void removeProperty(@Named("id") String id) {
		EntityManager mgr = getEntityManager();
		try {
			Property property = mgr.find(Property.class, id);
			mgr.remove(property);
		} finally {
			mgr.close();
		}
	}

	private boolean containsProperty(Property property) {
		EntityManager mgr = getEntityManager();
		boolean contains = true;
		try {
			Property item = mgr.find(Property.class, property.getKey());
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
