package com.inzynierkanew.battle;

import java.util.List;

import com.inzynierkanew.game.GameView;
import com.inzynierkanew.town.Unit;
import com.inzynierkanew.utils.GameUtils;

public class BattleResolver {

	private List<Unit> heroArmy;
	private List<Unit> enemyArmy;
	
	private int experienceGained = 0;
	private Boolean victory;

	private final GameView gameView;

	public BattleResolver(List<Integer> heroArmy, List<Integer> enemyArmy, GameView gameView) {
		super();
		this.heroArmy = GameUtils.armyToUnitList(heroArmy, gameView.getUnitTypes());
		this.enemyArmy = GameUtils.armyToUnitList(enemyArmy, gameView.getUnitTypes());
		this.gameView = gameView;
	} 
	
	public BattleResolver runAutoResolve(){
		while(true){
			if(enemyArmy.isEmpty()){
				victory = true;
				break;
			}
			if(heroArmy.isEmpty()){
				victory = false;
				break;
			}
			playTurn();
		}
		return this;
	}
	
	private void playTurn() {
		for(Unit enemyUnit: enemyArmy){
			experienceGained += enemyUnit.getCount()*enemyUnit.getUnitType().getHitpoints()/10;
		}
		enemyArmy.clear();
	}

	public BattleResult getBattleResult(){
		return new BattleResult(victory, experienceGained, heroArmy, enemyArmy);
	}
	
	
	
}
