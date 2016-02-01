package com.explorersguild.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import com.explorersguild.battle.BattleResolver;
import com.explorersguild.battle.BattleResult;
import com.explorersguild.entities.players.unittypeendpoint.model.UnitType;
import com.explorersguild.game.GameView;

import android.test.AndroidTestCase;

public class BattleResolverTest extends AndroidTestCase {

	private static final Map<Integer, UnitType> unitTypes = new HashMap<>();

	@BeforeClass
	public void beforeClass() {
		putUnitType(1L, "Goblin", 100, "goblin", 2L, 3, 6, 50, 6, true, 6);
		putUnitType(2L, "Orc", 400, "orc", 2L, 10, 15, 200, 5, false, 0);
		putUnitType(3L, "Troll", 2000, "troll", 2L, 30, 100, 1000, 3, false, 0);
		putUnitType(101L, "Swordsman", 300, "swordsman", 1L, 8, 12, 150, 5, false, 0);
		putUnitType(102L, "Archer", 450, "archer", 1L, 6, 10, 100, 5, true, 8);
		putUnitType(103L, "Knight", 1500, "knight", 1L, 18, 36, 500, 7, false, 0);
	}

	private static void putUnitType(Long id, String name, int cost, String texture, long factionId, int minDmg,
			int maxDmg, int hp, int speed, boolean ranged, int missiles) {
		unitTypes.put(id.intValue(),
				new UnitType().setId(id).setName(name).setCost(cost).setTexture(texture).setFactionId(factionId)
						.setMinDamage(minDmg).setMaxDamage(maxDmg).setHitpoints(hp).setSpeed(speed).setRanged(ranged)
						.setMissiles(missiles));
	}

	@Test
	public void test() {
		GameView gameView = mockGameView();
		
		BattleResult battleResult = new BattleResolver(Arrays.asList(101, 50), new ArrayList<Integer>(), gameView).runAutoResolve().getBattleResult();
		Assert.assertTrue(battleResult.isVictory());
		Assert.assertEquals(battleResult.getHeroArmy().size(), 1);
		Assert.assertTrue(battleResult.getPlayerLosses().isEmpty());
		Assert.assertTrue(battleResult.getEnemyArmy().isEmpty());
		Assert.assertTrue(battleResult.getEnemyLosses().isEmpty());
		
		battleResult = new BattleResolver(new ArrayList<Integer>(), Arrays.asList(2, 10), gameView).runAutoResolve().getBattleResult();
		Assert.assertFalse(battleResult.isVictory());
		Assert.assertTrue(battleResult.getHeroArmy().isEmpty());
		Assert.assertTrue(battleResult.getPlayerLosses().isEmpty());
		Assert.assertEquals(battleResult.getEnemyArmy().size(), 1);
		Assert.assertTrue(battleResult.getEnemyLosses().isEmpty());
	
		battleResult = new BattleResolver(Arrays.asList(101, 50), Arrays.asList(2, 10), mockGameView()).runAutoResolve().getBattleResult();
		Assert.assertTrue(battleResult.isVictory());
		Assert.assertFalse(battleResult.getHeroArmy().isEmpty());
		Assert.assertTrue(battleResult.getEnemyArmy().isEmpty());
		Assert.assertEquals(battleResult.getEnemyLosses().size(), 1);
	}

	private GameView mockGameView(){
		GameView gameView = Mockito.mock(GameView.class);
		Mockito.when(gameView.getUnitTypes()).thenReturn(unitTypes);
		return gameView;
	}

}
