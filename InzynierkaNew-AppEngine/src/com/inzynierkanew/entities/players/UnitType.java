package com.inzynierkanew.entities.players;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class UnitType {
	
	private static final long serialVersionUID = -4005051003659282302L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;
	private int hitpoints;
	private int minDamage;
	private int maxDamage;
	private int speed;
	private boolean ranged;
	private int missiles;
	private int cost;
	
	public UnitType(String name, int hitpoints, int minDamage, int maxDamage, int speed, boolean ranged, int missiles, int cost) {
		super();
		this.name = name;
		this.hitpoints = hitpoints;
		this.minDamage = minDamage;
		this.maxDamage = maxDamage;
		this.speed = speed;
		this.ranged = ranged;
		this.missiles = missiles;
		this.cost = cost;
	}
	
	public long getId() {
		return id;
	}
		
	public String getName() {
		return name;
	}
	public int getHitpoints() {
		return hitpoints;
	}
	public int getMinDamage() {
		return minDamage;
	}
	public int getMaxDamage() {
		return maxDamage;
	}
	public int getSpeed() {
		return speed;
	}
	public boolean isRanged() {
		return ranged;
	}
	public int getMissiles() {
		return missiles;
	}
	public int getCost() {
		return cost;
	}
	
	
}
