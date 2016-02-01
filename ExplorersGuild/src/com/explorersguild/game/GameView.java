package com.explorersguild.game;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;

import com.explorersguild.R;
import com.explorersguild.entities.map.dungeonendpoint.Dungeonendpoint;
import com.explorersguild.entities.map.dungeonendpoint.model.Dungeon;
import com.explorersguild.entities.map.dungeonvisitendpoint.Dungeonvisitendpoint;
import com.explorersguild.entities.map.dungeonvisitendpoint.model.DungeonVisit;
import com.explorersguild.entities.map.fieldtypeendpoint.Fieldtypeendpoint;
import com.explorersguild.entities.map.fieldtypeendpoint.model.FieldType;
import com.explorersguild.entities.map.landendpoint.Landendpoint;
import com.explorersguild.entities.map.landendpoint.model.Land;
import com.explorersguild.entities.map.passageendpoint.Passageendpoint;
import com.explorersguild.entities.map.passageendpoint.model.Passage;
import com.explorersguild.entities.map.townendpoint.Townendpoint;
import com.explorersguild.entities.map.townendpoint.model.Town;
import com.explorersguild.entities.map.townvisitendpoint.Townvisitendpoint;
import com.explorersguild.entities.map.townvisitendpoint.model.TownVisit;
import com.explorersguild.entities.players.factionendpoint.Factionendpoint;
import com.explorersguild.entities.players.factionendpoint.model.Faction;
import com.explorersguild.entities.players.heroendpoint.Heroendpoint;
import com.explorersguild.entities.players.heroendpoint.model.Hero;
import com.explorersguild.entities.players.itemtypeendpoint.Itemtypeendpoint;
import com.explorersguild.entities.players.itemtypeendpoint.model.ItemType;
import com.explorersguild.entities.players.playerendpoint.Playerendpoint;
import com.explorersguild.entities.players.playerendpoint.model.Player;
import com.explorersguild.entities.players.unittypeendpoint.Unittypeendpoint;
import com.explorersguild.entities.players.unittypeendpoint.model.UnitType;
import com.explorersguild.model.DrawableFieldType;
import com.explorersguild.model.HeroModel;
import com.explorersguild.model.IRenderable;
import com.explorersguild.model.LandModel;
import com.explorersguild.shared.SharedConstants;
import com.explorersguild.shared.StringUtils;
import com.explorersguild.shared.TimeUtils;
import com.explorersguild.utils.AndroidUtils;
import com.explorersguild.utils.CloudEndpointUtils;
import com.explorersguild.utils.IOnChoice;
import com.explorersguild.utils.IOnConfirm;
import com.explorersguild.utils.Point;
import com.google.api.client.json.GenericJson;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.os.AsyncTask;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

public class GameView extends SurfaceView implements SurfaceHolder.Callback, IRenderable {

	private static final String TAG = GameView.class.getSimpleName();

	private static final long DELTA_FOR_DOUBLE_TAP = 500L;

	public static final int LOOP = 0;
	public static final int SHOW_DIALOG = 1;
	public static final int DIALOG_CHOSEN = 2;

	public enum ScreenMode {
		STATIC, SCROLLING, ZOOMING
	};

	public enum RenderMode {
		LOOP, SHOW_DIALOG, DIALOG_CHOSEN
	};

	private MainThread thread;

	private final Playerendpoint playerEndpoint = CloudEndpointUtils.newPlayerEndpoint();
	private final Heroendpoint heroEndpoint = CloudEndpointUtils.newHeroEndpoint();
	private final Landendpoint landEndpoint = CloudEndpointUtils.newLandEndpoint();
	private final Dungeonendpoint dungeonEndpoint = CloudEndpointUtils.newDungeonEndpoint();
	private final Passageendpoint passageEndpoint = CloudEndpointUtils.newPassageEndpoint();
	private final Dungeonvisitendpoint dungeonVisitEndpoint = CloudEndpointUtils.newDungeonVisitEndpoint();
	private final Fieldtypeendpoint fieldTypeEndpoint = CloudEndpointUtils.newFieldTypeEndpoint();
	private final Unittypeendpoint unitTypeEndpoint = CloudEndpointUtils.newUnitTypeEndpoint();
	private final Townendpoint townEndpoint = CloudEndpointUtils.newTownEndpoint();
	private final Townvisitendpoint townVisitEndpoint = CloudEndpointUtils.newTownVisitEndpoint();
	private final Factionendpoint factionEndpoint = CloudEndpointUtils.newFactionEndpoint();
	private final Itemtypeendpoint itemEndpoint = CloudEndpointUtils.newItemEndpoint();

