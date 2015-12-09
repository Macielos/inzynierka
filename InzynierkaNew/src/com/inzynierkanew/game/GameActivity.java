package com.inzynierkanew.game;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import com.inzynierkanew.R;
import com.inzynierkanew.activities.BaseActivity;
import com.inzynierkanew.battle.BattleResolver;
import com.inzynierkanew.battle.BattleResult;
import com.inzynierkanew.entities.map.dungeonendpoint.Dungeonendpoint;
import com.inzynierkanew.entities.map.dungeonendpoint.model.Dungeon;
import com.inzynierkanew.entities.map.dungeonvisitendpoint.Dungeonvisitendpoint;
import com.inzynierkanew.entities.map.dungeonvisitendpoint.model.DungeonVisit;
import com.inzynierkanew.entities.map.townendpoint.Townendpoint;
import com.inzynierkanew.entities.map.townendpoint.model.Town;
import com.inzynierkanew.entities.map.townvisitendpoint.Townvisitendpoint;
import com.inzynierkanew.entities.map.townvisitendpoint.model.TownVisit;
import com.inzynierkanew.entities.players.heroendpoint.Heroendpoint;
import com.inzynierkanew.entities.players.heroendpoint.model.Hero;
import com.inzynierkanew.entities.players.itemendpoint.model.Item;
import com.inzynierkanew.entities.players.unittypeendpoint.model.UnitType;
import com.inzynierkanew.model.Loot;
import com.inzynierkanew.model.Unit;
import com.inzynierkanew.utils.AndroidUtils;
import com.inzynierkanew.utils.CloudEndpointUtils;
import com.inzynierkanew.utils.Constants;
import com.inzynierkanew.utils.GameUtils;
import com.inzynierkanew.utils.SharedConstants;

