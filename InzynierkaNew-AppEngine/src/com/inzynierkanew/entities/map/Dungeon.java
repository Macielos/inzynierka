package com.inzynierkanew.entities.map;

import javax.persistence.Entity;

@Entity
public class Dungeon extends BaseField {

	private static final long serialVersionUID = -4364131150495077922L;
	
//	@Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;

	private Long factionId;
	private int[] army;
	private int[] maxArmy;
	
	public Dungeon(int x, int y, long type, Long factionId, int... army) {
		super(x, y, type);
		this.factionId = factionId;
		this.army = army;
		this.maxArmy = army;
	}
	
//	public Long getId() {
//		return id;
//	}
	
	public int[] getArmy() {
		return army;
	}

	public Long getFactionId() {
		return factionId;
	}

	public void setFactionId(Long factionId) {
		this.factionId = factionId;
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
