package com.inzynierkanew.entities.map;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Dungeon {

	private static final long serialVersionUID = -4364131150495077922L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private int x;
	private int y;
	private long type;
	
	private Long landId;
	private Long factionId;
	
	private int[] army;
	
	public Dungeon(int x, int y, long type, Long landId, Long factionId, int... army) {
		this.x = x;
		this.y = y;
		this.type = type;
		this.landId = landId;
		this.factionId = factionId;
		this.army = army;
	}
	
	public Long getId() {
		return id;
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public long getType() {
		return type;
	}

	public void setType(long type) {
		this.type = type;
	}

	public Long getLandId() {
		return landId;
	}

	public void setLandId(Long landId) {
		this.landId = landId;
	}

	public Long getFactionId() {
		return factionId;
	}

	public void setFactionId(Long factionId) {
		this.factionId = factionId;
	}

	public int[] getArmy() {
		return army;
	}
	
	public void setArmy(int[] army) {
		this.army = army;
	}

}
