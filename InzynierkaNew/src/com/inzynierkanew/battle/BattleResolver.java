package com.inzynierkanew.battle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.inzynierkanew.game.GameView;
import com.inzynierkanew.model.Unit;
import com.inzynierkanew.utils.GameUtils;

public class BattleResolver {

	private List<Unit> playerArmy;
	private List<Unit> enemyArmy;
	
	private List<Unit> playerLosses;
	private List<Unit> enemyLosses;
	
	private int experienceGained = 0;
	private Boolean victory;

	private final GameView gameView;

	public BattleResolver(List<Integer> heroArmy, List<Integer> enemyArmy, GameView gameView) {
		super();
		this.playerArmy = GameUtils.armyToUnitList(heroArmy, gameView.getUnitTypes());
		this.enemyArmy = GameUtils.armyToUnitList(enemyArmy, gameView.getUnitTypes());
		this.gameView = gameView;
	} 
	
	public BattleResolver runAutoResolve(){
		while(true){
			if(enemyArmy.isEmpty()){
				victory = true;
				break;
			}
			if(playerArmy.isEmpty()){
				victory = false;
				break;
			}
			playTurn();
		}
		return this;
	}
	
	private void playTurn() {
		Random random = new Random();
		enemyLosses = new ArrayList<>(enemyArmy);
		playerLosses = new ArrayList<>(playerArmy.size());
		int loss, lossCount;
		for(Unit unit: playerArmy){
			loss = random.nextInt(40);
			lossCount = unit.getCount() * loss / 100;
			if(lossCount > 0){
				playerLosses.add(new Unit(unit.getUnitType(), lossCount));
				unit.setCount(unit.getCount() - lossCount);
			}
		}
		for(Unit enemyUnit: enemyArmy){
			experienceGained += enemyUnit.getCount()*enemyUnit.getUnitType().getHitpoints()/10;
		}
		enemyArmy.clear();
	}

	public BattleResult getBattleResult(){
		return new BattleResult(victory, experienceGained, playerArmy, enemyArmy, playerLosses, enemyLosses);
	}
	
	
	
}
