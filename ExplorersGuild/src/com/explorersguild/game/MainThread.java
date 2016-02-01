package com.explorersguild.game;

import com.explorersguild.shared.TimeUtils;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

public class MainThread extends Thread {

	private static final String TAG = MainThread.class.getSimpleName();

	// desired fps
	private final static int MAX_FPS = 30;
	// maximum number of frames to be skipped
	private final static int MAX_FRAME_SKIPS = 5;
	// the frame period
	private final static int FRAME_PERIOD = 1000 / MAX_FPS;

	// Surface holder that can access the physical surface
	private SurfaceHolder surfaceHolder;
	// The actual view that handles inputs
	// and draws to the surface
	private GameView gameView;

	// flag to hold game state
	private boolean running;
	private boolean paused;

	public MainThread(SurfaceHolder surfaceHolder, GameView gameView) {
		super();
		this.surfaceHolder = surfaceHolder;
		this.gameView = gameView;
		running = false;
		paused = false;
	}

	@Override
	public void run() {
		Canvas canvas;
		Log.d(TAG, "Starting game loop");

		long beginTime; // the time when the cycle begun
		long timeDiff; // the time it took for the cycle to execute
		int sleepTime; // ms to sleep (<0 if we're behind)
		int framesSkipped; // number of frames being skipped

		sleepTime = 0;

		while (running) {
			if (paused) {
				try {
					Thread.sleep(FRAME_PERIOD);
				} catch (InterruptedException e) {
				}
			} else {
				canvas = null;
				// try locking the canvas for exclusive pixel editing
				// in the surface
				try {
					canvas = this.surfaceHolder.lockCanvas();
					synchronized (surfaceHolder) {
						beginTime = System.currentTimeMillis();
						framesSkipped = 0; // resetting the frames skipped
						// update game state
						this.gameView.update();
						// render state to the screen
						// draws the canvas on the panel
						this.gameView.render(canvas);
						// calculate how long did the cycle take
						timeDiff = TimeUtils.now() - beginTime;
						// calculate sleep time
						sleepTime = (int) (FRAME_PERIOD - timeDiff);

						if (sleepTime > 0) {
							// if sleepTime > 0 we're OK
							try {
								// send the thread to sleep for a short period
								// very useful for battery saving
								Thread.sleep(sleepTime);
							} catch (InterruptedException e) {
							}
						}

						while (sleepTime < 0 && framesSkipped < MAX_FRAME_SKIPS) {
							// we need to catch up
							this.gameView.update(); // update without rendering
							sleepTime += FRAME_PERIOD; // add frame period to
														// check if in next
														// frame
							framesSkipped++;
						}
					}
				} finally {
					// in case of an exception the surface is not left in
					// an inconsistent state
					if (canvas != null) {
						try {
							surfaceHolder.unlockCanvasAndPost(canvas);
						} catch (IllegalArgumentException e) {
							Log.e(TAG, "Error unlocking canvas: IllegalArgument", e);
						} catch (IllegalStateException e) {
							Log.e(TAG, "Error unlocking canvas: IllegalState", e);
						}
					}
				} 
			}
		}
	}

	public synchronized void setRunning(boolean running) {
		this.running = running;
	}

	public synchronized void setPaused(boolean paused) {
		this.paused = paused;
	}

}
