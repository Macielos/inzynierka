package com.inzynierkanew.battle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.inzynierkanew.entities.players.itemendpoint.model.Item;
import com.inzynierkanew.entities.players.unittypeendpoint.model.UnitType;
import com.inzynierkanew.game.GameView;
import com.inzynierkanew.model.Unit;
import com.inzynierkanew.shared.SharedConstants;
import com.inzynierkanew.utils.GameUtils;

import android.util.Log;

public class BattleResolver {

	private static final int MAP_SIZE = 20;
	
	private static final String TURN_TAG = "Battle Turn";
	private static final String DAMAGE_TAG = "Battle Damage";
	
	private final Unit heroUnit;
	private final UnitType heroStats;

	private final boolean turnLogEnabled = true;
	private final boolean damageLogEnabled = true;
	
	private int strength;
	private int agility;
	private int intelligence;
	
	private int meleeBonus;
	private int rangedBonus;
	
	private List<Unit> playerArmy;
	private List<Unit> enemyArmy;

	private List<Unit> playerInitialArmy;
	private List<Unit> enemyInitialArmy;

	private int turn = 0;
	private Boolean victory;

	private List<Integer> playerPositions;
	private List<Integer> enemyPositions;

	private List<Integer> playerHitpoints;
	private List<Integer> enemyHitpoints;
	
	private List<Integer> playerMissiles;
	private List<Integer> enemyMissiles;

	private final Random random = new Random();

	public BattleResolver(List<Integer> playerArmy, List<Integer> enemyArmy, GameView gameView) {
		this.playerArmy = GameUtils.armyToUnitList(playerArmy, gameView.getUnitTypes());
		this.enemyArmy = GameUtils.armyToUnitList(enemyArmy, gameView.getUnitTypes());
		
		playerInitialArmy = GameUtils.copyUnits(this.playerArmy);
		enemyInitialArmy = GameUtils.copyUnits(this.enemyArmy);

		playerPositions = new ArrayList<>(this.playerArmy.size());
		enemyPositions = new ArrayList<>(this.enemyArmy.size());

		playerHitpoints = new ArrayList<>(this.playerArmy.size());
		enemyHitpoints = new ArrayList<>(this.enemyArmy.size());

		playerMissiles = new ArrayList<>(this.playerArmy.size());
		enemyMissiles = new ArrayList<>(this.enemyArmy.size());
		
		for (int i = 0; i < this.playerArmy.size(); ++i) {
			playerPositions.add(0);
			playerHitpoints.add(this.playerArmy.get(i).getUnitType().getHitpoints());
			playerMissiles.add(this.playerArmy.get(i).getUnitType().getMissiles());
		}
		for (int i = 0; i < this.enemyArmy.size(); ++i) {
			enemyPositions.add(MAP_SIZE - 1);
			enemyHitpoints.add(this.enemyArmy.get(i).getUnitType().getHitpoints());
			enemyMissiles.add(this.enemyArmy.get(i).getUnitType().getMissiles());
		}
		
		strength = gameView.getHero().getStrength();
		agility = gameView.getHero().getAgility();
		intelligence = gameView.getHero().getIntelligence();
		
		for(Item item: gameView.getHeroInventory()){
			strength+=item.getStrengthBonus();
			agility+=item.getAgilityBonus();
			intelligence+=item.getIntelligenceBonus();
		}
		
		meleeBonus = (100+SharedConstants.HERO_STRENGTH_DAMAGE_BONUS*strength)/100;
		rangedBonus = (100+SharedConstants.HERO_STRENGTH_DAMAGE_BONUS*agility)/100;
		heroStats = new UnitType().setName("Hero").setRanged(true).setMissiles(Integer.MAX_VALUE)
				.setMinDamage(intelligence*SharedConstants.HERO_INTELLIGENCE_DAMAGE_BONUS)
				.setMaxDamage(intelligence*SharedConstants.HERO_INTELLIGENCE_DAMAGE_BONUS);
		heroUnit = new Unit(heroStats, 1);
	}

	public BattleResolver runAutoResolve() {
		while (true) {
			++turn;
			if (playerArmy.isEmpty()) {
				victory = false;
				break;
			}
			playHeroTurn();
			playTurnForArmy(playerArmy, enemyArmy, playerPositions, enemyPositions, enemyHitpoints, playerMissiles, 1);
			if (enemyArmy.isEmpty()) {
				victory = true;
				break;
			}
			playTurnForArmy(enemyArmy, playerArmy, enemyPositions, playerPositions, playerHitpoints, enemyMissiles, -1);
		}
		return this;
	}

