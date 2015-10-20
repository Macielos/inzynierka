package com.inzynierkanew.game;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicBoolean;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.AsyncTask;
import android.support.v4.view.GestureDetectorCompat;
import android.util.Log;
import android.view.GestureDetector.OnDoubleTapListener;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.google.api.client.json.GenericJson;
import com.inzynierkanew.R;
import com.inzynierkanew.entities.map.fieldtypeendpoint.Fieldtypeendpoint;
import com.inzynierkanew.entities.map.fieldtypeendpoint.model.FieldType;
import com.inzynierkanew.entities.map.landendpoint.Landendpoint;
import com.inzynierkanew.entities.map.landendpoint.model.Land;
import com.inzynierkanew.entities.map.landendpoint.model.Passage;
import com.inzynierkanew.entities.map.landendpoint.model.Town;
import com.inzynierkanew.entities.players.playerendpoint.Playerendpoint;
import com.inzynierkanew.entities.players.playerendpoint.model.Player;
import com.inzynierkanew.messageEndpoint.MessageEndpoint;
import com.inzynierkanew.model.DrawableFieldType;
import com.inzynierkanew.model.LandMap;
import com.inzynierkanew.model.HeroObject;
import com.inzynierkanew.utils.AndroidUtils;
import com.inzynierkanew.utils.CloudEndpointUtils;
import com.inzynierkanew.utils.Constants;
import com.inzynierkanew.utils.IOnChoice;
import com.inzynierkanew.utils.IOnConfirm;
import com.inzynierkanew.utils.Point;
import com.inzynierkanew.utils.TimeUtils;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

	private static final String TAG = GameView.class.getSimpleName();

	private static final long DELTA_FOR_DOUBLE_TAP = 500L;
	
	private MainThread thread;
	private GestureDetectorCompat gestureDetector; 

	private MessageEndpoint messageEndpoint = CloudEndpointUtils.newMessageEndpoint();
	private Playerendpoint playerEndpoint = CloudEndpointUtils.newPlayerEndpoint();
	private Landendpoint landEndpoint = CloudEndpointUtils.newLandEndpoint();
	private Fieldtypeendpoint fieldTypeEndpoint = CloudEndpointUtils.newFieldTypeEndpoint();

	private AtomicBoolean renderGame = new AtomicBoolean();
	
	private List<FieldType> fieldTypes;
	
	private Player player;
	private Land land;

	private HeroObject heroObject;
	private LandMap landMap;

	private String sessionId;

	private boolean scrolling = false;

	private float offsetX;
	private float offsetY;

	private float startX;
	private float startY;
	
	private long lastTouch = 0L;

	public GameView(Context context, String sessionId) {
		super(context);
		this.sessionId = sessionId;
		Log.i(TAG, "sessionId: " + sessionId);
		getHolder().addCallback(this);
		thread = new MainThread(getHolder(), this);
		//gestureDetector = new GestureDetectorCompat(getContext(), this);
		//gestureDetector.setOnDoubleTapListener(this);
		setFocusable(true);
		try {
			downloadPlayerStatus();
			downloadTypes();
			downloadNextLand(heroObject.getHero().getCurrentLandId());
			Log.i(TAG, "Land id: " + land.getId());
			Log.i(TAG, land.toPrettyString());
			renderGame.set(true);
		} catch (Exception e) {
			Log.e(TAG, "Failed to download game status from server, exception is" + e.getClass().getName() + ", message is: " + e.getMessage(),	e);
		}
	}

	private void downloadTypes() throws InterruptedException, ExecutionException {
		fieldTypes = new AsyncTask<Void, Void, List<FieldType>>(){

			@Override
			protected List<FieldType> doInBackground(Void... params) {
				try {
					return fieldTypeEndpoint.listFieldType().execute().getItems();
				} catch (IOException e) {
					Log.e(TAG, "Failed to download field types", e);
					return null;
				}
			}
			
		}.execute(null, null).get();
		if (fieldTypes == null) {
			throw new RuntimeException("Failed to download field types");
		}
	}

	private void downloadNextLand(Long id) throws InterruptedException, ExecutionException, IllegalAccessException, IllegalArgumentException, NoSuchFieldException {
		land = new AsyncTask<Long, Void, Land>() {
			protected Land doInBackground(Long[] params) {
				try {
					return landEndpoint.getLand(params[0]).execute();
				} catch (IOException e) {
					Log.e(TAG, "Failed to download land", e);
					return null;
				}
			};
		}.execute(id).get();
		landMap = new LandMap(this, land, fieldTypes);
		heroObject.setCornerCoordinates(land.getMinX(), land.getMinY());
	}
	
	private void downloadPlayerStatus() throws NumberFormatException, InterruptedException, ExecutionException {
		player = new AsyncTask<Long, Void, Player>() {
			protected Player doInBackground(Long[] params) {
				try {
					//TODO findBySession or sth
					return playerEndpoint.getPlayer(params[0]).execute();
				} catch (IOException e) {
					Log.e(TAG, "Failed to download player", e);
					return null;
				}
			};
		}.execute(Long.parseLong(sessionId)).get();
		if (player == null) {
			throw new RuntimeException("Player assigned to this session id not found on server");
		}
		if (player.getHero().getCurrentLandId() == null) {
			throw new RuntimeException("Player assigned to this session id is not in any land on server");
		}
		heroObject = new HeroObject(getPlayerBitmap(), player.getHero());
		centerCameraOnHero();
	}
	
	private void centerCameraOnHero(){
		offsetX = heroObject.getLocalX()*Constants.TILE_SIZE;
		offsetY = heroObject.getLocalY()*Constants.TILE_SIZE;
	}

	public Bitmap createBitmap(int id) {
		return BitmapFactory.decodeResource(getResources(), id);
	}
	
	private Bitmap getPlayerBitmap() {
		return BitmapFactory.decodeResource(getResources(), R.drawable.hero);
	}

	public void render(Canvas canvas) {
		//if(scrolling){	//TODO zobaczy sie, kiedy renderowac, a kiedy nie trzeba
		landMap.render(canvas);
		//}
		heroObject.render(canvas);
	}

	public void update() {
		landMap.update();
		heroObject.update();
		if(!heroObject.onObject()){
			return;
		}

		GenericJson object = landMap.getObject(heroObject.getLocalX(), heroObject.getLocalY());
		if(object==null){
			return;
		}
		
		if(object instanceof Town){
			Log.i(TAG, "Entering town");
		} else if(object instanceof Passage){
			Log.i(TAG, "Crossing passage");
			renderGame.set(true);
			final Passage passage = (Passage) object;
//			if(passage.getNextLandId()==null){
//
//				AndroidUtils.showDialog(getContext(), "Lands beyond this border are not available yet. Come back later", new IOnConfirm() {
//					
//					@Override
//					public void onConfirm() {
//						renderGame.set(true);
//					}
//				});
//			} else {
			AndroidUtils.showChoiceDialog(getContext(), "Do you want to move to the next land?", new IOnChoice() {
				
				@Override
				public void onConfirm() {
					try {
						downloadNextLand(passage.getNextLandId());
						heroObject.moveToNextLand(passage, land);
						renderGame.set(true);
					} catch (IllegalAccessException | IllegalArgumentException | NoSuchFieldException | InterruptedException | ExecutionException e) {
						Log.e(TAG, "Error while downloading next land", e);
					}
				}
				
				@Override
				public void onDecline() {
					renderGame.set(true);
				}

			});
		}
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		thread.setRunning(true);
		thread.start();
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		// TODO Auto-generated method stub
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		boolean retry = true;
		while (retry) {
			try {
				thread.join();
				retry = false;
			} catch (InterruptedException e) {
				// try again shutting down the thread
			}
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN: case MotionEvent.ACTION_POINTER_DOWN:
			Log.i(TAG, "down/pointer down");
			startX = event.getX();
			startY = event.getY();
			scrolling = true;
			if(TimeUtils.timeElapsed(lastTouch)<DELTA_FOR_DOUBLE_TAP){
				Log.i(TAG, "moving");
				Point localDestinationPoint = new Point((int)((event.getX()-offsetX)/Constants.TILE_SIZE), (int)((event.getY()-offsetY)/Constants.TILE_SIZE));
				Point localStartPoint = new Point(heroObject.getLocalX(), heroObject.getLocalY());
				if(landMap.isFieldPassable(localDestinationPoint.x, localDestinationPoint.y)){
					if(heroObject.onTheMove()){
						heroObject.haltMovement();
					}
					heroObject.setHeroCoordinates(localDestinationPoint.x, localDestinationPoint.y);
					//TODO update player in central DB via endpoint
					heroObject.setPath(landMap.findPath(localStartPoint, localDestinationPoint));
				} else {
					Log.w(TAG, "Incorrect destination point: "+localStartPoint);
				}
			}
			lastTouch = TimeUtils.now();
			break;
		case MotionEvent.ACTION_UP: case MotionEvent.ACTION_POINTER_UP:
			Log.i(TAG, "up/pointer up");
			scrolling = false;
			break;
		case MotionEvent.ACTION_MOVE:
			if (scrolling) {
				offsetX = offsetX + event.getX() - startX;
				offsetY = offsetY + event.getY() - startY;
				startX = event.getX();
				startY = event.getY();
				landMap.setOffset(offsetX, offsetY);
				heroObject.setOffset(offsetX, offsetY);
			}
			break;
		}

		Log.i(TAG, "Event coordinates: " + event.getX() + ", " + event.getY());
		Log.i(TAG, "Map offset: " + offsetX + ", " + offsetY);
		Log.i(TAG, "Current field coordinates: " + (int)((event.getX()-offsetX)/Constants.TILE_SIZE) + ", " + (int)((event.getY()-offsetY)/Constants.TILE_SIZE));
		return true;
	}
	
	public boolean renderGame(){
		return renderGame.get();
	}

