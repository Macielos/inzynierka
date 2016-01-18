package com.inzynierkanew.endpoints.communication;

import java.io.IOException;
import java.util.Collection;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.config.Named;
import com.inzynierkanew.endpoints.players.HeroEndpoint;
import com.inzynierkanew.entities.players.Hero;
import com.inzynierkanew.messages.WorldUpdate;
import com.inzynierkanew.shared.SharedConstants;

/**
 * A simple Cloud Endpoint that receives notifications from a web client
 * (<server url>/index.html), and broadcasts them to all of the devices that
 * were registered with this application (via DeviceInfoEndpoint).
 * 
 * In order for this sample to work, you have to populate the API_KEY field with
 * your key for server apps. You can obtain this key from the API console
 * (https://code.google.com/apis/console). You'll first have to create a project
 * and enable the Google Cloud Messaging Service for it, as described in the
 * javadoc for GCMIntentService.java (in your Android project).
 * 
 * After creating the project in the API console, browse to the "API Access"
 * section, and select the option to "Create a New Server Key". The generated
 * key is what you'll enter into the API_KEY field.
 * 
 * See the documentation at
 * http://developers.google.com/eclipse/docs/cloud_endpoints for more
 * information.
 * 
 * NOTE: This endpoint does not use any form of authorization or authentication!
 * If this app is deployed, anyone can access this endpoint! If you'd like to
 * add authentication, take a look at the documentation.
 */
@Api(name = "worldUpdateEndpoint", namespace = @ApiNamespace(ownerDomain = "inzynierkanew.com", ownerName = "inzynierkanew.com", packagePath = "") )
// NO AUTHENTICATION; OPEN ENDPOINT!
public class WorldUpdateEndpoint {

	/*
	 * TODO: Fill this in with the server key that you've obtained from the API
	 * Console (https://code.google.com/apis/console). This is required for
	 * using Google Cloud Messaging from your AppEngine application even if you
	 * are using a App Engine's local development server.
	 */
	private static final String API_KEY = "AIzaSyBj3cFU3AVaQt99q5DzGVF7nhEAowcPqN8";
	
	private static final Log log = LogFactory.getLog(WorldUpdateEndpoint.class);

	private static final HeroEndpoint heroEndpoint = new HeroEndpoint();

	/**
	 * This accepts a message and persists it in the AppEngine datastore, it
	 * will also broadcast the message to upto 10 registered android devices via
	 * Google Cloud Messaging
	 * 
	 * @param message
	 *            the entity to be inserted.
	 * @return
	 * @throws IOException
	 */
	public void sendWorldUpdate(@Named("landId") Long landId, WorldUpdate worldUpdate) throws IOException {
		Sender sender = new Sender(API_KEY);
		Collection<Hero> receivers = heroEndpoint.getActiveHeroesInLand(landId).getItems();
		for (Hero receiver : receivers) {
			if(receiver.getId().longValue()!=worldUpdate.heroId.longValue()){
				doSendViaGcm(worldUpdate, sender, receiver);
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
	private Result doSendViaGcm(WorldUpdate worldUpdate, Sender sender, Hero receiver) throws IOException {
		log.info("Sending "+worldUpdate+" to "+receiver.getId());
		Message msg = buildMessage(worldUpdate);
		log.info("MSG: "+msg);
		Result result = sender.send(msg, receiver.getDeviceRegistrationID(), 8);
		log.info("RESULT: "+result);

		/*
		 * if (result.getMessageId() != null) { String canonicalRegId =
		 * result.getCanonicalRegistrationId(); if (canonicalRegId != null) {
		 * //??? player.setDeviceRegistrationID(canonicalRegId);
		 * endpoint.updatePlayer(player); } } else { String error =
		 * result.getErrorCodeName(); if
		 * (error.equals(Constants.ERROR_NOT_REGISTERED)) {
		 * endpoint.removeDeviceInfo(deviceInfo.getDeviceRegistrationID()); }
		 */

		return result;
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
