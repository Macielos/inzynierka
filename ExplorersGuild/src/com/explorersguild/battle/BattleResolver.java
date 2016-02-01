package com.explorersguild.battle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.explorersguild.entities.players.itemtypeendpoint.model.ItemType;
import com.explorersguild.entities.players.unittypeendpoint.model.UnitType;
import com.explorersguild.game.GameView;
import com.explorersguild.model.BattleUnit;
import com.explorersguild.model.Unit;
import com.explorersguild.shared.SharedConstants;
import com.explorersguild.utils.GameUtils;

import android.util.Log;

public class BattleResolver {

	private static final int MAP_SIZE = 20;

	private static final String TURN_TAG = "Battle Turn";
	private static final String ACTION_TAG = "Battle Actions";

	private final BattleUnit heroUnit;
	private final UnitType heroStats;

	private final boolean turnLogEnabled = true;
	private final boolean actionLogEnabled = true;

	private int strength;
	private int agility;
	private int intelligence;

	private int meleeBonus;
	private int rangedBonus;

	private List<BattleUnit> playerArmy;
	private List<BattleUnit> enemyArmy;

	private List<BattleUnit> playerInitialArmy;
	private List<BattleUnit> enemyInitialArmy;

	private int turn = 0;
	private Boolean victory;

	private final Random random = new Random();

	public BattleResolver(List<Integer> playerArmy, List<Integer> enemyArmy, GameView gameView) {
		this.playerArmy = GameUtils.armyToBattleUnitList(playerArmy, gameView.getUnitTypes(), 0);
		this.enemyArmy = GameUtils.armyToBattleUnitList(enemyArmy, gameView.getUnitTypes(), MAP_SIZE - 1);

		playerInitialArmy = GameUtils.copyBattleUnits(this.playerArmy);
		enemyInitialArmy = GameUtils.copyBattleUnits(this.enemyArmy);

		strength = gameView.getHero().getStrength();
		agility = gameView.getHero().getAgility();
		intelligence = gameView.getHero().getIntelligence();

		for (ItemType item : gameView.getHeroInventory()) {
			strength += item.getStrengthBonus();
			agility += item.getAgilityBonus();
			intelligence += item.getIntelligenceBonus();
		}

		meleeBonus = (100 + SharedConstants.HERO_STRENGTH_DAMAGE_BONUS * strength) / 100;
		rangedBonus = (100 + SharedConstants.HERO_AGILITY_DAMAGE_BONUS * agility) / 100;
		heroStats = new UnitType().setName("Hero").setRanged(true).setMissiles(Integer.MAX_VALUE)
				.setHitpoints(Integer.MAX_VALUE)
				.setMinDamage(intelligence * SharedConstants.HERO_INTELLIGENCE_DAMAGE_BONUS)
				.setMaxDamage(intelligence * SharedConstants.HERO_INTELLIGENCE_DAMAGE_BONUS);
		heroUnit = new BattleUnit(heroStats, 1, 0);
	}

	public BattleResolver runAutoResolve() {
		while (true) {
			++turn;
			if (playerArmy.isEmpty()) {
				victory = false;
				break;
			}
			playHeroTurn();
			playTurnForArmy(playerArmy, enemyArmy, 1);
			if (enemyArmy.isEmpty()) {
				victory = true;
				break;
			}
			playTurnForArmy(enemyArmy, playerArmy, -1);
		}
		return this;
	}

	private void playHeroTurn() {
		int targetIndex = chooseTarget(heroUnit, enemyArmy);
		if (targetIndex != -1) {
			attack(heroUnit, enemyArmy.get(targetIndex), enemyArmy);
		}
	}

	private void playTurnForArmy(List<BattleUnit> thisArmy, List<BattleUnit> otherArmy, int delta) {
		int otherUnitToAttack;
		BattleUnit currentUnit;
		for (int i = 0; i < thisArmy.size(); ++i) {
			currentUnit = thisArmy.get(i);
			// ranged units stand at start point and shoot, melee go forward to
			// meet the enemy
			if (!currentUnit.getUnitType().getRanged().booleanValue()) {
				thisArmy.get(i).setPosition(move(currentUnit, otherArmy, delta));
				if (actionLogEnabled) {
					Log.i(ACTION_TAG,
							currentUnit.getUnitType().getName() + " moves to position " + currentUnit.getPosition());
				}
			}
			otherUnitToAttack = chooseTarget(currentUnit, otherArmy);
			if (otherUnitToAttack != -1) {
				attack(currentUnit, otherArmy.get(otherUnitToAttack), otherArmy);
			}
		}
		if (turnLogEnabled) {
			Log.i(TURN_TAG, toString());
		}
	}

	private int move(BattleUnit movingUnit, List<BattleUnit> otherArmy, int delta) {
		int movementRange = movingUnit.getPosition() + movingUnit.getUnitType().getSpeed() * delta;
		int closestTargetPosition = getClosestTargetPosition(otherArmy, delta);
		if (delta > 0) {
			return Math.min(MAP_SIZE - 1, Math.min(movementRange, closestTargetPosition));
		} else {
			return Math.max(0, Math.max(movementRange, closestTargetPosition));
		}
	}

	private int getClosestTargetPosition(List<BattleUnit> otherArmy, int delta) {
		int closestTargetPosition = MAP_SIZE / 2 + delta * MAP_SIZE;
		for (BattleUnit unit : otherArmy) {
			if (delta > 0 && unit.getPosition() < closestTargetPosition) {
				closestTargetPosition = unit.getPosition();
			}
			if (delta < 0 && unit.getPosition() > closestTargetPosition) {
				closestTargetPosition = unit.getPosition();
			}
		}
		return closestTargetPosition;
	}