	private void playHeroTurn() {
		int targetIndex = chooseTarget(heroUnit, -1, enemyPositions, null, -1);
		if(targetIndex!=-1){
			attack(heroUnit, targetIndex, enemyArmy, enemyPositions, enemyHitpoints);
		}
	}

	private void playTurnForArmy(List<Unit> thisArmy, List<Unit> otherArmy, List<Integer> thisArmyPositions,
			List<Integer> otherArmyPositions, List<Integer> otherArmyHitpoints, List<Integer> thisArmyMissiles, int delta) {
		int otherUnitToAttack;
		Unit currentUnit;
		for (int i = 0; i < thisArmy.size(); ++i) {
			currentUnit = thisArmy.get(i);
			// ranged units stand at start point and shoot, melee go forward to
			// meet the enemy
			if (!currentUnit.getUnitType().getRanged().booleanValue()) {
				thisArmyPositions.set(i, move(currentUnit, thisArmyPositions.get(i), otherArmyPositions, delta));
			}
			otherUnitToAttack = chooseTarget(currentUnit, thisArmyPositions.get(i), otherArmyPositions, thisArmyMissiles, i);
			if (otherUnitToAttack != -1) {
				attack(currentUnit, otherUnitToAttack, otherArmy, otherArmyPositions, otherArmyHitpoints);
			}
		}
		if (turnLogEnabled) {
			Log.i(TURN_TAG, toString());
		}
	}

	private int move(Unit movingUnit, int position, List<Integer> otherArmyPositions, int delta) {
		int movementRange = position + movingUnit.getUnitType().getSpeed() * delta;
		int closestTargetPosition = getClosestTargetPosition(otherArmyPositions, delta);
		if (delta > 0) {
			return Math.min(MAP_SIZE - 1, Math.min(movementRange, closestTargetPosition));
		} else {
			return Math.max(0, Math.max(movementRange, closestTargetPosition));
		}
	}

	private int getClosestTargetPosition(List<Integer> otherArmyPositions, int delta) {
		int closestTargetPosition = MAP_SIZE / 2 + delta * MAP_SIZE;
		for (Integer position : otherArmyPositions) {
			if (delta > 0 && position < closestTargetPosition) {
				closestTargetPosition = position;
			}
			if (delta < 0 && position > closestTargetPosition) {
				closestTargetPosition = position;
			}
		}
		return closestTargetPosition;
	}

	private int chooseTarget(Unit currentUnit, int position, List<Integer> otherArmyPositions, List<Integer> thisArmyMissiles, int index) {
		if(otherArmyPositions.isEmpty()){
			return -1;
		}
		if(currentUnit.getUnitType().getRanged().booleanValue() && currentUnit != heroUnit && thisArmyMissiles.get(index) == 0){
			return -1;
		}

		// if ranged, can attack any target in enemy army
		if (currentUnit.getUnitType().getRanged().booleanValue()) {
			return random.nextInt(otherArmyPositions.size());
		}
		// if not, can attack enemy on same position
		List<Integer> validTargets = new ArrayList<>(otherArmyPositions.size());
		for (int i = 0; i < otherArmyPositions.size(); ++i) {
			if (otherArmyPositions.get(i).intValue() == position) {
				validTargets.add(i);
			}
		}
		if (validTargets.isEmpty()) {
			return -1;
		}
		
		if(currentUnit.getUnitType().getRanged().booleanValue() && currentUnit != heroUnit){
			thisArmyMissiles.set(index, thisArmyMissiles.get(index)-1);
		}
		return validTargets.get(random.nextInt(validTargets.size()));
	}

