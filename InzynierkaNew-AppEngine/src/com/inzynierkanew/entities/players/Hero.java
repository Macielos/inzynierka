package com.inzynierkanew.entities.players;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Hero {

	/**
	 * TODO: player.hero -> nie referencja tylko id szukaj hero, a nie
	 * player.getHero rozkmiñ czemu po kliku pasek rekrut. jednostek siê nie
	 * zmienia
	 */

	// @Id
	// @GeneratedValue(strategy = GenerationType.IDENTITY)
	// private Key key;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private int x;

	private int y;

	private int level;

	private int experience;

	private Long currentLandId;

	private int[] army;

	private int[] items;

	// public Key getKey() {
	// return key;
	// }

	public Hero(int x, int y, Long currentLandId){
		this(x, y, currentLandId, null, null);
	}
	
	public Hero(int x, int y, Long currentLandId, int[] army, int[] items) {
		this.x = x;
		this.y = y;
		this.level = 1;
		this.experience = 0;
		this.currentLandId = currentLandId;
		this.army = army;
		this.items = items;
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

	public Long getCurrentLandId() {
		return currentLandId;
	}

	public void setCurrentLandId(Long currentLandId) {
		this.currentLandId = currentLandId;
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

}
