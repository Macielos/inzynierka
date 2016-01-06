package com.inzynierkanew.endpoints.players;

import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Named;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;

import org.datanucleus.store.appengine.query.JPACursorHelper;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.appengine.api.datastore.Cursor;
import com.inzynierkanew.entities.players.Hero;
import com.inzynierkanew.utils.EMF;

@Api(name = "heroendpoint", namespace = @ApiNamespace(ownerDomain = "inzynierkanew.com", ownerName = "inzynierkanew.com", packagePath = "entities.players"))
public class HeroEndpoint {

	/**
	 * This method lists all the entities inserted in datastore.
	 * It uses HTTP GET method and paging support.
	 *
	 * @return A CollectionResponse class containing the list of all entities
	 * persisted and a cursor to the next page.
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	@ApiMethod(name = "listHero")
	public CollectionResponse<Hero> listHero(@Nullable @Named("cursor") String cursorString, @Nullable @Named("limit") Integer limit) {

		EntityManager mgr = null;
		Cursor cursor = null;
		List<Hero> execute = null;

		try {
			mgr = getEntityManager();
			Query query = mgr.createQuery("select from Hero as Hero");
			if (cursorString != null && cursorString != "") {
				cursor = Cursor.fromWebSafeString(cursorString);
				query.setHint(JPACursorHelper.CURSOR_HINT, cursor);
			}

			if (limit != null) {
				query.setFirstResult(0);
				query.setMaxResults(limit);
			}

			execute = (List<Hero>) query.getResultList();
			cursor = JPACursorHelper.getCursor(execute);
			if (cursor != null)
				cursorString = cursor.toWebSafeString();

			// Tight loop for fetching all entities from datastore and accomodate
			// for lazy fetch.
			for (Hero obj : execute)
				;
		} finally {
			mgr.close();
		}

		return CollectionResponse.<Hero> builder().setItems(execute).setNextPageToken(cursorString).build();
	}

	/**
	 * This method gets the entity having primary key id. It uses HTTP GET method.
	 *
	 * @param id the primary key of the java bean.
	 * @return The entity with primary key id.
	 */
	@ApiMethod(name = "getHero")
	public Hero getHero(@Named("id") Long id) {
		EntityManager mgr = getEntityManager();
		Hero hero = null;
		try {
			hero = mgr.find(Hero.class, id);
		} finally {
			mgr.close();
		}
		return hero;
	}

	/**
	 * This inserts a new entity into App Engine datastore. If the entity already
	 * exists in the datastore, an exception is thrown.
	 * It uses HTTP POST method.
	 *
	 * @param hero the entity to be inserted.
	 * @return The inserted entity.
	 */
	@ApiMethod(name = "insertHero")
	public Hero insertHero(Hero hero) {
		EntityManager mgr = getEntityManager();
		try {
			if (containsHero(hero)) {
				throw new EntityExistsException("Object already exists");
			}
			mgr.persist(hero);
		} finally {
			mgr.close();
		}
		return hero;
	}

	/**
	 * This method is used for updating an existing entity. If the entity does not
	 * exist in the datastore, an exception is thrown.
	 * It uses HTTP PUT method.
	 *
	 * @param hero the entity to be updated.
	 * @return The updated entity.
	 */
	@ApiMethod(name = "updateHero")
	public Hero updateHero(Hero hero) {
		EntityManager mgr = getEntityManager();
		try {
			if (!containsHero(hero)) {
				throw new EntityNotFoundException("Object does not exist");
			}
			mgr.merge(hero);
		} finally {
			mgr.close();
		}
		return hero;
	}

	/**
	 * This method removes the entity with primary key id.
	 * It uses HTTP DELETE method.
	 *
	 * @param id the primary key of the entity to be deleted.
	 */
	@ApiMethod(name = "removeHero")
	public void removeHero(@Named("id") Long id) {
		EntityManager mgr = getEntityManager();
		try {
			Hero hero = mgr.find(Hero.class, id);
			mgr.remove(hero);
		} finally {
			mgr.close();
		}
	}

	private boolean containsHero(Hero hero) {
		if(hero.getId() == null){
			return false;
		}
		EntityManager mgr = getEntityManager();
		boolean contains = true;
		try {
			Hero item = mgr.find(Hero.class, hero.getId());
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
