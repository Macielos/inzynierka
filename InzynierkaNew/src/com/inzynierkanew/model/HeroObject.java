package com.inzynierkanew.model;

import java.util.Queue;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

import com.inzynierkanew.entities.players.playerendpoint.model.Hero;
import com.inzynierkanew.utils.Constants;
import com.inzynierkanew.utils.Point;

public class HeroObject implements IRenderable {
	
	public static final int UP = 1;
	public static final int DOWN = 2;
	public static final int LEFT = 3;
	public static final int RIGHT = 4;
	public static final int NONE = 0;
	
	private static final float DELTA = 0.1f;
	private static final float SPEED = 0.1f;
	
	private final Bitmap bitmap;
	private Hero hero;

	private float offsetX;
	private float offsetY;
	
	private float localX;
	private float localY;
	
	private int direction = NONE;
	
	private Queue<Point> path = null;
	private Point nextPoint;
	
	private int cornerX;
	private int cornerY;
	
	public HeroObject(Bitmap bitmap, Hero hero, int cornerX, int cornerY) {
		this.bitmap = bitmap;
		this.hero = hero;
		this.cornerX = cornerX;
		this.cornerY = cornerY;
		this.localX = hero.getX()-cornerX;
		this.localY = hero.getY()-cornerY;
	}

	public Hero getHero() {
		return hero;
	}

	public void setHero(Hero hero) {
		this.hero = hero;
	}

	public Bitmap getBitmap() {
		return bitmap;
	}

	@Override
	public void render(Canvas canvas) {
		canvas.drawBitmap(bitmap, offsetX+Constants.TILE_SIZE*localX, offsetY+Constants.TILE_SIZE*localY, null);
	}

	@Override
	public void update() {
		if(path==null){
			return;
		}
		direction = getDirection();
		switch(direction){
		case LEFT:
			localX-=SPEED;
			break;
		case RIGHT:
			localX+=SPEED;
			break;
		case UP:
			localY-=SPEED;
			break;
		case DOWN:
			localY+=SPEED;
			break;
		case NONE:
			localX = nextPoint.x;
			localY = nextPoint.y;
			nextPoint = path.poll();
			if(nextPoint==null){
				path = null;
			}
			break;
		}
	}

	public void setOffset(float offsetX, float offsetY){
		this.offsetX = offsetX;
		this.offsetY = offsetY;
	}
	
	public void setCornerCoordinates(int cornerX, int cornerY){
		this.cornerX = cornerX;
		this.cornerY = cornerY;
	}
	
	public void setPath(Queue<Point> path){
		this.path = path;
		this.direction = NONE;
		this.nextPoint = path.poll();
		if(nextPoint==null){
			path = null;
		}
	}
	
	public void setGlobalCoordinates(int localX, int localY){
		hero.setX(localX+cornerX);
		hero.setY(localY+cornerY);
	}
	
	private int getDirection(){
		float diffX = nextPoint.x - localX;
		float diffY = nextPoint.y - localY;
		if(Math.abs(diffX) > Math.abs(diffY)){
			if(diffX > DELTA){
				return RIGHT;
			} else if(diffX < -DELTA){
				return LEFT;
			} else {
				return NONE;
			}
		} else {
			if(diffY > DELTA){
				return DOWN;
			} else if(diffY < -DELTA){
				return UP;
			} else {
				return NONE;
			}
		}
	}
	
	public int getLocalX(){
		return hero.getX() - cornerX;
	}
	
	public int getLocalY(){
		return hero.getY() - cornerY;
	}
	
}
