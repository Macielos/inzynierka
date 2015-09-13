package com.inzynierkanew.entities.communication;

import java.io.Serializable;

public class Hero implements Serializable {

	private static final long serialVersionUID = -7143962525038660012L;
	
	private String name;
	private int level;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
	
}
