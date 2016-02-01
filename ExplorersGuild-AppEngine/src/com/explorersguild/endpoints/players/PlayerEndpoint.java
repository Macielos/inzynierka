package com.explorersguild.endpoints.players;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Named;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;

import com.explorersguild.endpoints.communication.WorldUpdateEndpoint;
import com.explorersguild.endpoints.map.LandEndpoint;
import com.explorersguild.endpoints.map.TownEndpoint;
import com.explorersguild.entities.map.Land;
import com.explorersguild.entities.map.Town;
import com.explorersguild.entities.players.Hero;
import com.explorersguild.entities.players.Player;
import com.explorersguild.init.ApplicationInitializer;
import com.explorersguild.messages.WorldUpdate;
import com.explorersguild.shared.RequestValidator;
import com.explorersguild.shared.SharedConstants;
import com.explorersguild.shared.StringUtils;
import com.explorersguild.shared.TimeUtils;
import com.explorersguild.utils.EMF;
import com.explorersguild.utils.NamespaceConstants;
import com.explorersguild.wrappers.LoginResponse;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;
import com.google.appengine.datanucleus.query.JPACursorHelper;

@Api(name = "playerendpoint", namespace = @ApiNamespace(ownerDomain = "explorersguild.com", ownerName = "explorersguild.com", packagePath = "entities.players") )
public class PlayerEndpoint {

	private final LandEndpoint landEndpoint = new LandEndpoint();
	private final TownEndpoint townEndpoint = new TownEndpoint();
	private final HeroEndpoint heroEndpoint = new HeroEndpoint();
	private final WorldUpdateEndpoint worldUpdateEndpoint = new WorldUpdateEndpoint();

	private final Log log = LogFactory.getLog(getClass());

	private static final String INCORRECT_LOGIN_OR_PASSWORD = "Incorrect username of password";
	private static final String PROBLEM = "Internal server error occurred. Please try again later";

	/**
	 * This method lists all the entities inserted in datastore. It uses HTTP
	 * GET method and paging support.
	 *
	 * @return A CollectionResponse class containing the list of all entities
	 *         persisted and a cursor to the next page.
	 */
	@SuppressWarnings({ "unchecked" })
	@ApiMethod(name = "listPlayer")
	public CollectionResponse<Player> listPlayer(@Nullable @Named("cursor") String cursorString,
			@Nullable @Named("limit") Integer limit) {

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
		} finally {
			mgr.close();
		}

		return CollectionResponse.<Player> builder().setItems(execute).setNextPageToken(cursorString).build();
	}

	/**
	 * This method gets the entity having primary key id. It uses HTTP GET
	 * method.
	 *
	 * @param id
	 *            the primary key of the java bean.
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
	 * This inserts a new entity into App Engine datastore. If the entity
	 * already exists in the datastore, an exception is thrown. It uses HTTP
	 * POST method.
	 *
	 * @param player
	 *            the entity to be inserted.
	 * @return The inserted entity.
	 */
	@ApiMethod(name = "registerPlayer")
	public Player registerPlayer(Player player, @Named("deviceRegistrationId") String deviceRegistrationID,
			@Named("strength") Integer strength, @Named("agility") Integer agility,
			@Named("intelligence") Integer intelligence, @Named("freeSkillPoints") Integer freeSkillPoints) {
		EntityManager mgr = getEntityManager();
		try {
			if (containsPlayer(player)) {
				throw new EntityExistsException("Object already exists");
			}
			Land startingLand = landEndpoint.findLandForNewPlayer();
			Town startingTown = townEndpoint.getTown(startingLand.getTownId());
			Hero hero = new Hero(deviceRegistrationID, startingTown.getX(), startingTown.getY(), startingLand.getId(),
					SharedConstants.HERO_INITIAL_GOLD, strength, agility, intelligence, freeSkillPoints);
			hero = heroEndpoint.insertHero(hero);
			player.setHeroId(hero.getId());
			mgr.persist(player);
			new WorldUpdateEndpoint().sendWorldUpdate(hero.getCurrentLandId(), new WorldUpdate(SharedConstants.ARRIVE,
					hero.getId(), hero.getCurrentLandId(), hero.getX(), hero.getY()));
		} catch (IOException e) {
			log.error(e, e);
		} finally {
			mgr.close();
		}
		// new WorldDump(landEndpoint).dumpLands().dumpTerrain();
		return player;
	}

	/**
	 * This method is used for updating an existing entity. If the entity does
	 * not exist in the datastore, an exception is thrown. It uses HTTP PUT
	 * method.
	 *
	 * @param player
	 *            the entity to be updated.
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
	 * This method removes the entity with primary key id. It uses HTTP DELETE
	 * method.
	 *
	 * @param id
	 *            the primary key of the entity to be deleted.
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

	@ApiMethod(name = "authenticatePlayer")
	public LoginResponse authenticatePlayer(@Named("name") String name, @Named("password") String password) {
		EntityManager mgr = null;
		if (StringUtils.isEmpty(name) || StringUtils.isEmpty(password)) {
			return new LoginResponse(INCORRECT_LOGIN_OR_PASSWORD);
		}
		boolean dailyReward = false;
		boolean saveHero = false;
		try {
			mgr = getEntityManager();
			List<Player> players = (List<Player>) mgr
					.createQuery("select from Player as Player where Player.name = '" + name + "'").getResultList();
			if (players.isEmpty()) {
				return new LoginResponse(INCORRECT_LOGIN_OR_PASSWORD);
			}
			Player player = players.get(0);

			if (!player.getPassword().equals(RequestValidator.hashPassword(password.getBytes()))) {
				return new LoginResponse(INCORRECT_LOGIN_OR_PASSWORD);
			}

			Hero hero = mgr.find(Hero.class, player.getHeroId());
			if (hero == null) {
				return new LoginResponse(INCORRECT_LOGIN_OR_PASSWORD);
			}
			if (!hero.isActive()) {
				saveHero = true;
				hero.setActive(true);
				if (hero.getLastLogin() != null && hero.getLastLogin().before(TimeUtils.startOfDay())) {
					hero.setGold(hero.getGold() + SharedConstants.DAILY_REWARD);
					dailyReward = true;
				}
			}
			hero.setLastLogin(new Date());
			if (saveHero || !ApplicationInitializer.QUOTA_SAVING_MODE) {
				mgr.merge(hero);
			}

			MemcacheService memcacheService = MemcacheServiceFactory
					.getMemcacheService(NamespaceConstants.MEMCACHE_ACTIVE_HEROES);
			HashMap<Long, String> activeHeroes = (HashMap<Long, String>) memcacheService.get(hero.getCurrentLandId());
			if (activeHeroes == null) {
				activeHeroes = new HashMap<>();
			}
			activeHeroes.put(hero.getId(), hero.getDeviceRegistrationID());
			memcacheService.put(hero.getCurrentLandId(), activeHeroes);

			worldUpdateEndpoint.sendWorldUpdate(hero.getCurrentLandId(), new WorldUpdate(SharedConstants.ARRIVE,
					hero.getId(), hero.getCurrentLandId(), hero.getX(), hero.getY()));

			return new LoginResponse(player.getId().toString(), dailyReward ? getDailyRewardMessage() : null);
		} catch (IOException e) {
			log.error(e, e);
			return new LoginResponse(PROBLEM);
		} finally {
			mgr.close();
		}
	}

	private String getDailyRewardMessage() {
		return "You have received a daily reward of " + SharedConstants.DAILY_REWARD + " gold for regular logging!";
	}

	private boolean containsPlayer(Player player) {
		Long id = player.getId();
		if (id == null) {
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
