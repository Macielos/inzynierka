package com.inzynierkanew;

import java.io.IOException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.TextView;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.inzynierkanew.entities.players.playerendpoint.Playerendpoint;
import com.inzynierkanew.entities.players.playerendpoint.Playerendpoint.AuthenticatePlayer;
import com.inzynierkanew.entities.players.playerendpoint.model.LoginResponse;
import com.inzynierkanew.entities.players.playerendpoint.model.Player;
import com.inzynierkanew.messageEndpoint.MessageEndpoint;
import com.inzynierkanew.messageEndpoint.model.CollectionResponseMessageData;
import com.inzynierkanew.messageEndpoint.model.MessageData;

/**
 * An activity that communicates with your App Engine backend via Cloud
 * Endpoints.
 * 
 * When the user hits the "Register" button, a message is sent to the backend
 * (over endpoints) indicating that the device would like to receive broadcast
 * messages from it. Clicking "Register" also has the effect of registering this
 * device for Google Cloud Messaging (GCM). Whenever the backend wants to
 * broadcast a message, it does it via GCM, so that the device does not need to
 * keep polling the backend for messages.
 * 
 * If you've generated an App Engine backend for an existing Android project,
 * this activity will not be hooked in to your main activity as yet. You can
 * easily do so by adding the following lines to your main activity:
 * 
 * Intent intent = new Intent(this, RegisterActivity.class);
 * startActivity(intent);
 * 
 * To make the sample run, you need to set your PROJECT_NUMBER in
 * GCMIntentService.java. If you're going to be running a local version of the
 * App Engine backend (using the DevAppServer), you'll need to toggle the
 * LOCAL_ANDROID_RUN flag in CloudEndpointUtils.java. See the javadoc in these
 * classes for more details.
 * 
 * For a comprehensive walkthrough, check out the documentation at
 * http://developers.google.com/eclipse/docs/cloud_endpoints
 */
public class LoginActivity extends Activity {

	private OnTouchListener loginListener = null;
	
	private Button loginButton;
	private Button registerButton;
	private TextView nameView;
	private TextView passwordView;
	
	private Playerendpoint playerEndpoint = CloudEndpointUtils.newPlayerEndpoint();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		loginButton = (Button) findViewById(R.id.loginButton);
		registerButton = (Button) findViewById(R.id.registerButton);
		nameView = (TextView) findViewById(R.id.name);
		passwordView = (TextView) findViewById(R.id.password);

		loginButton.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction() & MotionEvent.ACTION_MASK) {
				case MotionEvent.ACTION_DOWN:
					String name = nameView.getText().toString();
					String password = passwordView.getText().toString();
					ValidationResult result = RequestValidator.validateRegisterRequest(name, password.getBytes());
					if(!result.valid){
						showDialog(result.error);
					} else {
						LoginResponse response = null;
						try {
							response = new AsyncTask<String, Void, LoginResponse>(){
								
								@Override
								protected LoginResponse doInBackground(String... params) {
									try {
										return playerEndpoint.authenticatePlayer(params[0], params[1]).execute();
									} catch (IOException e) {
										Log.e(getClass().getName(), "AsyncTask: Failed to authenticate player, exception is "+e.getClass().getName()+", "+e.getMessage(), e);
										Log.e(getClass().getName(), "Stack trace elements: "+e.getStackTrace().length);
										for(StackTraceElement el: e.getStackTrace()){
											Log.e(getClass().getName(), el.toString());
										}
										return null;
									}
								}

							}.execute(name, password).get();
						} catch (Exception e) {
							Log.e(getClass().getName(), "Failed to authenticate player, exception is "+e.getClass().getName()+", "+e, e);
						}
						if(response == null || response.getSessionId()==null){							
							showDialog("Incorrect username or password");
						} else {
							Intent gameLaunchIntent = new Intent(getApplicationContext(), GameActivity.class);
							gameLaunchIntent.putExtra("sessionId", response.getSessionId());
							startActivity(gameLaunchIntent);
							finish();
						}
					}
					return true;
				case MotionEvent.ACTION_UP:
					return true;
				default:
					return false;
				}
			}
		});
		
		registerButton.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction() & MotionEvent.ACTION_MASK) {
				case MotionEvent.ACTION_DOWN:
					Intent registerIntent = new Intent(getApplicationContext(), RegisterActivity.class);
					startActivity(registerIntent);
					finish();
				case MotionEvent.ACTION_UP:
					return true;
				default:
					return false;
				}
			}
		});
	}
	
	private void showDialog(String message) {
		new AlertDialog.Builder(this).setMessage(message).setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				dialog.dismiss();
			}
		}).show();
	}
}
