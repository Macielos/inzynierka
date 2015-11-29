package com.inzynierkanew.entities.map;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class DungeonVisit {

	private static final long serialVersionUID = -4364131150495077922L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Long heroId;
	private Long dungeonId;
	private Long landId;
	
	private int[] army;

	public DungeonVisit(Long heroId, Long dungeonId, Long landId, int... army) {
		super();
		this.heroId = heroId;
		this.dungeonId = dungeonId;
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

	public Long getDungeonId() {
		return dungeonId;
	}

	public Long getLandId() {
		return landId;
	}
	
}
