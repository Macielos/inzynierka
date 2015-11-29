package com.inzynierkanew.model;

import com.inzynierkanew.entities.players.unittypeendpoint.model.UnitType;

public class Unit {
	
	private final UnitType unitType;
	private int count;
	
	public Unit(UnitType unitType, int count) {
		this.unitType = unitType;
		this.count = count;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public UnitType getUnitType() {
		return unitType;
	}

	@Override
	public String toString() {
		return "AvailableUnit [unitType=" + unitType + ", count=" + count + "]";
	}
	
}
