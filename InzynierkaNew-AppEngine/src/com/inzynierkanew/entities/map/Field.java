package com.inzynierkanew.entities.map;

public class Field {

	private static final long serialVersionUID = 3107055016837598861L;

	private int x;
	private int y;
	private long type;
	
	public Field(int x, int y, long type) {
		this.x = x;
		this.y = y;
		this.type = type;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public long getType() {
		return type;
	}	
}