//	
//    @Override
//    public boolean onDown(MotionEvent event) { 
//        Log.i(TAG,"onDown: " + event.toString()); 
//        return true;
//    }
//
//    @Override
//    public boolean onFling(MotionEvent event1, MotionEvent event2, 
//            float velocityX, float velocityY) {
//        Log.i(TAG, "onFling: " + event1.toString()+event2.toString());
//        return true;
//    }
//
//    @Override
//    public void onLongPress(MotionEvent event) {
//        Log.i(TAG, "onLongPress: " + event.toString()); 
//    }
//
//    @Override
//    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
//            float distanceY) {
//        Log.i(TAG, "onScroll: " + e1.toString()+e2.toString());
//        return true;
//    }
//
//    @Override
//    public void onShowPress(MotionEvent event) {
//        Log.i(TAG, "onShowPress: " + event.toString());
//    }
//
//    @Override
//    public boolean onSingleTapUp(MotionEvent event) {
//        Log.i(TAG, "onSingleTapUp: " + event.toString());
//        return true;
//    }
//
//    @Override
//    public boolean onDoubleTap(MotionEvent event) {
//        Log.i(TAG, "onDoubleTap: " + event.toString());
//        return true;
//    }
//
//    @Override
//    public boolean onDoubleTapEvent(MotionEvent event) {
//        Log.i(TAG, "onDoubleTapEvent: " + event.toString());
//        return true;
//    }
//
//    @Override
//    public boolean onSingleTapConfirmed(MotionEvent event) {
//        Log.i(TAG, "onSingleTapConfirmed: " + event.toString());
//        return true;
//    }

}
