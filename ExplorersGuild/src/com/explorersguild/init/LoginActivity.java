package com.explorersguild.init;

import java.io.IOException;

import com.explorersguild.R;
import com.explorersguild.activities.BaseActivity;
import com.explorersguild.entities.players.playerendpoint.Playerendpoint;
import com.explorersguild.entities.players.playerendpoint.model.LoginResponse;
import com.explorersguild.game.GameActivity;
import com.explorersguild.shared.RequestValidator;
import com.explorersguild.shared.ValidationResult;
import com.explorersguild.utils.CloudEndpointUtils;
import com.explorersguild.utils.Constants;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LoginActivity extends BaseActivity {

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

		loginButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				String name = nameView.getText().toString();
				String password = passwordView.getText().toString();
				ValidationResult result = RequestValidator.validateRegisterRequest(name, password.getBytes());
				if (!result.valid) {
					showDialog(result.error);
					return;
				}
				LoginResponse response = null;
				try {
					response = new AsyncTask<String, Void, LoginResponse>() {

						@Override
						protected LoginResponse doInBackground(String... params) {
							try {
								return playerEndpoint.authenticatePlayer(params[0], params[1]).execute();
							} catch (IOException e) {
								Log.e(getClass().getName(), "AsyncTask: Failed to authenticate player", e);
								return null;
							}
						}
					}.execute(name, password).get();
				} catch (Exception e) {
					Log.e(getClass().getName(),
							"Failed to authenticate player", e);
				}
				if (response == null){
					showDialog(getString(R.string.cant_reach_server));
				} else if (response.getPlayerId() == null){
					showDialog(response.getMessage()==null ? getString(R.string.problem_on_server) : response.getMessage());
				} else {	
					newActivity(GameActivity.class, Constants.PLAYER_ID, response.getPlayerId(), Constants.WELCOME_MSG, response.getMessage());
				}
			}
		});

		registerButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				newActivity(RegisterActivity.class);
			}
		});
	}

}
