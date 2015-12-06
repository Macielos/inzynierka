package com.inzynierkanew.entities.map;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class TownVisit {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Long townId;
	private Long heroId;
	
	private int[] army;

	public TownVisit(Long townId, Long heroId, int... army) {
		super();
		this.townId = townId;
		this.heroId = heroId;
		this.army = army;
	}

	public int[] getArmy() {
		return army;
	}

	public void setArmy(int[] army) {
		this.army = army;
	}

	public Long getHeroId() {
		return heroId;
	}

	public Long getTownId() {
		return townId;
	}

}
