package com.inzynierkanew.model;

import com.inzynierkanew.utils.Constants;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public abstract class GameObject implements IRenderable {
	
	protected final Bitmap bitmap;

	protected int x;
	protected int y;
	protected float xv;	
	protected float yv;
	
	public GameObject(Bitmap bitmap, int x, int y) {
		this.bitmap = bitmap;
		this.x = x;
		this.y = y;
		this.xv = 0;
		this.yv = 0;
	}
	
	public GameObject(Bitmap bitmap, int x, int y, float xv, float yv) {
		this.bitmap = bitmap;
		this.x = x;
		this.y = y;
		this.xv = xv;
		this.yv = yv;
	}

	public Bitmap getBitmap() {
		return bitmap;
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public float getXv() {
		return xv;
	}

	public void setXv(float xv) {
		this.xv = xv;
	}

	public float getYv() {
		return yv;
	}

	public void setYv(float yv) {
		this.yv = yv;
	}
	
	@Override
	public void render(Canvas canvas) {
		canvas.drawBitmap(bitmap, Constants.TILE_SIZE*x, Constants.TILE_SIZE*y, null);
	}
	
	@Override
	public void update() {

	}
		
}
