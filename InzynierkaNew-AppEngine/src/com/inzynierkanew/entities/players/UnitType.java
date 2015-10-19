package com.inzynierkanew.entities.players;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class UnitType {

	@Id
	private Long id;
	
	private String name;
	private int cost;
	private String texture;
	private Long factionId;
	
	private int minDamage;
	private int maxDamage;
	private int hitpoints;
	private int speed;
	private boolean ranged;
	private int missiles;
	
	public UnitType(Long id, String name, int cost, String texture, Long factionId, int minDamage, int maxDamage, int hitpoints, int speed, boolean ranged, int missiles) {
		this.id = id;
		this.name = name;
		this.cost = cost;
		this.texture = texture;
		this.factionId = factionId;
		this.minDamage = minDamage;
		this.maxDamage = maxDamage;
		this.hitpoints = hitpoints;
		this.speed = speed;
		this.ranged = ranged;
		this.missiles = missiles;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public String getTexture() {
		return texture;
	}

	public void setTexture(String texture) {
		this.texture = texture;
	}

	public Long getFactionId() {
		return factionId;
	}

	public void setFactionId(Long factionId) {
		this.factionId = factionId;
	}

	public int getMinDamage() {
		return minDamage;
	}

	public void setMinDamage(int minDamage) {
		this.minDamage = minDamage;
	}

	public int getMaxDamage() {
		return maxDamage;
	}

	public void setMaxDamage(int maxDamage) {
		this.maxDamage = maxDamage;
	}

	public int getHitpoints() {
		return hitpoints;
	}

	public void setHitpoints(int hitpoints) {
		this.hitpoints = hitpoints;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public boolean isRanged() {
		return ranged;
	}

	public void setRanged(boolean ranged) {
		this.ranged = ranged;
	}

	public int getMissiles() {
		return missiles;
	}

	public void setMissiles(int missiles) {
		this.missiles = missiles;
	}

	public Long getId() {
		return id;
	}

	public int calcUnitStrength(){
		int cost = ((minDamage+maxDamage)/2+hitpoints/4+speed*2);
		if(ranged){
			cost = cost * (20+missiles)/20;
		}
		return cost;
	}

}
