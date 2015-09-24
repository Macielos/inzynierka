package com.inzynierkanew.game;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import android.content.Context;
import android.graphics.Canvas;
import android.os.AsyncTask;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.inzynierkanew.entities.map.landendpoint.Landendpoint;
import com.inzynierkanew.entities.map.landendpoint.model.Land;
import com.inzynierkanew.entities.players.playerendpoint.Playerendpoint;
import com.inzynierkanew.entities.players.playerendpoint.model.LoginResponse;
import com.inzynierkanew.entities.players.playerendpoint.model.Player;
import com.inzynierkanew.messageEndpoint.MessageEndpoint;
import com.inzynierkanew.utils.CloudEndpointUtils;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

	private static final String TAG = GameView.class.getSimpleName();
	
	private MainThread thread;
	
	private MessageEndpoint messageEndpoint = CloudEndpointUtils.newMessageEndpoint();
	private Playerendpoint playerEndpoint = CloudEndpointUtils.newPlayerEndpoint();
	private Landendpoint landEndpoint = CloudEndpointUtils.newLandEndpoint();
	
	private Player player;
	private Land land;
	
	private String sessionId;
	
	public GameView(Context context, String sessionId) {
		super(context);
		this.sessionId = sessionId;
		Log.i(TAG, "sessionId: "+sessionId);
		getHolder().addCallback(this);
		thread = new MainThread(getHolder(), this);
		setFocusable(true);
		try {
			downloadGameStatus();
			Log.i(TAG, "Land id: "+land.getId());
			Log.i(TAG, land.toPrettyString());
		} catch (Exception e) {
			Log.e(TAG, "Failed to download game status from server, exception is"+e.getClass().getName()+", message is: "+e.getMessage(), e);
			return;
		}
	}
	
	private void downloadGameStatus() throws IOException, InterruptedException, ExecutionException {
		player = new AsyncTask<String, Void, Player>(){
			protected Player doInBackground(String[] params) {
				try {
					return playerEndpoint.findPlayerBySessionId(params[0]).execute();
				} catch (IOException e) {
					Log.e(TAG, "Failed to download player", e);
					return null;
				}
			};
		}.execute(sessionId).get();
		if(player==null){
			throw new RuntimeException("Player assigned to this session id not found on server");
		}
		if(player.getHero().getCurrentLandId()==null){
			throw new RuntimeException("Player assigned to this session id is in no land on server");
		}
		land = new AsyncTask<Long, Void, Land>(){
			protected Land doInBackground(Long[] params) {
				try {
					return landEndpoint.getLand(params[0]).execute();
				} catch (IOException e) {
					Log.e(TAG, "Failed to download land", e);
					return null;
				}
			};
		}.execute(player.getHero().getCurrentLandId()).get();
	}

	public void render(Canvas canvas) {
		
	}
	
	public void update() {
		
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,	int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		//thread.setRunning(true);
		//thread.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
	/*	boolean retry = true;
		while (retry) {
			try {
				thread.join();
				retry = false;
			} catch (InterruptedException e) {
				// try again shutting down the thread
			}
		}*/
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		Log.d(TAG, "Coordinates: "+event.getX()+", "+event.getY());
		return true;
	}	
}