	private void attack(Unit attacker, int targetPosition, List<Unit> otherArmy, List<Integer> otherArmyPositions,
			List<Integer> otherArmyHitpoints) {
		Unit target = otherArmy.get(targetPosition);
		int minDamage = attacker.getUnitType().getMinDamage();
		int maxDamage = attacker.getUnitType().getMaxDamage();
		int totalDamage = attacker.getCount() * minDamage;
		if(minDamage != maxDamage){
			totalDamage += random.nextInt(attacker.getCount() * (maxDamage - minDamage));
		}
		if(attacker!=heroUnit){
			totalDamage = totalDamage * (100 + (attacker.getUnitType().getRanged().booleanValue() ? rangedBonus : meleeBonus))/100;
		}
		int targetTotalHp = (target.getCount() - 1) * target.getUnitType().getHitpoints()
				+ otherArmyHitpoints.get(targetPosition) - totalDamage;

		if (targetTotalHp <= 0) {
			if(damageLogEnabled){
				Log.i(DAMAGE_TAG, attacker.getUnitType().getName()+" deals "+totalDamage+" damage to "+target.getUnitType().getName()+" killing all");
			}
			// enemy unit killed - remove from lists
			otherArmy.remove(targetPosition);
			otherArmyPositions.remove(targetPosition);
			otherArmyHitpoints.remove(targetPosition);
		} else {
			// enemy unit survived the attack - calc casualties
			int newCount = targetTotalHp / target.getUnitType().getHitpoints();
			if(targetTotalHp % target.getUnitType().getHitpoints()!=0){
				newCount+=1;
			}
			if(damageLogEnabled){
				Log.i(DAMAGE_TAG, attacker.getUnitType().getName()+" deals "+totalDamage+" damage to "+target.getUnitType().getName()+" killing "+(target.getCount() - newCount));
			}
			target.setCount(newCount);
			otherArmyHitpoints.set(targetPosition, targetTotalHp % target.getUnitType().getHitpoints());
		}
	}

	public BattleResult getBattleResult() {
		List<Unit> playerLosses = getLosses(playerInitialArmy, playerArmy);
		List<Unit> enemyLosses = getLosses(enemyInitialArmy, enemyArmy);
		int experienceGained = countXpGained(enemyLosses);
		return new BattleResult(victory, experienceGained, playerArmy, enemyArmy, playerLosses, enemyLosses);
	}

	private int countXpGained(List<Unit> enemyLosses) {
		int experienceGained = 0;
		for (Unit enemyUnit : enemyLosses) {
			experienceGained += enemyUnit.getCount() * enemyUnit.getUnitType().getHitpoints() / 10;
		}
		return experienceGained;
	}

	private List<Unit> getLosses(List<Unit> initialArmy, List<Unit> outcomeArmy) {
		//no army - no losses
		if (initialArmy.isEmpty()) {
			return new ArrayList<>(0);
		}
		//lost entire army
		if (outcomeArmy.isEmpty()) {
			return GameUtils.copyUnits(initialArmy);
		}
		List<Unit> losses = new ArrayList<>(initialArmy.size());
		Unit initial, outcome;
		for (int initialIndex = 0, outcomeIndex = 0; initialIndex < initialArmy.size()
				&& outcomeIndex < outcomeArmy.size();) {
			initial = initialArmy.get(initialIndex);
			outcome = outcomeArmy.get(outcomeIndex);
			if (initial.getUnitType().getId().equals(outcome.getUnitType().getId())) {
				if(initial.getCount()!=outcome.getCount()){
					//lost part of this unit
					losses.add(new Unit(initial.getUnitType(), initial.getCount()-outcome.getCount()));
				}
				//no losses in this unit
				++outcomeIndex;
			} else {
				//lost all troops from this unit
				losses.add(new Unit(initial.getUnitType(), initial.getCount()));
			}
			++initialIndex;
		}
		return losses;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Turn " + turn + "\n");
		armyToString(sb, playerArmy, playerHitpoints, "Player army");
		armyToString(sb, enemyArmy, enemyHitpoints, "Enemy army");
		armyPositionsToString(sb, playerPositions, "P");
		armyPositionsToString(sb, enemyPositions, "E");
		return sb.toString();
	}

	private void armyToString(StringBuilder sb, List<Unit> army, List<Integer> hitpoints, String title) {
		sb.append(title + ": \n");
		for (int i = 0; i < army.size(); ++i) {
			sb.append(army.get(i).getUnitType().getName() + ", " + army.get(i).getCount() + ", " + hitpoints.get(i)
					+ "\n");
		}
	}

	private void armyPositionsToString(StringBuilder sb, List<Integer> positions, String sign) {
		for (int i = 0; i < positions.size(); ++i) {
			for (int j = 0; j < MAP_SIZE; ++j) {
				if (positions.get(i).intValue() == j) {
					sb.append(sign + i + " ");
				} else {
					sb.append("   ");
				}
			}
			sb.append("\n");
		}
	}

}
