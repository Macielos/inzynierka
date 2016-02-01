package com.explorersguild.entities.players;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Hero {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String deviceRegistrationID;

	private int x;
	private int y;

	private int level;
	private int experience;
	private int freeSkillPoints;

	private Long currentLandId;
	
	private long gold;
	
	private int strength;
	private int agility;
	private int intelligence;

	private int[] army;
	private int[] items;
	
	private boolean active;
	private Date lastLogin;

	public Hero(String deviceRegistrationId, int x, int y, Long currentLandId, long initialGold, int strength, int agility, int intelligence, int freeSkillPoints){
		this(deviceRegistrationId, x, y, currentLandId, initialGold, strength, agility, intelligence, freeSkillPoints, null, null);
	}
	
	public Hero(String deviceRegistrationId, int x, int y, Long currentLandId, long initialGold, int strength, int agility, int intelligence, int freeSkillPoints, int[] army, int[] items) {
		this.deviceRegistrationID = deviceRegistrationId;
		this.x = x;
		this.y = y;
		this.level = 1;
		this.experience = 0;
		this.freeSkillPoints = freeSkillPoints;
		this.currentLandId = currentLandId;
		this.gold = initialGold;
		this.strength = strength;
		this.agility = agility;
		this.intelligence = intelligence;
		this.army = army;
		this.items = items;
		this.active = false;
		this.lastLogin = null;
	}

	public Long getId() {
		return id;
	}
	
	public String getDeviceRegistrationID() {
		return deviceRegistrationID;
	}

	public void setDeviceRegistrationID(String deviceRegistrationID) {
		this.deviceRegistrationID = deviceRegistrationID;
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

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getExperience() {
		return experience;
	}

	public void setExperience(int experience) {
		this.experience = experience;
	}

	public int getFreeSkillPoints() {
		return freeSkillPoints;
	}

	public void setFreeSkillPoints(int freeSkillPoints) {
		this.freeSkillPoints = freeSkillPoints;
	}

	public Long getCurrentLandId() {
		return currentLandId;
	}

	public void setCurrentLandId(Long currentLandId) {
		this.currentLandId = currentLandId;
	}

	public long getGold() {
		return gold;
	}

	public void setGold(long gold) {
		this.gold = gold;
	}
		
	public int getStrength() {
		return strength;
	}

	public void setStrength(int strength) {
		this.strength = strength;
	}

	public int getAgility() {
		return agility;
	}

	public void setAgility(int agility) {
		this.agility = agility;
	}

	public int getIntelligence() {
		return intelligence;
	}

	public void setIntelligence(int intelligence) {
		this.intelligence = intelligence;
	}

	public int[] getArmy() {
		return army;
	}

	public void setArmy(int[] army) {
		this.army = army;
	}
	
	public int[] getItems() {
		return items;
	}

	public void setItems(int[] items) {
		this.items = items;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

}
