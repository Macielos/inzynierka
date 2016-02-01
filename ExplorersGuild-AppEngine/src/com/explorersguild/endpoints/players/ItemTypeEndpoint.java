package com.explorersguild.endpoints.players;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Named;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.explorersguild.entities.players.ItemType;
import com.explorersguild.shared.CollectionUtils;
import com.explorersguild.shared.SharedConstants;
import com.explorersguild.utils.EMF;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.datanucleus.query.JPACursorHelper;

@Api(name = "itemtypeendpoint", namespace = @ApiNamespace(ownerDomain = "explorersguild.com", ownerName = "explorersguild.com", packagePath = "entities.players") )
public class ItemTypeEndpoint {

	/**
	 * This method lists all the entities inserted in datastore.
	 * It uses HTTP GET method and paging support.
	 *
	 * @return A CollectionResponse class containing the list of all entities
	 * persisted and a cursor to the next page.
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	@ApiMethod(name = "listItemType")
	public CollectionResponse<ItemType> listItemType(@Nullable @Named("cursor") String cursorString,
			@Nullable @Named("limit") Integer limit) {

		EntityManager mgr = null;
		Cursor cursor = null;
		List<ItemType> execute = null;

		try {
			mgr = getEntityManager();
			Query query = mgr.createQuery("select from ItemType as ItemType");
			if (cursorString != null && cursorString != "") {
				cursor = Cursor.fromWebSafeString(cursorString);
				query.setHint(JPACursorHelper.CURSOR_HINT, cursor);
			}

			if (limit != null) {
				query.setFirstResult(0);
				query.setMaxResults(limit);
			}

			execute = (List<ItemType>) query.getResultList();
			cursor = JPACursorHelper.getCursor(execute);
			if (cursor != null)
				cursorString = cursor.toWebSafeString();

			// Tight loop for fetching all entities from datastore and accomodate
			// for lazy fetch.
			for (ItemType obj : execute)
				;
		} finally {
			mgr.close();
		}

		return CollectionResponse.<ItemType> builder().setItems(execute).setNextPageToken(cursorString).build();
	}
	
	@ApiMethod(name = "getItemTypes")
	public CollectionResponse<ItemType> getItemTypes(@Named("ids") String ids) {
		String[] idArray = ids.split(SharedConstants.SEPARATOR);
		List<ItemType> execute = new ArrayList<>(idArray.length);
		EntityManager mgr = null;
		try {
			mgr = getEntityManager();
			ItemType item;
			for(String i: idArray){
				item = mgr.find(ItemType.class, Long.parseLong(i));
				if(item!=null){
					execute.add(item);
				}
			}
		} finally {
			mgr.close();
		}
		return CollectionResponse.<ItemType> builder().setItems(execute).build();
	}
	
	@SuppressWarnings({ "unchecked", "unused" })
	@ApiMethod(name = "getRandomItemsByType")
	public CollectionResponse<ItemType> getRandomItemsByType(@Named("limit") Integer limit, 
			@Named("level") Integer level, @Named("itemClass") String itemClass ) {

		EntityManager mgr = null;
		Cursor cursor = null;
		List<ItemType> execute = null;
		
		int minItemLevel = level == null ? 0 : (level - SharedConstants.HERO_ITEM_LEVEL_DIFF);
		Integer maxItemLevel = level == null ? null : (level + SharedConstants.HERO_ITEM_LEVEL_DIFF);

		try {
			mgr = getEntityManager();
			String queryString = "select from ItemType as ItemType where ItemType.itemLevel >= "+minItemLevel;
			if(maxItemLevel!=null){
				queryString+=" and ItemType.itemLevel <= "+maxItemLevel;
			}
			if(itemClass!=null){
				queryString+=" and ItemType.itemClass = '"+itemClass+"'";
			}
			Query query = mgr.createQuery(queryString);
			
			execute = (List<ItemType>) query.getResultList();
			execute = CollectionUtils.getNRandomElements(execute, limit);
			// Tight loop for fetching all entities from datastore and accomodate
			// for lazy fetch.
			for (ItemType obj : execute);
		} finally {
			mgr.close();
		}

		return CollectionResponse.<ItemType> builder().setItems(execute).build();
	}
	
	/**
	 * This method gets the entity having primary key id. It uses HTTP GET method.
	 *
	 * @param id the primary key of the java bean.
	 * @return The entity with primary key id.
	 */
	@ApiMethod(name = "getItemType")
	public ItemType getItemType(@Named("id") Long id) {
		EntityManager mgr = getEntityManager();
		ItemType item = null;
		try {
			item = mgr.find(ItemType.class, id);
		} finally {
			mgr.close();
		}
		return item;
	}

	/**
	 * This inserts a new entity into App Engine datastore. If the entity already
	 * exists in the datastore, an exception is thrown.
	 * It uses HTTP POST method.
	 *
	 * @param item the entity to be inserted.
	 * @return The inserted entity.
	 */
	@ApiMethod(name = "insertItemType")
	public ItemType insertItemType(ItemType item) {
		EntityManager mgr = getEntityManager();
		try {
			if (containsItem(item)) {
				throw new EntityExistsException("Object already exists");
			}
			mgr.persist(item);
		} finally {
			mgr.close();
		}
		return item;
	}

	/**
	 * This method removes the entity with primary key id.
	 * It uses HTTP DELETE method.
	 *
	 * @param id the primary key of the entity to be deleted.
	 */
	@ApiMethod(name = "removeItemType")
	public void removeItemType(@Named("id") Long id) {
		EntityManager mgr = getEntityManager();
		try {
			ItemType item = mgr.find(ItemType.class, id);
			mgr.remove(item);
		} finally {
			mgr.close();
		}
	}

	private boolean containsItem(ItemType item) {
		if(item==null || item.getId()==null){
			return false;
		}
		EntityManager mgr = getEntityManager();
		boolean contains = true;
		try {
			ItemType dsItem = mgr.find(ItemType.class, item.getId());
			if (dsItem == null) {
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