	private int chooseTarget(BattleUnit currentUnit, List<BattleUnit> otherArmy) {
		if (otherArmy.isEmpty()) {
			return -1;
		}
		if (currentUnit.getUnitType().getRanged().booleanValue() && currentUnit != heroUnit
				&& currentUnit.getMissiles() == 0) {
			return -1;
		}

		// if ranged, can attack any target in enemy army
		if (currentUnit.getUnitType().getRanged().booleanValue()) {
			return random.nextInt(otherArmy.size());
		}
		// if not, can attack enemy on same position
		List<Integer> validTargets = new ArrayList<>(otherArmy.size());
		for (int i = 0; i < otherArmy.size(); ++i) {
			if (otherArmy.get(i).getPosition() == currentUnit.getPosition()) {
				validTargets.add(i);
			}
		}
		if (validTargets.isEmpty()) {
			return -1;
		}

		if (currentUnit.getUnitType().getRanged().booleanValue() && currentUnit != heroUnit) {
			currentUnit.setMissiles(currentUnit.getMissiles() - 1);
		}
		return validTargets.get(random.nextInt(validTargets.size()));
	}

	private void attack(BattleUnit attacker, BattleUnit target, List<BattleUnit> targetArmy) {
		int minDamage = attacker.getUnitType().getMinDamage();
		int maxDamage = attacker.getUnitType().getMaxDamage();
		int totalDamage = attacker.getCount() * minDamage;
		if (minDamage != maxDamage) {
			totalDamage += random.nextInt(attacker.getCount() * (maxDamage - minDamage));
		}
		if (attacker != heroUnit) {
			totalDamage = totalDamage
					* (100 + (attacker.getUnitType().getRanged().booleanValue() ? rangedBonus : meleeBonus)) / 100;
		}
		int targetTotalHp = (target.getCount() - 1) * target.getUnitType().getHitpoints()
				+ target.getHitpoints() - totalDamage;

		if (targetTotalHp <= 0) {
			if (actionLogEnabled) {
				Log.i(ACTION_TAG, attacker.getUnitType().getName() + " deals " + totalDamage + " damage to "
						+ target.getUnitType().getName() + " killing all");
			}
			// enemy unit killed - remove from list
			targetArmy.remove(target);
		} else {
			// enemy unit survived the attack - calc casualties
			int newCount = targetTotalHp / target.getUnitType().getHitpoints();
			if (targetTotalHp % target.getUnitType().getHitpoints() != 0) {
				newCount += 1;
			}
			if (actionLogEnabled) {
				Log.i(ACTION_TAG, attacker.getUnitType().getName() + " deals " + totalDamage + " damage to "
						+ target.getUnitType().getName() + " killing " + (target.getCount() - newCount));
			}
			target.setCount(newCount);
			target.setHitpoints(targetTotalHp % target.getUnitType().getHitpoints());
		}
	}

	public BattleResult getBattleResult() {
		List<Unit> playerLosses = getLosses(playerInitialArmy, playerArmy);
		List<Unit> enemyLosses = getLosses(enemyInitialArmy, enemyArmy);
		int experienceGained = countXpGained(enemyLosses);
		return new BattleResult(victory, experienceGained, GameUtils.battleUnitsToUnits(playerArmy), GameUtils.battleUnitsToUnits(enemyArmy), playerLosses, enemyLosses);
	}

	private int countXpGained(List<Unit> enemyLosses) {
		int experienceGained = 0;
		for (Unit enemyUnit : enemyLosses) {
			experienceGained += enemyUnit.getCount() * enemyUnit.getUnitType().getHitpoints() / 3;
		}
		return experienceGained;
	}

	private List<Unit> getLosses(List<BattleUnit> initialArmy, List<BattleUnit> outcomeArmy) {
		// no army - no losses
		if (initialArmy.isEmpty()) {
			return new ArrayList<>(0);
		}
		// lost entire army
		if (outcomeArmy.isEmpty()) {
			return GameUtils.battleUnitsToUnits(initialArmy);
		}
		List<Unit> losses = new ArrayList<>(initialArmy.size());
		Unit initial, outcome;
		for (int initialIndex = 0, outcomeIndex = 0; initialIndex < initialArmy.size()
				&& outcomeIndex < outcomeArmy.size();) {
			initial = initialArmy.get(initialIndex);
			outcome = outcomeArmy.get(outcomeIndex);
			if (initial.getUnitType().getId().equals(outcome.getUnitType().getId())) {
				if (initial.getCount() != outcome.getCount()) {
					// lost part of this unit
					losses.add(new Unit(initial.getUnitType(), initial.getCount() - outcome.getCount()));
				}
				// no losses in this unit
				++outcomeIndex;
			} else {
				// lost all troops from this unit
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
		armyToString(sb, playerArmy, "Player army");
		armyToString(sb, enemyArmy, "Enemy army");
		heroToString(sb);
		armyPositionsToString(sb, playerArmy, "P");
		armyPositionsToString(sb, enemyArmy, "E");
		return sb.toString();
	}

	private void armyToString(StringBuilder sb, List<BattleUnit> army, String title) {
		sb.append(title + ": \n");
		for (int i = 0; i < army.size(); ++i) {
			sb.append(army.get(i).getUnitType().getName() + ", " + army.get(i).getCount() + ", " + army.get(i).getHitpoints()
					+ "\n");
		}
	}

	private void armyPositionsToString(StringBuilder sb, List<BattleUnit> army, String sign) {
		for (int i = 0; i < army.size(); ++i) {
			for (int j = 0; j < MAP_SIZE; ++j) {
				if (army.get(i).getPosition() == j) {
					sb.append(sign + i + " ");
				} else {
					sb.append("   ");
				}
			}
			sb.append("\n");
		}
	}

	private void heroToString(StringBuilder sb) {
		sb.append("H\n");
	}

}
