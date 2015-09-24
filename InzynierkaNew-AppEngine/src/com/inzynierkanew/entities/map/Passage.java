package com.inzynierkanew.entities.map;

import javax.persistence.Entity;

@Entity
public class Passage extends BaseField {

	private static final long serialVersionUID = -4364131150495077922L;
	
	private Long nextLandId;

	public Passage(int x, int y, long type, Long nextLandId) {
		super(x, y, type);
		this.nextLandId = nextLandId;
	}

	public Long getNextLandId() {
		return nextLandId;
	}

	public void setNextLandId(Long nextLandId) {
		this.nextLandId = nextLandId;
	}
	
}
