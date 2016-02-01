package com.explorersguild.game;

public abstract class Timer extends Thread {
	
	private static final long HOLD_TIME = 2000L;
	
	@Override
	public void run() {
		try {
			Thread.sleep(HOLD_TIME);
			onExpire();
		} catch (InterruptedException e) {
			//player did sth, probably raised finger before timer expired
		}

	}
	
	protected abstract void onExpire();
}
