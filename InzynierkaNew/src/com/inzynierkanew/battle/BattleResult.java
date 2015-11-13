package com.inzynierkanew.battle;

import java.util.List;

import com.inzynierkanew.town.Unit;
import com.inzynierkanew.utils.GameUtils;

public class BattleResult {

	private boolean victory;
	private int experienceGained;
	
	private List<Integer> heroArmy;
	private List<Integer> enemyArmy;

	public BattleResult(boolean victory, int heroExperience, List<Unit> heroArmy, List<Unit> enemyArmy) {
		this.victory = victory;
		this.experienceGained = heroExperience;
		this.heroArmy = GameUtils.unitListToArmy(heroArmy);
		this.enemyArmy = GameUtils.unitListToArmy(enemyArmy);
	}
	
	public boolean isVictory() {
		return victory;
	}

	public int getExperienceGained() {
		return experienceGained;
	} 
	
	public List<Integer> getHeroArmy() {
		return heroArmy;
	}

	public List<Integer> getEnemyArmy() {
		return enemyArmy;
	}

	@Override
	public String toString() {
		return "BattleResult [victory=" + victory + ", experienceGained=" + experienceGained + ", heroArmy=" + heroArmy + ", enemyArmy="
				+ enemyArmy + "]";
	}

}