	private RenderMode renderMode = RenderMode.DIALOG_CHOSEN;
	private ScreenMode screenMode = ScreenMode.STATIC;
	private Random random = new Random();

	private Timer timer;

	private Map<Integer, DrawableFieldType> fieldTypes;
	private Map<Integer, UnitType> unitTypes;
	private Map<Integer, Faction> factions;

	private Map<String, List<ItemType>> itemCache;
	private List<ItemType> heroInventory;

	private Player player;
	private Land land;
	private Town town;
	private Hero hero;
	private List<Dungeon> dungeons;
	private List<Passage> passages;
	private TownVisit townVisit;
	private Map<Long, DungeonVisit> dungeonVisitHistory;

	private HeroModel heroModel;
	private Map<Long, HeroModel> otherHeroModels;
	private LandModel landModel;

	private int[] xpsForNextLevel;
	
	private Bitmap[] heroBitmaps = new Bitmap[4];

	private String playerId;

	private float tileSize;

	private float offsetX;
	private float offsetY;

	private float startX;
	private float startY;

	private int lastDialogX;
	private int lastDialogY;

	private float zoom = 1.0f;

	private float previousDistance;
	private long lastTouch = 0L;

	private PointF midPoint;

	private float newDistance;

	public GameView(Context context, String playerId) {
		super(context);

		this.playerId = playerId;
		getHolder().addCallback(this);

		setFocusable(true);

		xpsForNextLevel = new int[SharedConstants.MAX_HERO_LEVEL];
		xpsForNextLevel[0] = 0;

		int xpForCurrentLevel = SharedConstants.BASE_XP_PER_LEVEL;
		for (int i = 1; i < SharedConstants.MAX_HERO_LEVEL; ++i) {
			xpsForNextLevel[i] = xpsForNextLevel[i - 1] + xpForCurrentLevel;
			xpForCurrentLevel += SharedConstants.NEXT_LEVEL_FACTOR;
		}

		tileSize = getSize().heightPixels / 10;
		
		heroBitmaps = createHeroBitmaps();

		downloadInitialData();
		downloadNextLand(heroModel.getHero().getCurrentLandId());
	}

	private void downloadInitialData() {
		try {
			downloadPlayer();
			downloadTypes();
			downloadHero();
		} catch (Exception e) {
			Log.e(TAG, "Failed to download game status from server", e);
		}
	}

	private void downloadPlayer() throws NumberFormatException, InterruptedException, ExecutionException {
		player = new AsyncTask<Long, Void, Player>() {
			protected Player doInBackground(Long[] params) {
				try {
					return playerEndpoint.getPlayer(params[0]).execute();
				} catch (IOException e) {
					Log.e(TAG, "Failed to download player", e);
					return null;
				}
			};
		}.execute(Long.parseLong(playerId)).get();
		if (player == null) {
			throw new RuntimeException("Player assigned to this session id not found on server");
		}
	}

