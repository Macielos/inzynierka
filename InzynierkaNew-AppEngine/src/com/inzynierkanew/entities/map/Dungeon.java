package com.inzynierkanew.entities.map;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Dungeon extends BaseField {

	private static final long serialVersionUID = -4364131150495077922L;
	
//	@Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
		
	private int[] army;
	private int[] maxArmy;
	
	public Dungeon(int x, int y, long type, int... army) {
		super(x, y, type);
		this.army = army;
		this.maxArmy = army;
	}
	
//	public Long getId() {
//		return id;
//	}

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
