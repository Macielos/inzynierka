package test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import com.inzynierkanew.battle.BattleResolver;
import com.inzynierkanew.battle.BattleResult;
import com.inzynierkanew.entities.players.unittypeendpoint.model.UnitType;
import com.inzynierkanew.game.GameView;

public class BattleResolverTest {

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

	//@Test
	public void test() {
		BattleResult battleResult = new BattleResolver(Arrays.asList(101, 50), Arrays.asList(2, 20), mockGameView()).runAutoResolve().getBattleResult();
		System.out.println(battleResult);
	}

	private GameView mockGameView(){
		GameView gameView = Mockito.mock(GameView.class);
		Mockito.when(gameView.getUnitTypes()).thenReturn(unitTypes);
		return gameView;
	}

}
