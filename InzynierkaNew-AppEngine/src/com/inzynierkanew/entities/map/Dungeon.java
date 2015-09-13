package com.inzynierkanew.entities.map;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

//@Entity
public class Dungeon extends Field {

	private static final long serialVersionUID = -4364131150495077922L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	public Dungeon(int x, int y, long type) {
		super(x, y, type);
	}

	public long getId() {
		return id;
	}
	
}
