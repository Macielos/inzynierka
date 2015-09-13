package com.inzynierkanew;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

/**
 * The Main Activity.
 * 
 * This activity starts up the RegisterActivity immediately, which communicates
 * with your App Engine backend using Cloud Endpoints. It also receives push
 * notifications from backend via Google Cloud Messaging (GCM).
 * 
 * Check out RegisterActivity.java for more details.
 */
public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Intent intent = new Intent(this, accountExists() ? GameActivity.class : RegisterActivity.class);
		startActivity(intent);

		finish();
	}
	
	private boolean accountExists(){
		SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
		String name = sharedPreferences.getString(Constants.NAME, null);
		String password = sharedPreferences.getString(Constants.PASSWORD, null);
		return name != null && password != null;
	}
}
