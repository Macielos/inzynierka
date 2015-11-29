package com.inzynierkanew.battle;

import java.util.List;

import com.inzynierkanew.model.Unit;
import com.inzynierkanew.utils.GameUtils;

public class BattleResult {

	private boolean victory;
	private int experienceGained;
	
	private List<Unit> heroArmy;
	private List<Unit> enemyArmy;
	
	private List<Unit> playerLosses;
	private List<Unit> enemyLosses;

	public BattleResult(boolean victory, int heroExperience, List<Unit> heroArmy, List<Unit> enemyArmy, 
			List<Unit> playerLosses, List<Unit> enemyLosses) {
		this.victory = victory;
		this.experienceGained = heroExperience;
		this.heroArmy = heroArmy;
		this.enemyArmy = enemyArmy;
		this.playerLosses = playerLosses;
		this.enemyLosses = enemyLosses;
	}
	
	public boolean isVictory() {
		return victory;
	}

	public int getExperienceGained() {
		return experienceGained;
	} 
	
	public List<Unit> getHeroArmy() {
		return heroArmy;
	}

	public List<Unit> getEnemyArmy() {
		return enemyArmy;
	}
	
	public List<Unit> getPlayerLosses() {
		return playerLosses;
	}

	public List<Unit> getEnemyLosses() {
		return enemyLosses;
	}

	@Override
	public String toString() {
		return "BattleResult [victory=" + victory + ", experienceGained=" + experienceGained + ", heroArmy=" + heroArmy
				+ ", enemyArmy=" + enemyArmy + ", playerLosses=" + playerLosses + ", enemyLosses=" + enemyLosses + "]";
	}
	
}
