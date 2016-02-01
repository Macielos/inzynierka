package com.explorersguild.model;

import com.explorersguild.entities.players.unittypeendpoint.model.UnitType;

public class BattleUnit extends Unit {
	
	private int position;
	private int hitpoints;
	private int missiles;
	
	public BattleUnit(UnitType unitType, int count, int position) {
		super(unitType, count);
		this.position = position;
		this.hitpoints = unitType.getHitpoints();
		this.missiles = unitType.getMissiles();
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public int getHitpoints() {
		return hitpoints;
	}

	public void setHitpoints(int hitpoints) {
		this.hitpoints = hitpoints;
	}

	public int getMissiles() {
		return missiles;
	}

	public void setMissiles(int missiles) {
		this.missiles = missiles;
	}

	@Override
	public String toString() {
		return "BattleUnit [Unit="+ super.toString() +", position=" + position + ", hitpoints=" + hitpoints + ", missiles=" + missiles + "]";
	}

}