	private void downloadHero() throws NumberFormatException, InterruptedException, ExecutionException {
		hero = new AsyncTask<Long, Void, Hero>() {
			protected Hero doInBackground(Long[] params) {
				try {
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
		heroInventory = hero.getItems() == null ? new ArrayList<ItemType>(0) : new AsyncTask<Void, Void, List<ItemType>>() {
			protected List<ItemType> doInBackground(Void[] params) {
				try {
					return itemEndpoint.getItemTypes(StringUtils.join(hero.getItems())).execute().getItems();
				} catch (IOException e) {
					Log.e(TAG, "Failed to download items", e);
					return null;
				}
			};
		}.execute().get();
		heroModel = new HeroModel(this, heroBitmaps, hero);
	}

	private void downloadTypes() throws InterruptedException, ExecutionException, IllegalAccessException,
			IllegalArgumentException, NoSuchFieldException {

		AsyncTask<Void, Void, List<FieldType>> fieldTypeTask = new AsyncTask<Void, Void, List<FieldType>>() {

			@Override
			protected List<FieldType> doInBackground(Void... params) {
				try {
					return fieldTypeEndpoint.listFieldType().execute().getItems();
				} catch (IOException e) {
					Log.e(TAG, "Failed to download field types", e);
					return null;
				}
			}

		}.execute();

		AsyncTask<Void, Void, List<UnitType>> unitTypeTask = new AsyncTask<Void, Void, List<UnitType>>() {
			@Override
			protected List<UnitType> doInBackground(Void... params) {
				try {
					return unitTypeEndpoint.listUnitType().execute().getItems();
				} catch (IOException e) {
					Log.e(TAG, "Failed to download unit types", e);
					return null;
				}
			}

		}.execute();

		AsyncTask<Void, Void, List<Faction>> factionTask = new AsyncTask<Void, Void, List<Faction>>() {
			@Override
			protected List<Faction> doInBackground(Void... params) {
				try {
					return factionEndpoint.listFaction().execute().getItems();
				} catch (IOException e) {
					Log.e(TAG, "Failed to download factions", e);
					return null;
				}
			}
		}.execute();

		List<FieldType> fieldTypeList = fieldTypeTask.get();
		List<UnitType> unitTypeList = unitTypeTask.get();
		List<Faction> factionList = factionTask.get();
		if (fieldTypeList == null) {
			throw new RuntimeException("Failed to download field types");
		}
		if (unitTypeList == null) {
			throw new RuntimeException("Failed to download unit types");
		}
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
			if (land == null) {
				throw new RuntimeException("Land is null");
			}

			boolean hasTown = land.getTownId() != null;

			AsyncTask<Long, Void, Town> townTask = null;
			if (hasTown) {
				townTask = new AsyncTask<Long, Void, Town>() {
					protected Town doInBackground(Long[] params) {
						try {
							return townEndpoint.getTown(params[0]).execute();
						} catch (IOException e) {
							Log.e(TAG, "Failed to download town", e);
							return null;
						}
					};
				}.execute(land.getTownId());
			}

			AsyncTask<Void, Void, List<Passage>> passagesTask = new AsyncTask<Void, Void, List<Passage>>() {
				protected List<Passage> doInBackground(Void[] params) {
					try {
						return passageEndpoint.listPassage(land.getId()).execute().getItems();
					} catch (IOException e) {
						Log.e(TAG, "Failed to download dungeons", e);
						return null;
					}
				};
			}.execute();

			
			AsyncTask<Void, Void, List<Dungeon>> dungeonsTask = new AsyncTask<Void, Void, List<Dungeon>>() {
				protected List<Dungeon> doInBackground(Void[] params) {
					try {
						return dungeonEndpoint.listDungeon(land.getId()).execute().getItems();
					} catch (IOException e) {
						Log.e(TAG, "Failed to download dungeons", e);
						return null;
					}
				};
			}.execute();

			AsyncTask<Void, Void, List<Hero>> otherHeroesTask = new AsyncTask<Void, Void, List<Hero>>() {
				protected List<Hero> doInBackground(Void[] params) {
					try {
						return heroEndpoint.getActiveHeroesInLand(land.getId()).execute().getItems();
					} catch (IOException e) {
						Log.e(TAG, "Failed to download other heroes", e);
						return null;
					}
				};
			}.execute();

			town = hasTown ? townTask.get() : null;
			passages = passagesTask.get();
			dungeons = dungeonsTask.get();
			List<Hero> otherHeroes = otherHeroesTask.get();
			if (otherHeroes == null) {
				otherHeroes = new ArrayList<>(0);
			}
			if (hasTown && town == null) {
				throw new RuntimeException("Town is null");
			}
			if (dungeons == null) {
				throw new RuntimeException("Dungeon list is null");
			}

			AsyncTask<Void, Void, List<DungeonVisit>> dungeonVisitsTask = new AsyncTask<Void, Void, List<DungeonVisit>>() {
				protected List<DungeonVisit> doInBackground(Void[] params) {
					try {
						return dungeonVisitEndpoint.listDungeonVisitsByIds(hero.getId(), land.getId(), true).execute()
								.getItems();
					} catch (IOException e) {
						Log.e(TAG, "Failed to download dungeon visit history", e);
						return null;
					}
				};
			}.execute();

			AsyncTask<Void, Void, TownVisit> townVisitTask = null;
			if (hasTown) {
				townVisitTask = new AsyncTask<Void, Void, TownVisit>() {
					protected TownVisit doInBackground(Void[] params) {
						try {
							return townVisitEndpoint.getTownVisit(town.getId(), hero.getId()).execute();
						} catch (IOException e) {
							Log.e(TAG, "Failed to download town visit history", e);
							return null;
						}
					};
				}.execute();
			}

			AsyncTask<Void, Void, List<ItemType>> standardItemsTask = downloadItems(SharedConstants.STANDARD, SharedConstants.STANDARD_ITEM_STOCK_FACTOR);
			AsyncTask<Void, Void, List<ItemType>> magicalItemsTask = downloadItems(SharedConstants.MAGICAL, SharedConstants.MAGICAL_ITEM_STOCK_FACTOR);
			AsyncTask<Void, Void, List<ItemType>> legendaryItemsTask = downloadItems(SharedConstants.LEGENDARY, SharedConstants.LEGENDARY_ITEM_STOCK_FACTOR);

			dungeonVisitHistory = createDungeonVisitHistory(dungeonVisitsTask.get());
			townVisit = hasTown ? townVisitTask.get() : null;
			otherHeroModels = new ConcurrentHashMap<>();
			HeroModel otherHeroModel;
			for (Hero otherHero : otherHeroes) {
				if (otherHero.getId().longValue() != heroModel.getHero().getId().longValue()) {
					otherHeroModel = new HeroModel(this, heroBitmaps, otherHero);
					otherHeroModel.setCornerCoordinates(land.getMinX(), land.getMinY());
					otherHeroModels.put(otherHero.getId(), otherHeroModel);
				}
			}

			itemCache = new HashMap<>(3);
			itemCache.put(SharedConstants.STANDARD, standardItemsTask.get());
			itemCache.put(SharedConstants.MAGICAL, magicalItemsTask.get());
			itemCache.put(SharedConstants.LEGENDARY, legendaryItemsTask.get());

			landModel = new LandModel(this, land, town, passages, dungeons, fieldTypes);
			centerCameraOnHero();
		} catch (InterruptedException | ExecutionException | IllegalAccessException | IllegalArgumentException
				| NoSuchFieldException e) {
			Log.e(TAG, "Failed to download land in an async task", e);
		}
	}

	private AsyncTask<Void, Void, List<ItemType>> downloadItems(final String itemClass, final double factor) {
		return new AsyncTask<Void, Void, List<ItemType>>() {
			protected List<ItemType> doInBackground(Void[] params) {
				try {
					return itemEndpoint.getRandomItemsByType(
							(int) ((double) dungeons.size() * factor),
							hero.getLevel(), itemClass).execute().getItems();
				} catch (IOException e) {
					Log.e(TAG, "Failed to download items", e);
					return null;
				}
			};
		}.execute();
	}

	private void centerCameraOnHero() {
		heroModel.setCornerCoordinates(land.getMinX(), land.getMinY());
		DisplayMetrics size = getSize();
		offsetX = -heroModel.getLocalX() * tileSize + size.widthPixels / 2;
		offsetY = -heroModel.getLocalY() * tileSize + size.heightPixels / 2;
		setLastDialogOnHeroLocation();
	}

	public Bitmap createBitmap(int id, double scale) {
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(), id);
		return Bitmap.createScaledBitmap(bitmap, (int) (tileSize * scale * zoom), (int) (tileSize * scale * zoom),
				false);
	}

	public Bitmap[] createHeroBitmaps() {
		Bitmap playerBitmaps[] = new Bitmap[4];
		playerBitmaps[0] = createPlayerBitmap(R.drawable.hero_up);
		playerBitmaps[1] = createPlayerBitmap(R.drawable.hero_down);
		playerBitmaps[2] = createPlayerBitmap(R.drawable.hero_left);
		playerBitmaps[3] = createPlayerBitmap(R.drawable.hero_right);
		return playerBitmaps;
	}
	
	private Bitmap createPlayerBitmap(int id){
		return Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), id), (int) (tileSize * zoom), (int) (tileSize * zoom), false);
	}

	private DisplayMetrics getSize() {
		DisplayMetrics metrics = new DisplayMetrics();
		((WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(metrics);
		return metrics;
	}

	public void render(Canvas canvas) {
		if (canvas != null) {
			canvas.save();
			if(midPoint!=null){
				canvas.scale(zoom, zoom, midPoint.x, midPoint.y);
			}
			landModel.render(canvas);
			heroModel.render(canvas);
			for (HeroModel otherHeroModel : otherHeroModels.values()) {
				otherHeroModel.render(canvas);
			}
			canvas.restore();
		}
	}

	public void update() {
		landModel.update();
		heroModel.update();
		for (HeroModel otherHeroModel : otherHeroModels.values()) {
			otherHeroModel.update();
		}
		if (!heroModel.onObject()) {
			return;
		}

		if (heroModel.onTheMove()) {
			return;
		}

		if (renderMode == RenderMode.DIALOG_CHOSEN
				&& (heroModel.getLocalX() != lastDialogX || heroModel.getLocalY() != lastDialogY)) {
			renderMode = RenderMode.LOOP;
		}

		if (renderMode != RenderMode.LOOP) {
			return;
		}

		final GenericJson object = landModel.getObject(heroModel.getLocalX(), heroModel.getLocalY());
		if (object != null) {
			interact(object);
		}
	}

	private void interact(final GenericJson object) {
		if (object instanceof Town) {
			Log.i(TAG, "Entering town");
			renderMode = RenderMode.SHOW_DIALOG;
			thread.setRunning(false);
			final GameActivity activity = (GameActivity) AndroidUtils.getActivity(getContext());
			activity.runOnUiThread(new Runnable() {

				@Override
				public void run() {
					activity.initTownView();
				}
			});
		} else if (object instanceof Dungeon) {
			Log.i(TAG, "Entering dungeon");
			renderMode = RenderMode.SHOW_DIALOG;
			final GameActivity activity = (GameActivity) AndroidUtils.getActivity(getContext());
			activity.runOnUiThread(new Runnable() {

				@Override
				public void run() {
					activity.showDungeonDialog(((Dungeon) object).getId());
				}
			});
		} else if (object instanceof Passage) {
			Log.i(TAG, "Crossing passage");
			renderMode = RenderMode.SHOW_DIALOG;
			final Passage passage = (Passage) object;
			try {
				if (passage.getNextLandId() == null) {

					AndroidUtils.showDialog(AndroidUtils.getActivity(getContext()),
							AndroidUtils.getActivity(getContext()).getString(R.string.not_available), new IOnConfirm() {

								@Override
								public void onConfirm() {
									renderMode = RenderMode.DIALOG_CHOSEN;
									lastDialogX = heroModel.getLocalX();
									lastDialogY = heroModel.getLocalY();
								}
							});
				} else {
					AndroidUtils.showChoiceDialog(AndroidUtils.getActivity(getContext()),
							AndroidUtils.getActivity(getContext()).getString(R.string.move_to_land), new IOnChoice() {

								@Override
								public void onConfirm() {
									thread.setPaused(true);
									downloadNextLand(passage.getNextLandId());
									heroModel.moveToNextLand(passage, land);
									new AsyncTask<Void, Void, Void>() {

										@Override
										protected Void doInBackground(Void... params) {
											try {
												heroEndpoint.moveHeroToDifferentLand(heroModel.getHero().getId(),
														passage.getNextLandId(), passage.getNextX(), passage.getNextY())
														.execute();
											} catch (IOException e) {
												Log.e(TAG, "Failed to move Hero to land " + passage.getNextLandId(), e);
											}
											return null;
										}

									}.execute();

									renderMode = RenderMode.DIALOG_CHOSEN;
									lastDialogX = heroModel.getLocalX();
									lastDialogY = heroModel.getLocalY();
									thread.setPaused(false);
								}

								@Override
								public void onDecline() {
									renderMode = RenderMode.DIALOG_CHOSEN;
									lastDialogX = heroModel.getLocalX();
									lastDialogY = heroModel.getLocalY();
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
		thread = new MainThread(getHolder(), this);
		thread.setRunning(true);
		thread.start();
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		thread.setRunning(false);
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
		switch (event.getActionMasked()) {
		case MotionEvent.ACTION_DOWN:
			Log.i(TAG, "down");
			startX = event.getX();
			startY = event.getY();
			screenMode = ScreenMode.SCROLLING;
			final Point localDestinationPoint = new Point((int) ((event.getX() - offsetX) / tileSize),
					(int) ((event.getY() - offsetY) / tileSize));
			Point localStartPoint = new Point(heroModel.getLocalX(), heroModel.getLocalY());
			if (TimeUtils.timeElapsed(lastTouch) < DELTA_FOR_DOUBLE_TAP) {

				if (landModel.isFieldPassable(localDestinationPoint.x, localDestinationPoint.y)) {
					GenericJson object = landModel.getObject(localDestinationPoint);
					if (localStartPoint.equals(localDestinationPoint) && object != null) {
						interact(object);
					} else {
						if (heroModel.onTheMove()) {
							heroModel.haltMovement();
						}
						heroModel.setHeroCoordinates(localDestinationPoint.x, localDestinationPoint.y);
						heroModel.setPath(findPath(localStartPoint, localDestinationPoint));
						new AsyncTask<Void, Void, Void>() {

							@Override
							protected Void doInBackground(Void... params) {
								try {
									heroEndpoint.updateHero(SharedConstants.MOVE, heroModel.getHero()).execute();
								} catch (IOException e) {
									Log.e(TAG, "failed to move hero to position " + localDestinationPoint, e);
								}
								return null;
							}
						}.execute();
						Log.i("hero", "Hero moved to " + localDestinationPoint);
					}
				} else {
					Log.w(TAG, "Incorrect destination point: " + localDestinationPoint);
				}
			} else {
				if (timer != null) {
					timer.interrupt();
				}

				timer = new Timer() {

					@Override
					protected void onExpire() {
						try {
							if (screenMode == ScreenMode.SCROLLING) {
								AndroidUtils.showDialog(AndroidUtils.getActivity(getContext()),
										getFieldInfo(localDestinationPoint.x, localDestinationPoint.y));
							}
						} catch (InterruptedException e) {

						} catch (ExecutionException e) {
							Log.e(TAG, "error showing dialog", e);
						}
					}
				};
				timer.start();
			}
			lastTouch = TimeUtils.now();
			break;
		case MotionEvent.ACTION_POINTER_DOWN:
			Log.i(TAG, "pointer down");
			previousDistance = distance(event);
			if (previousDistance > 10f) {
				midPoint = midpoint(event);
				screenMode = ScreenMode.ZOOMING;
			}
			break;
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_POINTER_UP:
			Log.i(TAG, "up/pointer up");
			if (timer != null) {
				timer.interrupt();
			}
			timer = null;
			if(screenMode == ScreenMode.ZOOMING){
				previousDistance = newDistance;
			}
			screenMode = ScreenMode.STATIC;
			break;
		case MotionEvent.ACTION_MOVE:
			switch (screenMode) {
			case SCROLLING:
				if (screenMode == ScreenMode.SCROLLING) {
					offsetX = offsetX + event.getX() - startX;
					offsetY = offsetY + event.getY() - startY;
					startX = event.getX();
					startY = event.getY();
				}
				break;
			case ZOOMING:
				newDistance = distance(event);
				zoom = newDistance / previousDistance;
				break;
			default:
				break;
			}

		}

		Log.i(TAG, "Event coordinates: " + event.getX() + ", " + event.getY());
		Log.i(TAG, "Map offset: " + offsetX + ", " + offsetY);
		Log.i(TAG, "Current field coordinates: " + (int) ((event.getX() - offsetX) / tileSize) + ", "
				+ (int) ((event.getY() - offsetY) / tileSize));
		return true;
	}

	private PointF midpoint(MotionEvent event) {
		return new PointF((event.getX(0) + event.getX(1)) / 2, (event.getY(0) + event.getY(1)) / 2);
	}

	private float distance(MotionEvent event) {
		float x = event.getX(0) - event.getX(1);
		float y = event.getY(0) - event.getY(1);
		return (float) Math.sqrt(x * x + y * y);
	}

	private Queue<Point> findPath(Point localStartPoint, Point localDestinationPoint) {
		return landModel.findPath(localStartPoint, localDestinationPoint);
	}

	private String getFieldInfo(int x, int y) {
		DrawableFieldType drawableFieldType = landModel.getField(x, y);
		if (drawableFieldType == null) {
			return "Beyond land borders";
		}
		GenericJson object = landModel.getObject(x, y);
		if (object == null) {
			FieldType fieldType = drawableFieldType.getFieldType();
			return fieldType.getName() + " (" + x + ", " + y + ")\n"
					+ (fieldType.getPassable().booleanValue() ? "passable" : "non-passable");
		}
		if (object instanceof Town) {
			Town town = (Town) object;
			return "Town " + town.getName() + " (" + x + ", " + y + ")\nPopulated by "
					+ factions.get(town.getFactionId().intValue()).getName() + "\n"
					+ (townVisit != null && townVisit.getTownId().longValue() == town.getId().longValue()
							? "Already visited today" : "Not visited today yet");
		}
		if (object instanceof Passage) {
			Passage passage = (Passage) object;
			return (passage.getNextLandId() == null ? "Closed " : "Open ")
					+ (passage.getPortal().booleanValue() ? "portal" : "passage") + " (" + x + ", " + y + ")\n"
					+ (passage.getNextLandSuggestedLevel() == null ? ""
							: "Suggested level: " + passage.getNextLandSuggestedLevel());
		}
		if (object instanceof Dungeon) {
			Dungeon dungeon = (Dungeon) object;
			return "Dungeon (" + x + ", " + y + ")\nGuarded by "
					+ factions.get(dungeon.getFactionId().intValue()).getName() + "\n"
					+ (dungeonVisitHistory.containsKey(dungeon.getId()) ? "Already visited today"
							: "Not visited today yet");
		}
		return "Unknown object";
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
		for (Faction faction : factions) {
			map.put(faction.getId().intValue(), faction);
		}
		return map;
	}

	private Map<Integer, DrawableFieldType> createFieldtypes(List<FieldType> fieldTypes)
			throws IllegalAccessException, IllegalArgumentException, NoSuchFieldException {
		Map<Integer, DrawableFieldType> fieldTypeMap = new HashMap<>(fieldTypes.size());
		for (FieldType fieldType : fieldTypes) {
			fieldTypeMap.put(fieldType.getId().intValue(), new DrawableFieldType(fieldType, createBitmap(
					R.drawable.class.getField(fieldType.getTexture()).getInt(null), fieldType.getScale())));
		}
		return fieldTypeMap;
	}

	private Map<Long, DungeonVisit> createDungeonVisitHistory(List<DungeonVisit> dungeonVisits) {
		if (dungeonVisits == null) {
			return new HashMap<>();
		}
		Map<Long, DungeonVisit> dungeonVisitHistory = new HashMap<>(dungeonVisits.size());
		for (DungeonVisit dungeonVisit : dungeonVisits) {
			dungeonVisitHistory.put(dungeonVisit.getDungeonId(), dungeonVisit);
		}
		return dungeonVisitHistory;
	}

	private Map<Integer, ItemType> createHeroInventory(List<ItemType> heroItemList) {
		Map<Integer, ItemType> inventory = new HashMap<>(heroItemList.size());
		for (ItemType item : heroItemList) {
			inventory.put(item.getId().intValue(), item);
		}
		return inventory;
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

	public Long getLandId() {
		return land == null ? null : land.getId();
	}

	public Long getTownId() {
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

	public TownVisit getTownVisit() {
		return townVisit;
	}

	public void setTownVisit(TownVisit townVisit) {
		this.townVisit = townVisit;
	}

	public Integer getXpForNextLevel(int level) {
		if (level <= 0 && level > xpsForNextLevel.length) {
			return null;
		}
		return xpsForNextLevel[level - 1];
	}

	public Integer getHeroLevelForXp(int currentLevel, int xp) {
		for (int i = currentLevel - 1; i < xpsForNextLevel.length; ++i) {
			if (xpsForNextLevel[i] > xp) {
				return i;
			}
		}
		return SharedConstants.MAX_HERO_LEVEL;
	}

	public DungeonVisit getDungeonVisit(Long dungeonId) {
		return dungeonVisitHistory.get(dungeonId);
	}

	public void addDungeonVisit(DungeonVisit dungeonVisit) {
		dungeonVisitHistory.put(dungeonVisit.getDungeonId(), dungeonVisit);
	}

	public void setRenderMode(RenderMode renderMode) {
		this.renderMode = renderMode;
	}

	public void setLastDialogOnHeroLocation() {
		lastDialogX = heroModel.getLocalX();
		lastDialogY = heroModel.getLocalY();
	}

	public ItemType getRandomItem(String itemClass) {
		List<ItemType> itemsOfType = itemCache.get(itemClass);
		if (itemsOfType.isEmpty()) {
			return null;
		}
		ItemType item = itemsOfType.get(random.nextInt(itemsOfType.size()));
		itemsOfType.remove(item);
		return item;
	}

	public List<ItemType> getHeroInventory() {
		return heroInventory;
	}

	public void setHeroInventory(List<ItemType> heroInventory) {
		this.heroInventory = heroInventory;
		List<Integer> items = new ArrayList<>(heroInventory.size());
		for (ItemType item : heroInventory) {
			items.add(item.getId().intValue());
		}
		this.hero.setItems(items);
	}

	public void removeItem(ItemType toRemove) {
		for (ItemType item : heroInventory) {
			if (item.getId().longValue() == toRemove.getId().longValue()) {
				heroInventory.remove(item);
				hero.getItems().remove(hero.getItems().indexOf(item.getId().intValue()));
				break;
			}
		}
	}

	public void updateOtherHero(Intent intent) {
		int action = getInt(intent, SharedConstants.ACTION, -1);
		Long heroId = getLong(intent, SharedConstants.HERO_ID, -1L);
		Long currentLandId = getLong(intent, SharedConstants.CURRENT_LAND_ID, -1L);
		Integer newX = getInt(intent, SharedConstants.NEW_X, Integer.MAX_VALUE);
		Integer newY = getInt(intent, SharedConstants.NEW_Y, Integer.MAX_VALUE);
		updateOtherHero(action, heroId, currentLandId, newX, newY);
	}

	public void updateOtherHero(int action, Long heroId, Long currentLandId, Integer newX, Integer newY) {
		Log.i("GCM", action + " " + heroId + " " + currentLandId + " " + newX + " " + newY);

		Hero hero;
		switch (action) {
		case SharedConstants.ARRIVE:
			hero = new Hero().setId(heroId).setX(newX).setY(newY).setCurrentLandId(currentLandId);
			otherHeroModels.put(heroId, new HeroModel(this, heroBitmaps, hero));
			break;
		case SharedConstants.MOVE:
			HeroModel otherHeroModel = otherHeroModels.get(heroId);
			if (heroModel != null) {
				int startX = otherHeroModel.getHero().getX();
				int startY = otherHeroModel.getHero().getY();
				otherHeroModel.setHeroCoordinates(newX - landModel.getCornerX(), newY - landModel.getCornerY());
				otherHeroModel
						.setPath(findPath(new Point(startX - landModel.getCornerX(), startY - landModel.getCornerY()),
								new Point(newX - landModel.getCornerX(), newY - landModel.getCornerY())));
			}
			break;
		case SharedConstants.DEPART:
			otherHeroModels.remove(heroId);
			break;
		default:
			break;
		}

	}

	private long getLong(Intent intent, String key, long def) {
		String s = intent.getStringExtra(key);
		if (StringUtils.isEmpty(s)) {
			return def;
		}
		try {
			return Long.parseLong(s);
		} catch (NumberFormatException e) {
			return def;
		}
	}

	private int getInt(Intent intent, String key, int def) {
		String s = intent.getStringExtra(key);
		if (StringUtils.isEmpty(s)) {
			return def;
		}
		try {
			return Integer.parseInt(s);
		} catch (NumberFormatException e) {
			return def;
		}
	}

	public float getZoom() {
		return zoom;
	}

	public void setZoom(float zoom) {
		this.zoom = zoom;
	}

	public float getTileSize() {
		return tileSize;
	}

	public void setTileSize(float tileSize) {
		this.tileSize = tileSize;
	}

	public PointF getMidpoint() {
		return midPoint;
	}

}
