package com.inzynierkanew.game;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.inzynierkanew.R;
import com.inzynierkanew.activities.BaseActivity;
import com.inzynierkanew.entities.map.landendpoint.Landendpoint;
import com.inzynierkanew.entities.map.landendpoint.model.Town;
import com.inzynierkanew.entities.map.townendpoint.Townendpoint;
import com.inzynierkanew.entities.players.factionendpoint.Factionendpoint;
import com.inzynierkanew.entities.players.factionendpoint.model.Faction;
import com.inzynierkanew.entities.players.heroendpoint.Heroendpoint;
import com.inzynierkanew.entities.players.playerendpoint.Playerendpoint;
import com.inzynierkanew.entities.players.playerendpoint.model.Hero;
import com.inzynierkanew.entities.players.playerendpoint.model.Player;
import com.inzynierkanew.entities.players.unittypeendpoint.model.UnitType;
import com.inzynierkanew.town.AvailableUnit;
import com.inzynierkanew.utils.CloudEndpointUtils;
import com.inzynierkanew.utils.Constants;

public class GameActivity extends BaseActivity {

	private static final String TAG = GameActivity.class.getSimpleName();

	//TODO updatePlayer wstawia nowego hero (sic!)
	
	// private RelativeLayout viewWrapper;
	private GameView gameView;

	private Map<Integer, UnitType> unitTypes;
	private Town town;
	private Faction faction;
	private Player player;
	private Hero hero;
	private List<AvailableUnit> availableUnits;

	private boolean recruitmentTookPlace = false;

