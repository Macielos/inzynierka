package com.inzynierkanew.entities.map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

//@Entity
public class Passage extends Field {

	private static final long serialVersionUID = -4364131150495077922L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	long landA;
	@Column
	long landB;
	
	public Passage(int x, int y, long type, long landA, long landB) {
		super(x, y, type);
		this.landA = landA;
		this.landB = landB;
	}
	
	public long getId() {
		return id;
	}
	
	public long getLandA() {
		return landA;
	}
	public long getLandB() {
		return landB;
	}
}
