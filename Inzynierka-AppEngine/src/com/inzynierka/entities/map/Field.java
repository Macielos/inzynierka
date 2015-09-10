package com.inzynierka.entities.map;

import java.io.Serializable;

public class Field implements Serializable {

	private static final long serialVersionUID = 3107055016837598861L;
	
	private int x;
	private int y;
	private int type;
	
	public Field(int x, int y, int type) {
		super();
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

	public int getType() {
		return type;
	}
		
}
