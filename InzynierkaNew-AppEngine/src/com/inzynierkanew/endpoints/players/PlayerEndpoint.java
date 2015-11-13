package com.inzynierkanew.endpoints.players;

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
import com.inzynierkanew.dumps.WorldDump;
import com.inzynierkanew.endpoints.map.LandEndpoint;
import com.inzynierkanew.endpoints.map.TownEndpoint;
import com.inzynierkanew.entities.map.Land;
import com.inzynierkanew.entities.map.Town;
import com.inzynierkanew.entities.players.Hero;
import com.inzynierkanew.entities.players.Player;
import com.inzynierkanew.entities.players.PlayerSession;
import com.inzynierkanew.utils.EMF;
import com.inzynierkanew.utils.RequestValidator;
import com.inzynierkanew.utils.StringUtils;
import com.inzynierkanew.world.WorldGenerator;
import com.inzynierkanew.world.WorldGeneratorFactory;
import com.inzynierkanew.wrappers.LoginResponse;

@Api(name = "playerendpoint", namespace = @ApiNamespace(ownerDomain = "inzynierkanew.com", ownerName = "inzynierkanew.com", packagePath = "entities.players"))
public class PlayerEndpoint {

	private final LandEndpoint landEndpoint = new LandEndpoint();
	private final TownEndpoint townEndpoint = new TownEndpoint();
	private final HeroEndpoint heroEndpoint = new HeroEndpoint();
	
	/**
	 * This method lists all the entities inserted in datastore.
	 * It uses HTTP GET method and paging support.
	 *
	 * @return A CollectionResponse class containing the list of all entities
	 * persisted and a cursor to the next page.
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	@ApiMethod(name = "listPlayer")
	public CollectionResponse<Player> listPlayer(@Nullable @Named("cursor") String cursorString, @Nullable @Named("limit") Integer limit) {

		EntityManager mgr = null;
		Cursor cursor = null;
		List<Player> execute = null;

		try {
			mgr = getEntityManager();
			Query query = mgr.createQuery("select from Player as Player");
			if (cursorString != null && cursorString != "") {
				cursor = Cursor.fromWebSafeString(cursorString);
				query.setHint(JPACursorHelper.CURSOR_HINT, cursor);
			}

			if (limit != null) {
				query.setFirstResult(0);
				query.setMaxResults(limit);
			}

			execute = (List<Player>) query.getResultList();
			cursor = JPACursorHelper.getCursor(execute);
			if (cursor != null)
				cursorString = cursor.toWebSafeString();

			// Tight loop for fetching all entities from datastore and accomodate
			// for lazy fetch.
//			for (Player obj : execute){
//				obj.getHero();
//			}
		} finally {
			mgr.close();
		}

		return CollectionResponse.<Player> builder().setItems(execute).setNextPageToken(cursorString).build();
	}

	/**
	 * This method gets the entity having primary key id. It uses HTTP GET method.
	 *
	 * @param id the primary key of the java bean.
	 * @return The entity with primary key id.
	 */
	@ApiMethod(name = "getPlayer")
	public Player getPlayer(@Named("id") Long id) {
		EntityManager mgr = getEntityManager();
		Player player = null;
		try {
			player = mgr.find(Player.class, id);
		} finally {
			mgr.close();
		}
		return player;
	}

	/**
	 * This inserts a new entity into App Engine datastore. If the entity already
	 * exists in the datastore, an exception is thrown.
	 * It uses HTTP POST method.
	 *
	 * @param player the entity to be inserted.
	 * @return The inserted entity.
	 */
	@ApiMethod(name = "registerPlayer")
	public Player registerPlayer(Player player) {
		EntityManager mgr = getEntityManager();
		try {
			if (containsPlayer(player)) {
				throw new EntityExistsException("Object already exists");
			}
			WorldGeneratorFactory.fireWorldGeneration();
			player.setGold(WorldGenerator.PLAYER_INITIAL_GOLD);
			Land startingLand = landEndpoint.findLandForNewPlayer();
			Town startingTown = townEndpoint.getTown(startingLand.getTownId());
			Hero hero = new Hero(startingTown.getX(), startingTown.getY(), startingLand.getId());
			hero = heroEndpoint.insertHero(hero);
			player.setHeroId(hero.getId());
			mgr.persist(player);
		} finally {
			mgr.close();
		}
		new WorldDump(new LandEndpoint()).dump();
		return player;
	}

