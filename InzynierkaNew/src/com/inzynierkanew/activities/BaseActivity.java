package com.inzynierkanew.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;

public abstract class BaseActivity extends Activity {

	protected final String TAG = this.getClass().getName();
	
	protected void newActivity(Class<? extends BaseActivity> activityClass, String... properties){
		Intent newIntent = new Intent(getApplicationContext(), activityClass);
		for(int i=0; i<properties.length-1; i+=2){
			newIntent.putExtra(properties[i], properties[i+1]);
		}
		startActivity(newIntent);
		finish();
	}
	
	protected void showDialog(String message) {
		new AlertDialog.Builder(this).setMessage(message).setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				dialog.dismiss();
			}
		}).show();
	}
	
}
