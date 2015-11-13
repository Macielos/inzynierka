package com.inzynierkanew.entities.map;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.google.appengine.api.datastore.Key;

@Entity
public class Town {

	private static final long serialVersionUID = -4364131150495077922L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// @Id
	// @GeneratedValue(strategy = GenerationType.IDENTITY)
	// private Key key;

	private int x;
	private int y;
	private long type;

	private String name;
	private Long factionId;
	private int[] army;
	private int[] maxArmy;

	public Town(int x, int y, long type, String name, Long factionId, int... army) {
		this.x = x;
		this.y = y;
		this.type = type;
		this.name = name;
		this.factionId = factionId;
		this.army = army;
		this.maxArmy = army;
	}

	public Long getId() {
		return id;
	}

	// public Key getKey() {
	// return key;
	// }

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
