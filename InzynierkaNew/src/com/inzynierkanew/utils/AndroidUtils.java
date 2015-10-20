package com.inzynierkanew.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;

public abstract class AndroidUtils {
	
	private AndroidUtils(){
		
	}

	public static void showChoiceDialog(final Context context, final String message, final IOnChoice onChoice) {
		new AsyncTask<Void, Void, AlertDialog>() {
			@Override
			protected AlertDialog doInBackground(Void... params) {
				return new AlertDialog.Builder(context).setMessage(message).setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						onChoice.onConfirm();
						dialog.dismiss();
					}
				}).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						onChoice.onDecline();
						dialog.dismiss();
					}
				}).create();
			}
			
			@Override
			protected void onPostExecute(AlertDialog result) {
				result.show();
			}
		};
	}
	
//	public static void showDialog(final Context context, final String message, final IOnConfirm onConfirm) {
//		new AlertDialog.Builder(context).setMessage(message).setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
//			public void onClick(DialogInterface dialog, int id) {
//				onConfirm.onConfirm();
//				dialog.dismiss();
//			}
//		}).show();
//	}
	
	
}