import android.app.Dialog;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class GameActivity extends BaseActivity {

	private static final String TAG = GameActivity.class.getSimpleName();

	private GameView gameView;

	private Map<Integer, UnitType> unitTypes;
	private Town town;
	private TownVisit townVisit;
	private List<Integer> townArmy;
	private Hero hero;
	private List<Unit> availableUnits;
	
	private boolean recruitmentTookPlace = false;

	private final Heroendpoint heroEndpoint = CloudEndpointUtils.newHeroEndpoint();
	private final Townendpoint townEndpoint = CloudEndpointUtils.newTownEndpoint();
	private final Townvisitendpoint townVisitEndpoint = CloudEndpointUtils.newTownVisitEndpoint();
	private final Dungeonendpoint dungeonEndpoint = CloudEndpointUtils.newDungeonEndpoint();
	private final Dungeonvisitendpoint dungeonVisitEndpoint = CloudEndpointUtils.newDungeonVisitEndpoint();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		gameView = new GameView(this, getIntent().getStringExtra(Constants.SESSION_ID));
		initWorldView();
	}
	
	public void initWorldView() {
		setContentView(R.layout.activity_main);
		
		unitTypes = gameView.getUnitTypes();
		hero = gameView.getHero();
		
		LinearLayout surface = (LinearLayout) findViewById(R.id.main_gameSurface);
		surface.addView(gameView);
		
		displayPlayerGold();
		
		displayHeroExperience();
		displayPlayerArmyMain();
		
		gameView.setRenderMode(GameView.DIALOG_CHOSEN);
		gameView.setLastDialogOnHeroLocation();
	}

	private void displayPlayerGold() {
		TextView playerGold = (TextView) findViewById(R.id.main_goldCount);
		playerGold.setText(""+hero.getGold());
	}
	
	public void initTownView(){
		LinearLayout surface = (LinearLayout) findViewById(R.id.main_gameSurface);
		surface.removeView(gameView);
		loadTownData();
		showTownMain();
	}

	private void loadTownData() {
		try {
			town = new AsyncTask<Void, Void, Town>() {

				@Override
				protected Town doInBackground(Void... params) {
					try {
						return townEndpoint.getTown(gameView.getTownId()).execute();
					} catch (IOException e) {
						Log.e(TAG, "failed to download town data", e);
						return null;
					}
				}
			}.execute().get();
		} catch (InterruptedException | ExecutionException e) {
			Log.e(TAG, "failed to download town data in an async task", e);
		}
		townVisit = gameView.getTownVisit();
		townArmy = townVisit==null ? town.getArmy() : townVisit.getArmy();

//		try {
//			hero = new AsyncTask<Long, Void, Hero>() {
//
//				@Override
//				protected Hero doInBackground(Long... params) {
//					try {
//						return heroEndpoint.getHero(params[0]).execute();
//					} catch (IOException e) {
//						Log.e(TAG, "failed to download hero data", e);
//						return null;
//					}
//				}
//
//			}.execute(player.getHero().getKey().getId()).get();
//		} catch (InterruptedException | ExecutionException e) {
//			Log.e(TAG, "failed to download hero data in an async task", e);
//		}
		
		availableUnits = GameUtils.armyToUnitList(townArmy, unitTypes);
	}

	private void showTownMain() {
		setContentView(R.layout.activity_town);

		TextView townNameTextView = (TextView) findViewById(R.id.townName);
		townNameTextView.setText(town.getName());

		TextView factionTextView = (TextView) findViewById(R.id.townFaction);
		factionTextView.setText("Town owned by " + gameView.getFactions().get(town.getFactionId().intValue()).getName());

		Button barracksButton = (Button) findViewById(R.id.townBarracksButton);
		barracksButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showTownBarracks();
			}
		});

		Button marketplaceButton = (Button) findViewById(R.id.townMarketplaceButton);
		marketplaceButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showTownMarketplace();
			}
		});
		
		Button quitButton = (Button) findViewById(R.id.townQuitButton);
		quitButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				initWorldView();
			}
		});
	}

	private void showTownBarracks() {
		recruitmentTookPlace = false;
		// townView = new TownView(this, gameView.getTown(),
		// gameView.getUnitTypes());
		// setContentView(townView);
		// getLayoutInflater().inflate(R.id.townView, (ViewGroup) townView);

		setContentView(R.layout.activity_town_barracks);

		// new TownView(this, gameView.getTown(), gameView.getUnitTypes()));

		// viewWrapper = (RelativeLayout) findViewById(R.id.townView);
		// viewWrapper.removeAllViews();
		// LayoutInflater layoutInflater =
		// (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		// viewWrapper.addView(layoutInflater.inflate(R.layout.activity_town,
		// null));

		ListView listUnitsView = (ListView) findViewById(R.id.listUnitsView);
		listUnitsView.setAdapter(new BaseAdapter() {

			@Override
			public int getCount() {
				return availableUnits.size();
			}

			@Override
			public Object getItem(int index) {
				return availableUnits.get(index);
			}

			@Override
			public long getItemId(int index) {
				return availableUnits.get(index).getUnitType().getId();
			}

			@Override
			public View getView(final int position, View convertView, ViewGroup parent) {
				View view = convertView;
				if (view == null) {
					LayoutInflater inflater = (LayoutInflater) GameActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
					view = inflater.inflate(R.layout.element_unit, null);
				}

				final Unit availableUnit = availableUnits.get(position);

				// Handle TextView and display string from your list
				TextView listItemText = (TextView) view.findViewById(R.id.unitName);
				listItemText.setText(availableUnits.get(position).getUnitType().getName());

				final TextView unitCountText = (TextView) view.findViewById(R.id.unitCount);
				unitCountText.setText("0 / " + availableUnit.getCount());

				final SeekBar unitsToRecruitSeekBar = (SeekBar) view.findViewById(R.id.unitsToRecruit);
				unitsToRecruitSeekBar.setEnabled(availableUnit.getCount() > 0);
				unitsToRecruitSeekBar.setMax(availableUnit.getCount());
				unitsToRecruitSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

					@Override
					public void onStopTrackingTouch(SeekBar seekBar) {

					}

					@Override
					public void onStartTrackingTouch(SeekBar seekBar) {

					}

					@Override
					public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
						unitCountText.setText(progress + " / " + seekBar.getMax());
					}
				});

				final ImageView unitImageView = (ImageView) view.findViewById(R.id.unitImage);
				try {
					unitImageView.setImageBitmap(BitmapFactory.decodeResource(getResources(),
							R.drawable.class.getField(availableUnit.getUnitType().getTexture()).getInt(null)));
				} catch (IllegalAccessException | IllegalArgumentException | NoSuchFieldException e) {
					Log.e(TAG, "Failed to display texture for unit " + availableUnit, e);
				}

				//TODO nie dziala rekrutacja max liczby dostêpnych jednostek - jednostki nie znikaj¹ z miasta, pasek i podpis siê nie updatuj¹
				// Handle buttons and add onClickListeners
				final Button recruitBtn = (Button) view.findViewById(R.id.recruitButton);
				recruitBtn.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						int recruitedUnits = unitsToRecruitSeekBar.getProgress();
						int recruitmentCost = recruitedUnits * availableUnit.getUnitType().getCost();
						if (hero.getGold() >= recruitmentCost) {
							Log.i(TAG, "recruiting " + recruitedUnits + "x " + availableUnit.getUnitType().getName());
							hero.setGold(hero.getGold() - recruitmentCost);
							boolean recruitAll = unitsToRecruitSeekBar.getMax()==recruitedUnits;
							recruit(availableUnit.getUnitType().getId().intValue(), recruitedUnits);
							unitsToRecruitSeekBar.setProgress(0);
							if(recruitAll){
								//TODO pasek sie nie odswieza choc przycisk jest disabled jak powinien
								unitsToRecruitSeekBar.setMax(1);
								unitsToRecruitSeekBar.setEnabled(false);
								unitCountText.setText("0 / 0");
								recruitBtn.setEnabled(false);
							} else {
								unitsToRecruitSeekBar.setMax(unitsToRecruitSeekBar.getMax() - recruitedUnits);
								unitCountText.setText(unitsToRecruitSeekBar.getProgress() + " / " + unitsToRecruitSeekBar.getMax());
							}
							displayPlayerArmyTown();
						} else {
							String text = unitsToRecruitSeekBar.getProgress() + " / " + unitsToRecruitSeekBar.getMax();
							unitCountText.setText(text);
							showDialog("Not enough gold!");
						}

						notifyDataSetChanged();
					}
				});

				return view;
			}
		});

		displayPlayerArmyTown();

		Button backButton = (Button) findViewById(R.id.barracksBackButton);
		backButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.i(TAG, "return to land");
				if (recruitmentTookPlace) {
					try {
						new AsyncTask<Void, Void, Void>() {

							@Override
							protected Void doInBackground(Void... params) {
								try {
									//playerEndpoint.updatePlayer(player).execute();
									gameView.setTownVisit(townVisit);
									townVisitEndpoint.updateTownVisit(townVisit).execute();
									heroEndpoint.updateHero(hero).execute();
								} catch (IOException e) {
									Log.e(TAG, "failed to update entities after recruitment", e);
								}
								return null;
							}
						}.execute().get();
					} catch (InterruptedException | ExecutionException e) {
						Log.e(TAG, "failed to update entities after recruitment in an async task", e);
					}
				}
				showTownMain();
			}
		});
	}

	private void displayPlayerArmyAftermath(List<Unit> losses, Dialog dialog){
		displayArmy("dungeon_dialog_result_playerLosses_", losses, dialog);
	}
	
	private void displayDungeonArmyAftermath(List<Unit> losses, Dialog dialog){
		displayArmy("dungeon_dialog_result_enemyLosses_", losses, dialog);
	}
		
	private void displayDungeonArmy(List<Integer> dungeonArmy, Dialog dialog){
		displayArmy("dungeon_dialog_", GameUtils.armyToUnitList(dungeonArmy, unitTypes), dialog);
	}
	
	private void displayPlayerArmyMain() {
		displayArmy("main_", GameUtils.armyToUnitList(hero.getArmy(), unitTypes), null);
	}
	
	private void displayPlayerArmyTown() {
		displayArmy("", GameUtils.armyToUnitList(hero.getArmy(), unitTypes), null);
	}
	
	private void displayArmy(String fieldPrefix, List<Unit> army, Dialog dialog) {
		int armySize = army == null ? 0 : army.size();
		try {
			ImageView unitImage;
			TextView unitCount;
			// List<ImageView> unitImages = new
			// ArrayList<>(Constants.PLAYER_ARMY_MAX_SIZE);
			// List<TextView> unitCounts = new
			// ArrayList<>(Constants.PLAYER_ARMY_MAX_SIZE);
			for (int i = 0; i < Constants.PLAYER_ARMY_MAX_SIZE; ++i) {
				unitImage = (ImageView) findByField(fieldPrefix+"unit" + i, dialog);
				unitCount = (TextView) findByField(fieldPrefix+"unitCount" + i, dialog);
				if (i < armySize) {
					unitImage.setImageBitmap(BitmapFactory.decodeResource(getResources(),
							R.drawable.class.getField(""+army.get(i).getUnitType().getTexture()).getInt(null)));
					unitCount.setText(""+army.get(i).getCount());
				} else {
					unitImage.setImageBitmap(null);
					unitCount.setText("");
				}
			}
		} catch (NoSuchFieldException | IllegalAccessException | IllegalArgumentException e) {
			Log.e(TAG, "Error while displaying player army", e);
		}
	}
	
	private void displayItemsAftermath(final Dialog dialog, Loot loot) {
		displayItems("dungeon_dialog_result_", loot.getItems(), dialog);
	}
	
	private void displayItems(String fieldPrefix, List<Item> items, Dialog dialog) {
		try {
			ImageView itemImage;
			for (int i = 0; i < Constants.LOOT_MAX_SIZE; ++i) {
				itemImage = (ImageView) findByField(fieldPrefix+"item" + i, dialog);
				if (i < items.size()) {
					itemImage.setImageBitmap(BitmapFactory.decodeResource(getResources(),
							R.drawable.class.getField(""+items.get(i).getIcon()).getInt(null)));
				} else {
					itemImage.setImageBitmap(null);
				}
			}
		} catch (NoSuchFieldException | IllegalAccessException | IllegalArgumentException e) {
			Log.e(TAG, "Error while displaying player army", e);
		}
	}
	
	private View findByField(String field, Dialog dialog) throws IllegalAccessException, IllegalArgumentException, NoSuchFieldException{
		int id = R.id.class.getField(field).getInt(null);
		return dialog == null ? findViewById(id) : dialog.findViewById(id);
	}

	private void showTownMarketplace() {
		setContentView(R.layout.activity_town_marketplace);
		Button backButton = (Button) findViewById(R.id.marketPlaceBackButton);
		backButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showTownMain();
			}
		});
	}

	private void recruit(int unitType, int recruitedCount) {
		recruitmentTookPlace = true;
		List<Integer> heroArmy = (List<Integer>) (hero.getArmy() == null ? new ArrayList<>() : hero.getArmy());
		for (int i = 0; i < townArmy.size(); i += 2) {
			if (townArmy.get(i).equals(unitType) && townArmy.get(i + 1) >= recruitedCount) {
				if (townArmy.get(i + 1) > recruitedCount) {
					townArmy.set(i + 1, townArmy.get(i + 1) - recruitedCount);
				} 
				break;
			}
		}
		boolean found = false;
		for (int i = 0; i < heroArmy.size(); i += 2) {
			if (heroArmy.get(i).equals(unitType)) {
				heroArmy.set(i + 1, heroArmy.get(i + 1) + recruitedCount);
				found = true;
				break;
			}
		}
		if(!found){
			heroArmy.add(unitType);
			heroArmy.add(recruitedCount);
		}
		if(townVisit == null){
			townVisit = new TownVisit().setHeroId(hero.getId()).setTownId(town.getId()).setArmy(townArmy);
		} else {
			townVisit.setArmy(townArmy);
		}

		hero.setArmy(heroArmy);
		availableUnits = GameUtils.armyToUnitList(townArmy, unitTypes);
	}

	public void showDungeonDialog(final Long dungeonId) {
		Dungeon dungeonInit = null;
		try {
			dungeonInit = new AsyncTask<Void, Void, Dungeon>(){
				protected Dungeon doInBackground(Void[] params) {
					try {
						return dungeonEndpoint.getDungeon(dungeonId).execute();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						Log.e(TAG, "Failed to get dungeon in async task", e);
						return null;
					}
				};
			}.execute().get();
		} catch (InterruptedException | ExecutionException e1) {
			// TODO Auto-generated catch block
			Log.e(TAG, "Failed to get dungeon", e1);
		}
		
		final Dungeon dungeon = dungeonInit;
		
		final Dialog dialog = new Dialog(this);
		dialog.setContentView(R.layout.dialog_dungeon);
		
		DungeonVisit dungeonVisit = gameView.getDungeonVisit(dungeon.getId());
		List<Integer> army = dungeonVisit == null ? dungeon.getArmy() : dungeonVisit.getArmy();
		
		displayDungeonArmy(army, dialog);
		
		boolean guardiansPresent = !GameUtils.isArmyEmpty(army);
		
		TextView factionTextView = (TextView) dialog.findViewById(R.id.dungeon_dialog_faction);
		factionTextView.setText(guardiansPresent 
					? "Guarded by "+gameView.getFactions().get(dungeon.getFactionId().intValue()).getName()
					: "Deserted");
				
		Button combatButton = (Button) dialog.findViewById(R.id.dungeon_dialog_combatButton);
		AndroidUtils.setVisible(combatButton, guardiansPresent);
		combatButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.i(TAG, "battle!");
				final BattleResult battleResult = new BattleResolver(hero.getArmy(), dungeon.getArmy(), gameView).runAutoResolve().getBattleResult();
				Loot loot = getLoot(battleResult);
				hero.setArmy(GameUtils.unitListToArmy(battleResult.getHeroArmy()));
				updateHeroExperience(battleResult.getExperienceGained());
				hero.setGold(hero.getGold()+loot.getGold());
				displayPlayerGold();
				List<Integer> heroItems = hero.getItems();
				if(heroItems == null){
					heroItems = new ArrayList<>();
				}
				for(Item item: loot.getItems()){
					gameView.getHeroInventory().put(item.getId().intValue(), item);
					heroItems.add(item.getId().intValue());
				}
				hero.setItems(heroItems);
				
				displayPlayerArmyMain();
				displayHeroExperience();
				dungeon.setArmy(GameUtils.unitListToArmy(battleResult.getEnemyArmy()));
				try {
					new AsyncTask<Void, Void, Void>(){

						@Override
						protected Void doInBackground(Void... arg0) {
							try {
								heroEndpoint.updateHero(hero).execute();
							} catch (IOException e) {
								Log.e(TAG, "failed to update hero", e);
							}
							return null;
						}
						
					}.execute().get();
				} catch (InterruptedException | ExecutionException e) {
					Log.e(TAG, "failed to update hero in an async task", e);
				}
				try {
					new AsyncTask<Void, Void, Void>(){

						@Override
						protected Void doInBackground(Void... arg0) {
							try {
								DungeonVisit dungeonVisit = new DungeonVisit()
										.setHeroId(hero.getId())
										.setDungeonId(dungeon.getId())
										.setLandId(gameView.getLandId())
										.setArmy(GameUtils.unitListToArmy(battleResult.getEnemyArmy()));
								gameView.addDungeonVisit(dungeonVisit);
								dungeonVisitEndpoint.insertDungeonVisit(dungeonVisit).execute();
							} catch (IOException e) {
								Log.e(TAG, "failed to insert dungeon visit", e);
							}
							return null;
						}
					}.execute().get();
				} catch (InterruptedException | ExecutionException e) {
					Log.e(TAG, "failed to update dungeon in an async task", e);
				}
				dialog.setContentView(R.layout.dialog_dungeon_result);
				
				TextView goldFoundView = (TextView) dialog.findViewById(R.id.dungeon_dialog_result_goldFoundValue);
				goldFoundView.setText(""+loot.getGold());
				
				TextView experienceGainedView = (TextView) dialog.findViewById(R.id.dungeon_dialog_result_experienceGainedValue);
				experienceGainedView.setText(""+battleResult.getExperienceGained());

				displayItemsAftermath(dialog, loot);
				
				displayPlayerArmyAftermath(battleResult.getPlayerLosses(), dialog);
				displayDungeonArmyAftermath(battleResult.getEnemyLosses(), dialog);
				
				Button quitButton = (Button) dialog.findViewById(R.id.dungeon_dialog_result_quitButton);
				quitButton.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						dialog.hide();
						gameView.setRenderMode(GameView.DIALOG_CHOSEN);
						gameView.setLastDialogOnHeroLocation();                                                     
					}
				});

			}

		});
		
		Button withdrawButton = (Button) dialog.findViewById(R.id.dungeon_dialog_withdrawButton);
		withdrawButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.i(TAG, "withdraw!");
				dialog.hide();
				gameView.setRenderMode(GameView.DIALOG_CHOSEN);
				gameView.setLastDialogOnHeroLocation();    
			}
		});
		dialog.show();
	}
	
	protected void updateHeroExperience(int experienceGained) {
		hero.setExperience(hero.getExperience()+experienceGained);
		hero.setLevel(gameView.getHeroLevelForXp(hero.getLevel(), hero.getExperience()));
	}

	private void displayHeroExperience() {

		TextView playerLevel = (TextView) findViewById(R.id.main_level);
		playerLevel.setText(""+hero.getLevel());
		
		int xpForThisLevel = gameView.getXpForNextLevel(hero.getLevel());
		int xpForNextLevel = gameView.getXpForNextLevel(hero.getLevel()+1);
		
		TextView playerExperience = (TextView) findViewById(R.id.main_experienceText);
		playerExperience.setText(""+hero.getExperience()+"/"+xpForNextLevel);
		
		ProgressBar playerExperienceBar = (ProgressBar) findViewById(R.id.main_experienceBar);
		playerExperienceBar.setMax(xpForNextLevel - xpForThisLevel);
		playerExperienceBar.setProgress(hero.getExperience() - xpForThisLevel);
	}
	
	private Loot getLoot(BattleResult battleResult){
		double xpd = (double) battleResult.getExperienceGained();
		int goldGained = (int)(((double)SharedConstants.BASE_GOLD_PER_VICTORY+xpd*SharedConstants.GOLD_XP_MODIFIER)*SharedConstants.GOLD_RANDOM_FACTOR);
		Random random = new Random();
		double itemDropped = random.nextDouble();
		List<Item> items;
		if(itemDropped < SharedConstants.CHANCE_FOR_ITEM){
			Item item = gameView.getRandomItem(SharedConstants.STANDARD);
			if(item!=null){
				items = Arrays.asList(item);
			} else {
				items = new ArrayList<>(0);
			}
		} else {
			items = new ArrayList<>(0);
		}
		return new Loot(goldGained, items);
	}

	// @Override
	// protected void onNewIntent(Intent intent) {
	// super.onNewIntent(intent);
	//
	// /*
	// * If we are dealing with an intent generated by the GCMIntentService
	// * class, then display the provided message.
	// */
	// if (intent.getBooleanExtra("gcmIntentServiceMessage", false)) {
	//
	// showDialog(intent.getStringExtra("message"));
	//
	// if (!intent.getBooleanExtra("registrationMessage", false)) {
	// /*
	// * if we didn't get a registration/unregistration message then
	// * go get the last 5 messages from app-engine
	// */
	// new QueryMessagesTask(this, messageEndpoint).execute();
	// }
	// }
	// }
	//
	// /*
	// * Need to run this in background so we don't hold up the UI thread, this
	// * task will ask the App Engine backend for the last 5 messages sent to it
	// */
	// private class QueryMessagesTask extends AsyncTask<Void, Void,
	// CollectionResponseMessageData> {
	// Exception exceptionThrown = null;
	// MessageEndpoint messageEndpoint;
	//
	// public QueryMessagesTask(Activity activity, MessageEndpoint
	// messageEndpoint) {
	// this.messageEndpoint = messageEndpoint;
	// }
	//
	// @Override
	// protected CollectionResponseMessageData doInBackground(Void... params) {
	// try {
	// CollectionResponseMessageData messages =
	// messageEndpoint.listMessages().setLimit(5).execute();
	// return messages;
	// } catch (IOException e) {
	// exceptionThrown = e;
	// return null;
	// // Handle exception in PostExecute
	// }
	// }
	//
	// protected void onPostExecute(CollectionResponseMessageData messages) {
	// // Check if exception was thrown
	// if (exceptionThrown != null) {
	// Log.e(RegisterActivity.class.getName(),
	// "Exception when listing Messages", exceptionThrown);
	// showDialog("Failed to retrieve the last 5 messages from " +
	// "the endpoint at " + messageEndpoint.getBaseUrl()
	// + ", check log for details");
	// } else {
	// /* TextView messageView = (TextView) findViewById(R.id.message);
	// messageView.setText("Last 5 Messages read from " +
	// messageEndpoint.getBaseUrl() + ":\n");
	// for (MessageData message : messages.getItems()) {
	// messageView.append(message.getMessage() + "\n");
	// }*/
	// }
	// }
	// }*/
}