	/**
	 * This method is used for updating an existing entity. If the entity does not
	 * exist in the datastore, an exception is thrown.
	 * It uses HTTP PUT method.
	 *
	 * @param player the entity to be updated.
	 * @return The updated entity.
	 */
	@ApiMethod(name = "updatePlayer")
	public Player updatePlayer(Player player) {
		EntityManager mgr = getEntityManager();
		try {
			if (!containsPlayer(player)) {
				throw new EntityNotFoundException("Object does not exist");
			}
			mgr.merge(player);
		} finally {
			mgr.close();
		}
		return player;
	}

	/**
	 * This method removes the entity with primary key id.
	 * It uses HTTP DELETE method.
	 *
	 * @param id the primary key of the entity to be deleted.
	 */
	@ApiMethod(name = "removePlayer")
	public void removePlayer(@Named("id") Long id) {
		EntityManager mgr = getEntityManager();
		try {
			Player player = mgr.find(Player.class, id);
			mgr.remove(player);
		} finally {
			mgr.close();
		}
	}
	
	@ApiMethod(name = "removePlayerByRegistrationId")
	public void removePlayerByRegistrationId(@Named("id") String id) {
		EntityManager mgr = getEntityManager();
		try {
			Player player = findPlayerByRegistrationId(id);
			if(player!=null){
				mgr.remove(player);
			}
		} finally {
			mgr.close();
		}
	}
	
	private Player findPlayerByRegistrationId(@Named("id") String id){
		EntityManager mgr = getEntityManager();
		try {
			List<Player> players = (List<Player>) mgr.createQuery("select from Player as Player where Player.deviceRegistrationId = '"+id+"'").getResultList();
			if(players.isEmpty()){
				return null;
			}
			return players.get(0);
		} finally {
			mgr.close();
		}
	}
	
	@ApiMethod(name = "findPlayerBySessionId")
	public Player findPlayerBySessionId(@Named("id") String id){
		EntityManager mgr = getEntityManager();
		try {
			List<PlayerSession> playerSessions = (List<PlayerSession>) mgr.createQuery("select from PlayerSession as PlayerSession where PlayerSession.sessionId = '"+id+"'").getResultList();
			if(playerSessions.isEmpty()){
				return null;
			}
			Player player = getPlayer(playerSessions.get(0).getId());
			//player.getHero();
			return player;
		} finally {
			mgr.close();
		}
	}
	
	@ApiMethod(name = "authenticatePlayer")
	public LoginResponse authenticatePlayer(@Named("name")String name, @Named("password")String password){
		EntityManager mgr = null;
		PlayerSession session = null;
		if(StringUtils.isEmpty(name) || StringUtils.isEmpty(password)){
			return new LoginResponse();
		}
		try {
			mgr = getEntityManager();
			List<Player> players = (List<Player>) mgr.createQuery("select from Player as Player where Player.name = '"+name+"'").getResultList();
			if(players.isEmpty()){
				return new LoginResponse();
			}
			Player player = players.get(0);
			
			if(!player.getPassword().equals(RequestValidator.hashPassword(password.getBytes()))){
				return new LoginResponse();
			}
			
			//session = new PlayerSession(player.getId(), UUID.randomUUID().toString(), new Date());
			//mgr.merge(session);
			
			//TODO zrobiæ system sesji, który nie jest tak zjebany jak trzymanie ich w datastorze
			return new LoginResponse(player.getId().toString());
		} finally {
			mgr.close();
		}
	}
	
	@ApiMethod(name = "updateHeroPosition")
	public void updateHeroPosition(@Named("id")Long id, @Named("x")Integer x, @Named("y")Integer y){
		EntityManager mgr = null;
		try {
			mgr = getEntityManager();
			Hero hero = mgr.find(Hero.class, id);
			if(hero!=null){
				hero.setX(x);
				hero.setY(y);
				mgr.persist(hero);
			}
		} finally {
			mgr.close();
		}
	}
	
	@ApiMethod(name = "moveHeroToDifferentLand")
	public void moveHeroToDifferentLand(@Named("id")Long id, @Named("nextLandId")Long nextLandId, @Named("x")Integer x, @Named("y")Integer y){
		EntityManager mgr = null;
		try {
			mgr = getEntityManager();
			Hero hero = mgr.find(Hero.class, id);
			if(hero!=null){
				hero.setX(x);
				hero.setY(y);
				hero.setCurrentLandId(nextLandId);
				mgr.persist(hero);
			}
		} finally {
			mgr.close();
		}
	}

	private boolean containsPlayer(Player player) {
		Long id = player.getId();
		if(id == null){
			return false;
		}
		EntityManager mgr = getEntityManager();
		boolean contains = true;
		try {
			Player item = mgr.find(Player.class, player.getId());
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
