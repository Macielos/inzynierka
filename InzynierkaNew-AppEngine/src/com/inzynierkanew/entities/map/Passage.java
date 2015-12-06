package com.inzynierkanew.entities.map;

import javax.persistence.Entity;

@Entity
public class Passage extends BaseField {

	private static final long serialVersionUID = -4364131150495077922L;
	
//	@Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
		
	private int direction;
	
	private Long nextLandId;
	
	private int nextX;
	
	private int nextY;
		
	private boolean portal;
	
	public Passage(int x, int y, long type, int direction) {
		super(x, y, type);
		this.direction = direction;
		this.portal = false;
	}
	
	public Passage(int x, int y, long type, int direction, Long nextLandId, int nextX, int nextY) {
		super(x, y, type);
		this.direction = direction;
		this.nextLandId = nextLandId;
		this.nextX = nextX;
		this.nextY = nextY;
		this.portal = false;
	}
	
//	public Long getId() {
//		return id;
//	}

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
}
