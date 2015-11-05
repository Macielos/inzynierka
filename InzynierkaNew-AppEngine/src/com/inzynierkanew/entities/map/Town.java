package com.inzynierkanew.entities.map;

import javax.persistence.Entity;

@Entity
public class Town extends BaseField {

	private static final long serialVersionUID = -4364131150495077922L;
	
	private String name;
	private Long factionId;
	private int[] army;
	private int[] maxArmy;
	
	public Town(int x, int y, long type, String name, Long factionId, int... army) {
		super(x, y, type);
		this.name = name;
		this.factionId = factionId;
		this.army = army;
		this.maxArmy = army;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public int[] getMaxArmy() {
		return maxArmy;
	}

	public void setMaxArmy(int[] maxArmy) {
		this.maxArmy = maxArmy;
	}
	
	
	
}
