package com.inzynierkanew.entities.map;

import javax.persistence.Entity;

@Entity
public class Dungeon extends BaseField {

	private static final long serialVersionUID = -4364131150495077922L;
	
	public Dungeon(int x, int y, long type) {
		super(x, y, type);
	}

}
