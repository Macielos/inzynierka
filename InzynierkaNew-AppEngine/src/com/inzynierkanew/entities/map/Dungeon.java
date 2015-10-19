package com.inzynierkanew.entities.map;

import javax.persistence.Entity;

@Entity
public class Dungeon extends BaseField {

	private static final long serialVersionUID = -4364131150495077922L;
	
	int[] army;
	
	public Dungeon(int x, int y, long type, int... army) {
		super(x, y, type);
		this.army = army;
	}

}
