package com.inzynierkanew.entities.map;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class TownVisit {

	private static final long serialVersionUID = -4364131150495077922L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Long heroId;
	private Long townId;
	private Long landId;
	
	private int[] army;

	public TownVisit(Long heroId, Long townId, Long landId, int... army) {
		super();
		this.heroId = heroId;
		this.townId = townId;
		this.landId = landId;
		this.army = army;
	}

	public int[] getArmy() {
		return army;
	}

	public void setArmy(int[] army) {
		this.army = army;
	}

	public Long getId() {
		return id;
	}

	public Long getHeroId() {
		return heroId;
	}

	public Long getTownId() {
		return townId;
	}

	public Long getLandId() {
		return landId;
	}
	
}