	private final Factionendpoint factionEndpoint = CloudEndpointUtils.newFactionEndpoint();
	private final Playerendpoint playerEndpoint = CloudEndpointUtils.newPlayerEndpoint();
	private final Heroendpoint heroEndpoint = CloudEndpointUtils.newHeroEndpoint();
	private final Townendpoint townEndpoint = CloudEndpointUtils.newTownEndpoint();
	private final Landendpoint landEndpoint = CloudEndpointUtils.newLandEndpoint();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		gameView = new GameView(this, getIntent().getStringExtra(Constants.SESSION_ID));
		setContentView(gameView);
		//enterTown();
	}
	
	public void enterTown(){
		initTown();
		showTownMain();
	}

	private void initTown() {
		unitTypes = gameView.getUnitTypes();
		player = gameView.getPlayer();
		try {
			town = new AsyncTask<Void, Void, Town>() {

				@Override
				protected Town doInBackground(Void... params) {
					try {
						return landEndpoint.getTown(gameView.getLandId()).execute();
						//return townEndpoint.getTown(gameView.getTownKey().getId()).execute();
					} catch (IOException e) {
						Log.e(TAG, "failed to download town data", e);
						return null;
					}
				}

			}.execute().get();
		} catch (InterruptedException | ExecutionException e) {
			Log.e(TAG, "failed to download town data in an async task", e);
		}

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

		try {
			faction = new AsyncTask<Long, Void, Faction>() {

				@Override
				protected Faction doInBackground(Long... params) {
					try {
						return factionEndpoint.getFaction(params[0]).execute();
					} catch (IOException e) {
						Log.e(TAG, "failed to download faction data", e);
						return null;
					}
				}

			}.execute(town.getFactionId()).get();
		} catch (InterruptedException | ExecutionException e) {
			Log.e(TAG, "failed to download faction data in an async task", e);
		}
		availableUnits = armyToAvailableUnitList(town.getArmy());
	}

	private void showTownMain() {
		setContentView(R.layout.activity_town);

		TextView townNameTextView = (TextView) findViewById(R.id.townName);
		townNameTextView.setText(town.getName());

		TextView factionTextView = (TextView) findViewById(R.id.townFaction);
		factionTextView.setText("Town owned by " + faction.getName());

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
				leaveTown();
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

				final AvailableUnit availableUnit = availableUnits.get(position);

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

				// Handle buttons and add onClickListeners
				final Button recruitBtn = (Button) view.findViewById(R.id.recruitButton);
				recruitBtn.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						int recruitedUnits = unitsToRecruitSeekBar.getProgress();
						int recruitmentCost = recruitedUnits * availableUnit.getUnitType().getCost();
						if (player.getGold() >= recruitmentCost) {
							Log.i(TAG, "recruiting " + recruitedUnits + "x " + availableUnit.getUnitType().getName());
							player.setGold(player.getGold() - recruitmentCost);
							boolean recruitAll = unitsToRecruitSeekBar.getMax()==recruitedUnits;
							recruit(town, player.getHero(), availableUnit.getUnitType().getId().intValue(), recruitedUnits);
							unitsToRecruitSeekBar.setProgress(0);
							if(recruitAll){
								unitsToRecruitSeekBar.setMax(1);
								unitsToRecruitSeekBar.setEnabled(false);
								unitCountText.setText("0 / 0");
								recruitBtn.setEnabled(false);
							} else {
								unitsToRecruitSeekBar.setMax(unitsToRecruitSeekBar.getMax() - recruitedUnits);
								//unitsToRecruitSeekBar.forceLayout();
								unitCountText.setText(unitsToRecruitSeekBar.getProgress() + " / " + unitsToRecruitSeekBar.getMax());
							}
							displayPlayerArmy();
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

		displayPlayerArmy();

		Button backButton = (Button) findViewById(R.id.barracksBackButton);
		backButton.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				Log.i(TAG, "return to land");
				if (recruitmentTookPlace) {
					new AsyncTask<Void, Void, Void>() {

						@Override
						protected Void doInBackground(Void... params) {
							try {
								playerEndpoint.updatePlayer(player).execute();
								landEndpoint.updateTown(town).execute();
							} catch (IOException e) {
								Log.e(TAG, "failed to update entities after recruitment", e);
							}
							return null;
						}

					}.execute();
				}
				showTownMain();
				return true;
			}
		});
	}

	private void displayPlayerArmy() {
		List<Integer> army = player.getHero().getArmy();
		int armySize = army == null ? 0 : army.size();
		try {
			ImageView unitImage;
			TextView unitCount;
			// List<ImageView> unitImages = new
			// ArrayList<>(Constants.PLAYER_ARMY_MAX_SIZE);
			// List<TextView> unitCounts = new
			// ArrayList<>(Constants.PLAYER_ARMY_MAX_SIZE);
			for (int i = 0; i < Constants.PLAYER_ARMY_MAX_SIZE; ++i) {
				unitImage = (ImageView) findViewById(R.id.class.getField("unit" + i).getInt(null));
				unitCount = (TextView) findViewById(R.id.class.getField("unitCount" + i).getInt(null));
				if (2 * i < armySize) {
					unitImage.setImageBitmap(BitmapFactory.decodeResource(getResources(),
							R.drawable.class.getField(unitTypes.get(army.get(2 * i)).getTexture()).getInt(null)));
					unitCount.setText(""+army.get(2 * i + 1));
				} else {
					unitImage.setImageBitmap(null);
					unitCount.setText("");
				}
			}
		} catch (NoSuchFieldException | IllegalAccessException | IllegalArgumentException e) {
			Log.e(TAG, "Error while displaying player army", e);
		}
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

	public void leaveTown() {
		setContentView(gameView);
		gameView.setRenderMode(GameView.DIALOG_CHOSEN);
	}

	private List<AvailableUnit> armyToAvailableUnitList(List<Integer> army) {
		List<AvailableUnit> availableUnits = new ArrayList<>(army.size() / 2);
		for (int i = 0; i < army.size(); i += 2) {
			availableUnits.add(new AvailableUnit(unitTypes.get(army.get(i)), army.get(i + 1)));
		}
		return availableUnits;
	}

	private void recruit(Town town, Hero hero, int unitType, int recruitedCount) {
		recruitmentTookPlace = true;
		List<Integer> townArmy = (List<Integer>) (town.getArmy() == null ? new ArrayList<>() : town.getArmy());
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
		town.setArmy(townArmy);
		hero.setArmy(heroArmy);
		availableUnits = armyToAvailableUnitList(townArmy);
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
