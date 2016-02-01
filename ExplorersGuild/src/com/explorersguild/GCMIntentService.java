package com.explorersguild;

import java.io.IOException;

import com.explorersguild.entities.players.playerendpoint.Playerendpoint;
import com.explorersguild.entities.players.playerendpoint.model.Player;
import com.explorersguild.game.GameView;
import com.explorersguild.init.RegisterActivity;
import com.explorersguild.shared.TimeUtils;
import com.explorersguild.utils.CloudEndpointUtils;
import com.explorersguild.utils.RegistrationContainer;
import com.google.android.gcm.GCMBaseIntentService;
import com.google.android.gcm.GCMRegistrar;
import com.google.api.client.util.DateTime;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

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
public class GCMIntentService extends GCMBaseIntentService {
	private final Playerendpoint playerEndpoint;

	private static GameView gameView;

	/*
	 * Set this to a valid project number. See
	 * http://developers.google.com/eclipse/docs/cloud_endpoints for more
	 * information.
	 */
	public static final String PROJECT_NUMBER = "883772250";

	private static RegistrationContainer registree;

	/**
	 * Register the device for GCM.
	 * 
	 * @param mContext
	 *            the activity's context.
	 */
	public static void register(Context mContext, RegistrationContainer container) {
		registree = container;
		GCMRegistrar.checkDevice(mContext);
		GCMRegistrar.checkManifest(mContext);
		GCMRegistrar.register(mContext, PROJECT_NUMBER);
	}

	/**
	 * Unregister the device from the GCM service.
	 * 
	 * @param mContext
	 *            the activity's context.
	 */
	public static void unregister(Context mContext) {
		GCMRegistrar.unregister(mContext);
	}

	public GCMIntentService() {
		super(PROJECT_NUMBER);
		playerEndpoint = CloudEndpointUtils.newPlayerEndpoint();
	}

	// niezbyt to ³adne ale póki co innego pomys³u nie mam
	public static void setGameView(GameView newGameView) {
		gameView = newGameView;
	}

	/**
	 * Called on registration error. This is called in the context of a Service
	 * - no dialog or UI.
	 * 
	 * @param context
	 *            the Context
	 * @param errorId
	 *            an error message
	 */
	@Override
	public void onError(Context context, String errorId) {
		sendNotificationIntent(context, "Failed to register to Google Cloud Messaging. Try again later", true, true);
	}

	/**
	 * Called when a cloud message has been received.
	 */
	@Override
	public void onMessage(Context context, Intent intent) {
		if (gameView != null) {
			gameView.updateOtherHero(intent);
		}
	}

	/**
	 * Called back when a registration token has been received from the Google
	 * Cloud Messaging service.
	 * 
	 * @param context
	 *            the Context
	 */
	@Override
	public void onRegistered(Context context, String registrationId) {
		try {
			Player player = registree.getPlayer().setRegistrationTime(new DateTime(TimeUtils.now()));
			playerEndpoint.registerPlayer(registrationId, registree.getStrength(), registree.getAgility(),
					registree.getIntelligence(), registree.getPointsLeft(), player).execute();
		} catch (IOException e) {
			Log.e(GCMIntentService.class.getName(),
					"Exception received when attempting to register with server at " + playerEndpoint.getRootUrl(), e);

			sendNotificationIntent(context, "Failed to register to Endpoints Server. Try again later", true, true);
			return;
		}

		sendNotificationIntent(context, "Registration successful! You can now login with your username and password",
				false, true);
	}

	/**
	 * Called back when the Google Cloud Messaging service has unregistered the
	 * device.
	 * 
	 * @param context
	 *            the Context
	 */
	@Override
	protected void onUnregistered(Context context, String registrationId) {

	}

	/**
	 * Generate a notification intent and dispatch it to the RegisterActivity.
	 * This is how we get information from this service (non-UI) back to the
	 * activity.
	 * 
	 * For this to work, the 'android:launchMode="singleTop"' attribute needs to
	 * be set for the RegisterActivity in AndroidManifest.xml.
	 * 
	 * @param context
	 *            the application context
	 * @param message
	 *            the message to send
	 * @param isError
	 *            true if the message is an error-related message; false
	 *            otherwise
	 * @param isRegistrationMessage
	 *            true if this message is related to registration/unregistration
	 */
	private void sendNotificationIntent(Context context, String message, boolean isError,
			boolean isRegistrationMessage) {
		Intent notificationIntent = new Intent(context, RegisterActivity.class);
		notificationIntent.putExtra("gcmIntentServiceMessage", true);
		notificationIntent.putExtra("registrationMessage", isRegistrationMessage);
		notificationIntent.putExtra("error", isError);
		notificationIntent.putExtra("message", message);
		notificationIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(notificationIntent);
	}

	private String getWebSampleUrl(String endpointUrl) {
		// Not the most elegant solution; we'll improve this in the future
		if (CloudEndpointUtils.LOCAL_ANDROID_RUN) {
			return CloudEndpointUtils.LOCAL_APP_ENGINE_SERVER_URL + "index.html";
		}
		return endpointUrl.replace("/_ah/api/", "/index.html");
	}
}
