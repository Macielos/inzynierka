package com.inzynierkanew.entities.map;

import javax.persistence.Entity;

@Entity
public class Town extends BaseField {

	private static final long serialVersionUID = -4364131150495077922L;
	
	private String name;
	
	public Town(int x, int y, long type, String name) {
		super(x, y, type);
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
