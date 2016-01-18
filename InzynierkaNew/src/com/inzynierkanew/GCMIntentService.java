package com.inzynierkanew;

import java.io.IOException;

import com.google.android.gcm.GCMBaseIntentService;
import com.google.android.gcm.GCMRegistrar;
import com.google.api.client.util.DateTime;
import com.inzynierkanew.entities.players.playerendpoint.Playerendpoint;
import com.inzynierkanew.entities.players.playerendpoint.model.Player;
import com.inzynierkanew.game.GameView;
import com.inzynierkanew.init.RegisterActivity;
import com.inzynierkanew.utils.CloudEndpointUtils;
import com.inzynierkanew.utils.RegistrationContainer;
import com.inzynierkanew.utils.TimeUtils;

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
	 * TODO: Set this to a valid project number. See
	 * http://developers.google.com/eclipse/docs/cloud_endpoints for more
	 * information.
	 */
	public static final String PROJECT_NUMBER = "891637358470";

	// TODO wymyslic lepsze rozwiazanie jak przazywac playera miedzy register a
	// onRegistered, bo to jest thread-unsafe. Jakaœ kolejka?
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

		sendNotificationIntent(context,
				"Registration with Google Cloud Messaging...FAILED!\n\n"
						+ "A Google Cloud Messaging registration error occurred (errorid: " + errorId + "). "
						+ "Do you have your project number (" + ("".equals(PROJECT_NUMBER) ? "<unset>" : PROJECT_NUMBER)
						+ ")  set correctly, and do you have Google Cloud Messaging enabled for the " + "project?",
				true, true);
	}

	/**
	 * Called when a cloud message has been received.
	 */
	@Override
	public void onMessage(Context context, Intent intent) {
		Log.i("GCM", intent.getExtras().toString());
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

			sendNotificationIntent(context,
					"1) Registration with Google Cloud Messaging...SUCCEEDED!\n\n"
							+ "2) Registration with Endpoints Server...FAILED!\n\n"
							+ "Unable to register your device with your Cloud Endpoints server running at "
							+ playerEndpoint.getRootUrl()
							+ ". Either your Cloud Endpoints server is not deployed to App Engine, or "
							+ "your settings need to be changed to run against a local instance "
							+ "by setting LOCAL_ANDROID_RUN to 'true' in CloudEndpointUtils.java.",
					true, true);
			return;
		}

		sendNotificationIntent(context,
				"1) Registration with Google Cloud Messaging...SUCCEEDED!\n\n"
						+ "2) Registration with Endpoints Server...SUCCEEDED!\n\n"
						+ "Device registration with Cloud Endpoints Server running at  " + playerEndpoint.getRootUrl()
						+ " succeeded!\n\n" + "To send a message to this device, "
						+ "open your browser and navigate to the sample application at "
						+ getWebSampleUrl(playerEndpoint.getRootUrl()),
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

		if (registrationId != null && registrationId.length() > 0) {

			try {
				playerEndpoint.removePlayerByRegistrationId(registrationId).execute();
			} catch (IOException e) {
				Log.e(GCMIntentService.class.getName(),
						"Exception received when attempting to unregister with server at "
								+ playerEndpoint.getRootUrl(),
						e);
				sendNotificationIntent(context, "1) De-registration with Google Cloud Messaging....SUCCEEDED!\n\n"
						+ "2) De-registration with Endpoints Server...FAILED!\n\n"
						+ "We were unable to de-register your device from your Cloud " + "Endpoints server running at "
						+ playerEndpoint.getRootUrl() + "." + "See your Android log for more information.", true, true);
				return;
			}
		}

		sendNotificationIntent(context,
				"1) De-registration with Google Cloud Messaging....SUCCEEDED!\n\n"
						+ "2) De-registration with Endpoints Server...SUCCEEDED!\n\n"
						+ "Device de-registration with Cloud Endpoints server running at  "
						+ playerEndpoint.getRootUrl() + " succeeded!",
				false, true);
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
