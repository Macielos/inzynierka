package com.inzynierkanew.game;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.AsyncTask;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

import com.google.api.client.json.GenericJson;
import com.inzynierkanew.R;
import com.inzynierkanew.entities.map.fieldtypeendpoint.Fieldtypeendpoint;
import com.inzynierkanew.entities.map.fieldtypeendpoint.model.FieldType;
import com.inzynierkanew.entities.map.landendpoint.Landendpoint;
import com.inzynierkanew.entities.map.landendpoint.model.Dungeon;
import com.inzynierkanew.entities.map.landendpoint.model.Land;
import com.inzynierkanew.entities.map.landendpoint.model.Passage;
import com.inzynierkanew.entities.map.townendpoint.Townendpoint;
import com.inzynierkanew.entities.map.townendpoint.model.Town;
import com.inzynierkanew.entities.players.factionendpoint.Factionendpoint;
import com.inzynierkanew.entities.players.factionendpoint.model.Faction;
import com.inzynierkanew.entities.players.heroendpoint.Heroendpoint;
import com.inzynierkanew.entities.players.heroendpoint.model.Hero;
import com.inzynierkanew.entities.players.playerendpoint.Playerendpoint;
import com.inzynierkanew.entities.players.playerendpoint.model.Player;
import com.inzynierkanew.entities.players.unittypeendpoint.Unittypeendpoint;
import com.inzynierkanew.entities.players.unittypeendpoint.model.UnitType;
import com.inzynierkanew.messageEndpoint.MessageEndpoint;
import com.inzynierkanew.model.DrawableFieldType;
import com.inzynierkanew.model.HeroObject;
import com.inzynierkanew.model.LandMap;
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

	public static final int LOOP = 0;
	public static final int SHOW_DIALOG = 1;
	public static final int DIALOG_CHOSEN = 2;

	private MainThread thread;

	private MessageEndpoint messageEndpoint = CloudEndpointUtils.newMessageEndpoint();
	private Playerendpoint playerEndpoint = CloudEndpointUtils.newPlayerEndpoint();
	private Heroendpoint heroEndpoint = CloudEndpointUtils.newHeroEndpoint();
	private Landendpoint landEndpoint = CloudEndpointUtils.newLandEndpoint();
	private Fieldtypeendpoint fieldTypeEndpoint = CloudEndpointUtils.newFieldTypeEndpoint();
	private Unittypeendpoint unitTypeEndpoint = CloudEndpointUtils.newUnitTypeEndpoint();
	private Townendpoint townEndpoint = CloudEndpointUtils.newTownEndpoint();
	private Factionendpoint factionEndpoint = CloudEndpointUtils.newFactionEndpoint();

	private AtomicInteger renderMode = new AtomicInteger(DIALOG_CHOSEN);

	private Map<Integer, DrawableFieldType> fieldTypes;
	private Map<Integer, UnitType> unitTypes;
	private Map<Integer, Faction> factions;

	private int townIndex;
	private int passageIndex;
	private int dungeonIndex;

	private Player player;
	private Land land;
	private Town town;
	private Hero hero;

	private HeroObject heroObject;
	private LandMap landMap;

	private String sessionId;

	private boolean scrolling = false;

	private float offsetX;
	private float offsetY;

	private float startX;
	private float startY;

	private int lastDialogX;
	private int lastDialogY;

	private long lastTouch = 0L;

	public GameView(Context context, String sessionId) {
		super(context);

		this.sessionId = sessionId;
		Log.i(TAG, "sessionId: " + sessionId);
		getHolder().addCallback(this);
		thread = new MainThread(getHolder(), this);
		setFocusable(true);
		
		downloadInitialData();
		downloadNextLand(heroObject.getHero().getCurrentLandId());
		try {
			Log.i(TAG, land==null ? "land is null" : land.toPrettyString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void downloadInitialData() {
		try {
			downloadPlayer();
			downloadHero();
			downloadTypes();
		} catch (Exception e) {
			Log.e(TAG, "Failed to download game status from server", e);
		}
	}

	// TODO fieldTypes->texture, a nie toLowerCase()
	private void downloadPlayer() throws NumberFormatException, InterruptedException, ExecutionException {
		player = new AsyncTask<Long, Void, Player>() {
			protected Player doInBackground(Long[] params) {
				try {
					// TODO findBySession or sth
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
	}
	
	private void downloadHero() throws NumberFormatException, InterruptedException, ExecutionException{
		hero = new AsyncTask<Long, Void, Hero>() {
			protected Hero doInBackground(Long[] params) {
				try {
					// TODO findBySession or sth
					return heroEndpoint.getHero(params[0]).execute();
				} catch (IOException e) {
					Log.e(TAG, "Failed to download hero", e);
					return null;
				}
			};
		}.execute(player.getHeroId()).get();
		if (hero == null) {
			throw new RuntimeException("Failed to download hero from server");
		}
		if (hero.getCurrentLandId() == null) {
			throw new RuntimeException("Hero is not in any land on server");
		}
		heroObject = new HeroObject(this, getPlayerBitmap(), hero);
	}

	private void downloadTypes() throws InterruptedException, ExecutionException, IllegalAccessException, IllegalArgumentException,
			NoSuchFieldException {
		List<FieldType> fieldTypeList = new AsyncTask<Void, Void, List<FieldType>>() {

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
		if (fieldTypeList == null) {
			throw new RuntimeException("Failed to download field types");
		}
		List<UnitType> unitTypeList = new AsyncTask<Void, Void, List<UnitType>>() {
			@Override
			protected List<UnitType> doInBackground(Void... params) {
				try {
					return unitTypeEndpoint.listUnitType().execute().getItems();
				} catch (IOException e) {
					Log.e(TAG, "Failed to download unit types", e);
					return null;
				}
			}

		}.execute(null, null).get();
		if (unitTypeList == null) {
			throw new RuntimeException("Failed to download field types");
		}
		List<Faction> factionList = new AsyncTask<Void, Void, List<Faction>>() {
			@Override
			protected List<Faction> doInBackground(Void... params) {
				try {
					return factionEndpoint.listFaction().execute().getItems();
				} catch (IOException e) {
					Log.e(TAG, "Failed to download unit types", e);
					return null;
				}
			}

		}.execute(null, null).get();
		if (factionList == null) {
			throw new RuntimeException("Failed to download factions");
		}
		fieldTypes = createFieldtypes(fieldTypeList);
		unitTypes = createUnitTypes(unitTypeList);
		factions = createFactions(factionList);
	}

	private void downloadNextLand(Long id) {
		try {
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
			town = new AsyncTask<Long, Void, Town>() {
				protected Town doInBackground(Long[] params) {
					try {
						return townEndpoint.getTown(params[0]).execute();
					} catch (IOException e) {
						Log.e(TAG, "Failed to download town", e);
						return null;
					}
				};
			}.execute(land.getTownId()).get();
			if(land==null){
				throw new RuntimeException("Land is null");
			}
			if(town==null){
				throw new RuntimeException("Town is null");
			}
			landMap = new LandMap(this, land, town, fieldTypes, townIndex, passageIndex, dungeonIndex);
			centerCameraOnHero();
		} catch (InterruptedException | ExecutionException | IllegalAccessException | IllegalArgumentException | NoSuchFieldException e) {
			Log.e(TAG, "Failed to download land in an async task", e);
		}

//		com.inzynierkanew.entities.map.townendpoint.model.Town town = null;
//		try {
//			town = townEndpoint.getTown(land.getTown().getKey().getId()).execute();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} finally {
//			try {
//				Log.i(TAG, town.toPrettyString());
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
		
	}

	private void centerCameraOnHero() {
		heroObject.setCornerCoordinates(land.getMinX(), land.getMinY());
		DisplayMetrics size = getSize();
		offsetX = -heroObject.getLocalX() * Constants.TILE_SIZE + size.widthPixels / 2;
		offsetY = -heroObject.getLocalY() * Constants.TILE_SIZE + size.heightPixels / 2;
		setLastDialogOnHeroLocation();
	}

	public Bitmap createBitmap(int id) {
		return BitmapFactory.decodeResource(getResources(), id);
	}

	private Bitmap getPlayerBitmap() {
		return BitmapFactory.decodeResource(getResources(), R.drawable.hero);
	}

	private DisplayMetrics getSize() {
		DisplayMetrics metrics = new DisplayMetrics();
		((WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(metrics);
		return metrics;
	}

	public void render(Canvas canvas) {
		// if(scrolling){ //TODO zobaczy sie, kiedy renderowac, a kiedy nie
		// trzeba
		landMap.render(canvas);
		// }
		heroObject.render(canvas);
	}

	public void update() {
		landMap.update();
		heroObject.update();
		if (!heroObject.onObject()) {
			return;
		}

		if (heroObject.onTheMove()) {
			return;
		}

		if (renderMode.get() == DIALOG_CHOSEN && (heroObject.getLocalX() != lastDialogX || heroObject.getLocalY() != lastDialogY)) {
			renderMode.set(LOOP);
		}

		if (renderMode.get() != LOOP) {
			return;
		}

		final GenericJson object = landMap.getObject(heroObject.getLocalX(), heroObject.getLocalY());
		if (object == null) {
			return;
		}

		// TODO wchodzenie do miasta/passagu - onClick?
		if (object instanceof Town) {
			Log.i(TAG, "Entering town");
			renderMode.set(SHOW_DIALOG);
			thread.setRunning(false);
			final GameActivity activity = (GameActivity) AndroidUtils.getActivity(getContext());
			activity.runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					activity.initTownView();
				}
			});
			//thread.interrupt();
			// getHolder().removeCallback(this);
			//GameActivity activity = (GameActivity) AndroidUtils.getActivity(getContext());
			//activity.enterTown();
		} else if (object instanceof Dungeon) {
			Log.i(TAG, "Entering dungeon");
			renderMode.set(SHOW_DIALOG);
			final GameActivity activity = (GameActivity) AndroidUtils.getActivity(getContext());
			activity.runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					activity.showDungeonDialog((Dungeon) object);
				}
			});
		} else if (object instanceof Passage) {
			Log.i(TAG, "Crossing passage");
			renderMode.set(SHOW_DIALOG);
			final Passage passage = (Passage) object;
			try {
				if (passage.getNextLandId() == null) {

					AndroidUtils.showDialog(AndroidUtils.getActivity(getContext()),
							"Lands beyond this border are not available yet. Come back later", new IOnConfirm() {

								@Override
								public void onConfirm() {
									renderMode.set(DIALOG_CHOSEN);
									lastDialogX = heroObject.getLocalX();
									lastDialogY = heroObject.getLocalY();
								}
							});
				} else {
					AndroidUtils.showChoiceDialog(AndroidUtils.getActivity(getContext()), "Do you want to move to the next land?",
							new IOnChoice() {

								@Override
								public void onConfirm() {
									//try {
										thread.setPaused(true);
										downloadNextLand(passage.getNextLandId());
										heroObject.moveToNextLand(passage, land);
										try {
											playerEndpoint.moveHeroToDifferentLand(heroObject.getHero().getId(),
													passage.getNextLandId(), passage.getNextX(), passage.getNextY());
										} catch (IOException e) {
											Log.e(TAG, "Failed to move Hero to land " + passage.getNextLandId(), e);
										}
										renderMode.set(DIALOG_CHOSEN);
										lastDialogX = heroObject.getLocalX();
										lastDialogY = heroObject.getLocalY();
										thread.setPaused(false);
//									} catch (IllegalArgumentException e) {
//										Log.e(TAG, "Error while downloading next land", e);
//									}
								}

								@Override
								public void onDecline() {
									renderMode.set(DIALOG_CHOSEN);
									lastDialogX = heroObject.getLocalX();
									lastDialogY = heroObject.getLocalY();
								}

							});
				}
			} catch (Exception e) {
				Log.e(TAG, "Failed to display dialog", e);
			}
		}
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		thread = new MainThread(getHolder(), this);
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
		thread.setRunning(false);
		// boolean retry = true;
		// while (retry) {
		// try {
		// thread.join();
		// retry = false;
		// } catch (InterruptedException e) {
		// // try again shutting down the thread
		// }
		// }
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
		case MotionEvent.ACTION_POINTER_DOWN:
			Log.i(TAG, "down/pointer down");
			startX = event.getX();
			startY = event.getY();
			scrolling = true;
			if (TimeUtils.timeElapsed(lastTouch) < DELTA_FOR_DOUBLE_TAP) {
				Point localDestinationPoint = new Point((int) ((event.getX() - offsetX) / Constants.TILE_SIZE),
						(int) ((event.getY() - offsetY) / Constants.TILE_SIZE));
				Point localStartPoint = new Point(heroObject.getLocalX(), heroObject.getLocalY());

//				if (localDestinationPoint.equals(localStartPoint)
//						&& landMap.getObject(heroObject.getLocalX(), heroObject.getLocalY()) instanceof Town) {
//					Log.i(TAG, "Enter town onClick");
//					GameActivity activity = (GameActivity) AndroidUtils.getActivity(getContext());
//					activity.enterTown();
//					return true;
//				}
				if (landMap.isFieldPassable(localDestinationPoint.x, localDestinationPoint.y)) {
					if (heroObject.onTheMove()) {
						heroObject.haltMovement();
					}
					heroObject.setHeroCoordinates(localDestinationPoint.x, localDestinationPoint.y);
					heroObject.setPath(landMap.findPath(localStartPoint, localDestinationPoint));
					try {
						playerEndpoint.updateHeroPosition(heroObject.getHero().getId(),
								localDestinationPoint.x - landMap.getCornerX(), localDestinationPoint.y - landMap.getCornerY());
					} catch (IOException e) {
						Log.e(TAG, "Failed to move Hero to position " + localDestinationPoint, e);
					}
				} else {
					Log.w(TAG, "Incorrect destination point: " + localStartPoint);
				}
			}
			lastTouch = TimeUtils.now();
			break;
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_POINTER_UP:
			Log.i(TAG, "up/pointer up");
			scrolling = false;
			break;
		case MotionEvent.ACTION_MOVE:
			if (scrolling) {
				offsetX = offsetX + event.getX() - startX;
				offsetY = offsetY + event.getY() - startY;
				startX = event.getX();
				startY = event.getY();
			}
			break;
		}

		Log.i(TAG, "Event coordinates: " + event.getX() + ", " + event.getY());
		Log.i(TAG, "Map offset: " + offsetX + ", " + offsetY);
		Log.i(TAG, "Current field coordinates: " + (int) ((event.getX() - offsetX) / Constants.TILE_SIZE) + ", "
				+ (int) ((event.getY() - offsetY) / Constants.TILE_SIZE));
		return true;
	}

	private Map<Integer, UnitType> createUnitTypes(List<UnitType> unitTypes) {
		Map<Integer, UnitType> map = new HashMap<>(unitTypes.size());
		for (UnitType unitType : unitTypes) {
			map.put(unitType.getId().intValue(), unitType);
		}
		return map;
	}
	
	private Map<Integer, Faction> createFactions(List<Faction> factions) {
		Map<Integer, Faction> map = new HashMap<>(factions.size());
		for(Faction faction: factions){
			map.put(faction.getId().intValue(), faction);
		}
		return map;
	}

	private Map<Integer, DrawableFieldType> createFieldtypes(List<FieldType> fieldTypes) throws IllegalAccessException,
			IllegalArgumentException, NoSuchFieldException {
		Map<Integer, DrawableFieldType> fieldTypeMap = new HashMap<>(fieldTypes.size());
		for (FieldType fieldType : fieldTypes) {
			fieldTypeMap.put(fieldType.getId().intValue(),
					new DrawableFieldType(fieldType, createBitmap(R.drawable.class.getField(fieldType.getTexture()).getInt(null))));
			if (fieldType.getName().equals("Town")) {
				townIndex = fieldType.getId().intValue();
			}
			if (fieldType.getName().equals("Passage")) {
				passageIndex = fieldType.getId().intValue();
			}
			if (fieldType.getName().equals("Dungeon")) {
				dungeonIndex = fieldType.getId().intValue();
			}
		}
		return fieldTypeMap;
	}

	public float getOffsetX() {
		return offsetX;
	}

	public float getOffsetY() {
		return offsetY;
	}

	public Player getPlayer() {
		return player;
	}
	
	public Hero getHero() {
		return hero;
	}
	
	public Long getLandId(){
		return land == null ? null : land.getId();
	}
	
	public Long getTownId(){
		return land.getTownId();
	}

	public Map<Integer, DrawableFieldType> getFieldTypes() {
		return fieldTypes;
	}

	public Map<Integer, UnitType> getUnitTypes() {
		return unitTypes;
	}
	
	public Map<Integer, Faction> getFactions() {
		return factions;
	}

	public void setRenderMode(int renderMode){
		this.renderMode.set(renderMode);
	}
	
	public void setLastDialogOnHeroLocation(){
		lastDialogX = heroObject.getLocalX();
		lastDialogY = heroObject.getLocalY();
	}
}
