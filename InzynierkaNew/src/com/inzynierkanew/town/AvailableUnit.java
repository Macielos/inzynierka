package com.inzynierkanew.town;

import com.inzynierkanew.entities.players.unittypeendpoint.model.UnitType;

public class AvailableUnit {
	
	private final UnitType unitType;
	private int count;
	
	public AvailableUnit(UnitType unitType, int count) {
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
