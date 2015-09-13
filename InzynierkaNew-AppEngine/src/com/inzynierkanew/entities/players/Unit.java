package com.inzynierkanew.entities.players;

import java.io.Serializable;

public class Unit implements Serializable {
	
	private long type;
	private int count;
	
	public Unit(long type, int count) {
		super();
		this.type = type;
		this.count = count;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public long getType() {
		return type;
	}
	
	
}
