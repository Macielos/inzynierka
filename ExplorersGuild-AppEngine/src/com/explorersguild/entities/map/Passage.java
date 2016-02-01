package com.explorersguild.entities.map;

import javax.persistence.Entity;

@Entity
public class Passage extends BaseField {

	private static final long serialVersionUID = -4364131150495077922L;
	
	private int direction;
	
	private Long landId;
	
	private Long nextLandId;
	
	private int nextX;
	
	private int nextY;
	
	private Integer nextLandSuggestedLevel;
		
	private boolean portal;
	
	public Passage(int x, int y, int direction) {
		this(x, y, direction, null, 0, 0, null);
	}

	public Passage(int x, int y, int direction, Long nextLandId, int nextX, int nextY, Integer nextLandSuggestedLevel) {
		this(x, y, direction, nextLandId, nextX, nextY, nextLandSuggestedLevel, false);
	}
	
	public Passage(int x, int y, int direction, Long nextLandId, int nextX, int nextY, Integer nextLandSuggestedLevel, boolean portal) {
		super(x, y);
		this.direction = direction;
		this.nextLandId = nextLandId;
		this.nextX = nextX;
		this.nextY = nextY;
		this.nextLandSuggestedLevel = nextLandSuggestedLevel;
		this.portal = portal;
	}
	
	public Long getLandId() {
		return landId;
	}

	public void setLandId(Long landId) {
		this.landId = landId;
	}

	public Long getNextLandId() {
		return nextLandId;
	}

	public void setNextLandId(Long nextLandId) {
		this.nextLandId = nextLandId;
	}

	public int getNextX() {
		return nextX;
	}

	public void setNextX(int nextX) {
		this.nextX = nextX;
	}

	public int getNextY() {
		return nextY;
	}

	public void setNextY(int nextY) {
		this.nextY = nextY;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}
	
	public boolean isPortal() {
		return portal;
	}

	public void setPortal(boolean portal) {
		this.portal = portal;
	}

	public Integer getNextLandSuggestedLevel() {
		return nextLandSuggestedLevel;
	}

	public void setNextLandSuggestedLevel(Integer nextLandSuggestedLevel) {
		this.nextLandSuggestedLevel = nextLandSuggestedLevel;
	}
	
	
	
}
