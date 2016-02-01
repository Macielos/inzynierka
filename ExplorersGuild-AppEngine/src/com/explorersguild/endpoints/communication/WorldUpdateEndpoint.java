package com.explorersguild.endpoints.communication;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map.Entry;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;

import com.explorersguild.endpoints.players.HeroEndpoint;
import com.explorersguild.entities.players.Hero;
import com.explorersguild.messages.WorldUpdate;
import com.explorersguild.shared.SharedConstants;
import com.explorersguild.utils.NamespaceConstants;
import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Sender;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.config.Named;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;

@Api(name = "worldUpdateEndpoint", namespace = @ApiNamespace(ownerDomain = "explorersguild.com", ownerName = "explorersguild.com", packagePath = "") )
public class WorldUpdateEndpoint {

	/*
	 * Fill this in with the server key that you've obtained from the API
	 * Console (https://code.google.com/apis/console). This is required for
	 * using Google Cloud Messaging from your AppEngine application even if you
	 * are using a App Engine's local development server.
	 */
	private static final String API_KEY = "AIzaSyAHt1faxsd22w9jsNgf8PnyIxBpnvlqzFw";
	
	private static final Log log = LogFactory.getLog(WorldUpdateEndpoint.class);

	private static final HeroEndpoint heroEndpoint = new HeroEndpoint();

	public void sendWorldUpdate(@Named("landId") Long landId, WorldUpdate worldUpdate) throws IOException {
		Sender sender = new Sender(API_KEY);
		MemcacheService memcacheService = MemcacheServiceFactory.getMemcacheService(NamespaceConstants.MEMCACHE_ACTIVE_HEROES);
		HashMap<Long, String> activeHeroes = (HashMap<Long, String>) memcacheService.get(landId);
		if(activeHeroes==null){
			Collection<Hero> activeHeroesFromDatastore = heroEndpoint.getActiveHeroesInLand(landId).getItems();
			activeHeroes = new HashMap<>(activeHeroesFromDatastore.size());
			for(Hero hero: activeHeroesFromDatastore){
				activeHeroes.put(hero.getId(), hero.getDeviceRegistrationID());
			}
			memcacheService.put(landId, activeHeroes);
		} 
		for (Entry<Long, String> activeHero : activeHeroes.entrySet()) {
			if(activeHero.getKey().longValue()!=worldUpdate.heroId.longValue()){
				log.info("Sending world update "+worldUpdate+" to hero "+activeHero.getKey());
				doSendViaGcm(worldUpdate, sender, activeHero.getValue());
			}
		}
	}

	/**
	 * Sends the message using the Sender object to the registered device.
	 * 
	 * @param message
	 *            the message to be sent in the GCM ping to the device.
	 * @param sender
	 *            the Sender object to be used for ping,
	 * @param deviceInfo
	 *            the registration id of the device.
	 * @return Result the result of the ping.
	 */
	private void doSendViaGcm(WorldUpdate worldUpdate, Sender sender, String receiverId) {
		Message msg = buildMessage(worldUpdate);
		log.info("Sending message: "+msg);
		try {
			sender.send(msg, receiverId, 5);
		} catch (IOException e) {
			log.error(e, e);
		}
	}

	private Message buildMessage(WorldUpdate worldUpdate) {
		return new Message.Builder().addData(SharedConstants.ACTION, safeToString(worldUpdate.action))
				.addData(SharedConstants.HERO_ID, safeToString(worldUpdate.heroId))
				.addData(SharedConstants.CURRENT_LAND_ID, safeToString(worldUpdate.currentLandId))
				.addData(SharedConstants.NEW_X, safeToString(worldUpdate.newX))
				.addData(SharedConstants.NEW_Y, safeToString(worldUpdate.newY)).build();
	}

	private String safeToString(Object o) {
		return o == null ? null : o.toString();
	}
}
