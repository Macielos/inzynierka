package com.explorersguild.init;

import com.explorersguild.activities.BaseActivity;

import android.os.Bundle;

/**
 * The Main Activity.
 * 
 * This activity starts up the LoginActivity immediately. 
 */
public class MainActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		newActivity(LoginActivity.class);

	}

}
