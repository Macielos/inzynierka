package com.inzynierkanew.utils;

public class PointWithAttributes extends Point {

	private int f, g, h;
	
	public PointWithAttributes(int x, int y) {
		super(x, y);
	}

	public int getF() {
		return f;
	}

	public int getG() {
		return g;
	}
	
	public int getH() {
		return h;
	}
		
	public void setG(int g) {
		this.g = g;
		this.f = g+h;
	}

	public void setH(int h) {
		this.h = h;
		this.f = g+h;
	}

}
