package com.inzynierkanew.game;

import android.util.Log;

public abstract class Timer extends Thread {
	
	private static final long HOLD_TIME = 2000L;
	
	private static final String TAG = Timer.class.getSimpleName();
	
	@Override
	public void run() {
		try {
			Thread.sleep(HOLD_TIME);
			onExpire();
		} catch (InterruptedException e) {
			Log.e(TAG, "error sleeping");
		}

	}
	
	protected abstract void onExpire();
}
