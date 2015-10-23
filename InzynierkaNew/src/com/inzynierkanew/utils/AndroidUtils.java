package com.inzynierkanew.utils;

import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;

public abstract class AndroidUtils {

	private AndroidUtils() {

	}

	public static void showChoiceDialog(final Activity activity, final String message, final IOnChoice onChoice) throws InterruptedException, ExecutionException {
		new AsyncTask<Void, Void, AlertDialog>() {
			AlertDialog.Builder alertDialog;

			protected void onPreExecute() {
				super.onPreExecute();
				Log.i("dialog", "onPreExecute");
				alertDialog = new AlertDialog.Builder(activity);
			}

			@Override
			protected AlertDialog doInBackground(Void... params) {
				return null;
			}

			@Override
			protected void onPostExecute(AlertDialog result) {
				Log.i("dialog", "onPostExecute");
				alertDialog.setMessage(message).setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						onChoice.onConfirm();
						dialog.dismiss();
					}
				}).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {
						onChoice.onDecline();
						dialog.dismiss();
					}
				}).create().show();
				// dialog.setContentView(view);
			}
		}.execute().get();

		// new
		// AlertDialog.Builder(context).setMessage(message).setPositiveButton(android.R.string.ok,
		// new DialogInterface.OnClickListener() {
		// public void onClick(DialogInterface dialog, int id) {
		// onChoice.onConfirm();
		// dialog.dismiss();
		// }
		// }).setNegativeButton(android.R.string.cancel, new
		// DialogInterface.OnClickListener() {
		// @Override
		// public void onClick(DialogInterface dialog, int id) {
		// onChoice.onDecline();
		// dialog.dismiss();
		// }
		// }).create().show();
	}

	public static void showDialog(final Activity activity, final String message, final IOnConfirm onConfirm) {
		new AlertDialog.Builder(activity).setMessage(message).setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				onConfirm.onConfirm();
				dialog.dismiss();
			}
		}).show();
	}

}
