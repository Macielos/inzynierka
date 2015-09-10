package com.inzynierka.activity;

import java.io.IOException;
import java.net.URLEncoder;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.android.gcm.GCMBaseIntentService;
import com.google.android.gcm.GCMRegistrar;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.inzynierka.deviceinfoendpoint.Deviceinfoendpoint;
import com.inzynierka.deviceinfoendpoint.model.DeviceInfo;
import com.inzynierka.entities.players.playerendpoint.Playerendpoint;
import com.inzynierka.entities.players.playerendpoint.model.Player;

/**
 * This class is started up as a service of the Android application. It listens
 * for Google Cloud Messaging (GCM) messages directed to this device.
 * 
 * When the device is successfully registered for GCM, a message is sent to the
 * App Engine backend via Cloud Endpoints, indicating that it wants to receive
 * broadcast messages from the it.
 * 
 * Before registering for GCM, you have to create a project in Google's Cloud
 * Console (https://code.google.com/apis/console). In this project, you'll have
 * to enable the "Google Cloud Messaging for Android" Service.
 * 
 * Once you have set up a project and enabled GCM, you'll have to set the
 * PROJECT_NUMBER field to the project number mentioned in the "Overview" page.
 * 
 * See the documentation at
 * http://developers.google.com/eclipse/docs/cloud_endpoints for more
 * information.
 */
public class PlayerService {
	private final Playerendpoint endpoint;
	private static final String TAG = PlayerService.class.getName();

	public PlayerService() {
		Playerendpoint.Builder endpointBuilder = new Playerendpoint.Builder(AndroidHttp.newCompatibleTransport(),
				new JacksonFactory(), new HttpRequestInitializer() {
					public void initialize(HttpRequest httpRequest) {
					}
				});
		endpoint = CloudEndpointUtils.updateBuilder(endpointBuilder).build();
	}
	
	public void registerPlayer(Player player){
		try {
			endpoint.insertPlayer(player).execute();
		} catch (IOException e) {
			Log.e(TAG, "Exception received when attempting to insert player at " + endpoint.getRootUrl(), e);
		}
	}
	
	public void unregisterPlayer(Player player){
		try {
			endpoint.removePlayer(player.getId());
		} catch (IOException e) {
			Log.e(TAG, "Exception received when attempting to delete player at " + endpoint.getRootUrl(), e);
		}
	}

}
