package com.inzynierkanew.init;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.inzynierkanew.GCMIntentService;
import com.inzynierkanew.R;
import com.inzynierkanew.activities.BaseActivity;
import com.inzynierkanew.entities.players.playerendpoint.model.Player;
import com.inzynierkanew.shared.SharedConstants;
import com.inzynierkanew.utils.RegistrationContainer;
import com.inzynierkanew.utils.RequestValidator;
import com.inzynierkanew.utils.ValidationResult;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

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
public class RegisterActivity extends BaseActivity {

	enum State {
		REGISTERED, REGISTERING, UNREGISTERED
	}

	private State curState = State.UNREGISTERED;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);

		final TextView nameView = (TextView) findViewById(R.id.register_name);
		final TextView passwordView = (TextView) findViewById(R.id.register_password);

		Button nextButton = (Button) findViewById(R.id.register_nextButton);
		nextButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				final String name = nameView.getText().toString();
				final byte[] password = passwordView.getText().toString().getBytes();

				ValidationResult result = RequestValidator.validateRegisterRequest(name, password);
				if (!result.valid) {
					showDialog(result.error);
					return;
				}
				final String passwordHash = RequestValidator.hashPassword(password);

				setContentView(R.layout.activity_register_hero);

				final AtomicInteger pointsLeft = new AtomicInteger(SharedConstants.INITAL_SKILL_POINTS);
				final TextView pointsLeftView = (TextView) findViewById(R.id.register_hero_points_left);
				pointsLeftView.setText("(" + pointsLeft + " left)");

				final NumberPicker strengthNumberPicker = (NumberPicker) findViewById(
						R.id.register_hero_strength_numberpicker);
				final NumberPicker agilityNumberPicker = (NumberPicker) findViewById(
						R.id.register_hero_agility_numberpicker);
				final NumberPicker intelligenceNumberPicker = (NumberPicker) findViewById(
						R.id.register_hero_intelligence_numberPicker);

				final List<NumberPicker> numberPickers = Arrays.asList(strengthNumberPicker, agilityNumberPicker,
						intelligenceNumberPicker);

				for (NumberPicker numberPicker : numberPickers) {
					numberPicker.setMinValue(SharedConstants.INITIAL_SKILL_VALUE);
					numberPicker.setValue(SharedConstants.INITIAL_SKILL_VALUE);
					numberPicker.setMaxValue(SharedConstants.INITIAL_SKILL_VALUE + pointsLeft.get());
					numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {

						@Override
						public void onValueChange(NumberPicker changedPicker, int oldVal, int newVal) {
							pointsLeft.addAndGet(oldVal - newVal);
							pointsLeftView.setText("(" + pointsLeft + " left)");
							for (NumberPicker numberPicker : numberPickers) {
								numberPicker.setMaxValue(numberPicker.getValue() + pointsLeft.get());
							}
						}
					});
				}

				Button registerButton = (Button) findViewById(R.id.register_hero_registerButton);
				registerButton.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						if (GCMIntentService.PROJECT_NUMBER == null || GCMIntentService.PROJECT_NUMBER.length() == 0) {
							showDialog("Unable to register for Google Cloud Messaging. "
									+ "Your application's PROJECT_NUMBER field is unset! You can change "
									+ "it in GCMIntentService.java");
						} else {
							updateState(State.REGISTERING);
							try {
								Player player = new Player().setName(name).setPassword(passwordHash);
								GCMIntentService.register(getApplicationContext(),
										new RegistrationContainer(player, strengthNumberPicker.getValue(),
												agilityNumberPicker.getValue(), intelligenceNumberPicker.getValue(),
												pointsLeft.get()));
							} catch (Exception e) {
								Log.e(RegisterActivity.class.getName(),
										"Exception received when attempting to register for Google Cloud "
												+ "Messaging. Perhaps you need to set your virtual device's "
												+ " target to Google APIs? "
												+ "See https://developers.google.com/eclipse/docs/cloud_endpoints_android"
												+ " for more information.",
										e);
								showDialog("There was a problem when attempting to register for "
										+ "Google Cloud Messaging. If you're running in the emulator, "
										+ "is the target of your virtual device set to 'Google APIs?' "
										+ "See the Android log for more details.");
								updateState(State.UNREGISTERED);
							}
						}
					}
				});

				Button backButton = (Button) findViewById(R.id.register_hero_backButton);
				backButton.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						setContentView(R.layout.activity_register);
					}
				});
			}
		});

		Button cancelButton = (Button) findViewById(R.id.register_cancelButton);
		cancelButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				newActivity(LoginActivity.class);
			}

		});

	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		Log.i(TAG, intent.getExtras().toString());

		/*
		 * If we are dealing with an intent generated by the GCMIntentService
		 * class, then display the provided message.
		 */
		if (intent.getBooleanExtra("gcmIntentServiceMessage", false)) {

			showDialog(intent.getStringExtra("message"));

			if (intent.getBooleanExtra("registrationMessage", false)) {

				if (intent.getBooleanExtra("error", false)) {
					/*
					 * If we get a registration/unregistration-related error,
					 * and we're in the process of registering, then we move
					 * back to the unregistered state. If we're in the process
					 * of unregistering, then we move back to the registered
					 * state.
					 */
					if (curState == State.REGISTERING) {
						updateState(State.UNREGISTERED);
					} else {
						updateState(State.REGISTERED);
					}
				} else {
					/*
					 * If we get a registration/unregistration-related success,
					 * and we're in the process of registering, then we move to
					 * the registered state. If we're in the process of
					 * unregistering, the we move back to the unregistered
					 * state.
					 */
					if (curState == State.REGISTERING) {
						updateState(State.REGISTERED);
						newActivity(LoginActivity.class);
					} else {
						updateState(State.UNREGISTERED);
					}
				}
			}
		}
	}

	private void updateState(State newState) {
		Button registerButton = (Button) findViewById(R.id.register_hero_registerButton);
		switch (newState) {
		case REGISTERED:
			registerButton.setText("Registered");
			break;
		case REGISTERING:
			registerButton.setText("Registering...");
			registerButton.setEnabled(false);
			break;
		case UNREGISTERED:
			registerButton.setText("Register");
			registerButton.setEnabled(true);
			break;
		}
		curState = newState;

	}
}
