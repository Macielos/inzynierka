package com.explorersguild.model;

import java.util.Queue;

import com.explorersguild.entities.map.landendpoint.model.Land;
import com.explorersguild.entities.map.passageendpoint.model.Passage;
import com.explorersguild.entities.players.heroendpoint.model.Hero;
import com.explorersguild.game.GameView;
import com.explorersguild.utils.Point;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class HeroModel implements IRenderable {
	
	public static final int UP = 1;
	public static final int DOWN = 2;
	public static final int LEFT = 3;
	public static final int RIGHT = 4;
	public static final int NONE = 0;
	
	private static final float DELTA = 0.1f;
	private static final float SPEED = 0.1f;
	
	private GameView gameView;
	private Hero hero;

	private float localFloatX;
	private float localFloatY;
	
	private int localX;
	private int localY;

	private int direction = NONE;
	
	private Queue<Point> path = null;
	private Point nextPoint;
	
	private int cornerX;
	private int cornerY;
	
	private Bitmap[] heroBitmaps;
	
	public HeroModel(GameView gameView, Bitmap[] heroBitmaps, Hero hero) {
		this.gameView = gameView;
		this.heroBitmaps = heroBitmaps;
		this.hero = hero;
	}

	public Hero getHero() {
		return hero;
	}

	public void setHero(Hero hero) {
		this.hero = hero;
	}

	public Bitmap[] getHeroBitmaps() {
		return heroBitmaps;
	}
	
	public Queue<Point> getPath() {
		return path;
	}

	@Override
	public void render(Canvas canvas) {
		canvas.drawBitmap(selectBitmap(), gameView.getOffsetX()+gameView.getTileSize()*localFloatX, gameView.getOffsetY()+gameView.getTileSize()*localFloatY, null);
	}

	private Bitmap selectBitmap() {
		return direction == NONE ? heroBitmaps[0] : heroBitmaps[getDirection()-1];
	}

	@Override
	public void update() {
		if(path==null || nextPoint==null){
			return;
		}
		setDirection();
		switch(direction){
		case LEFT:
			localFloatX-=SPEED;
			break;
		case RIGHT:
			localFloatX+=SPEED;
			break;
		case UP:
			localFloatY-=SPEED;
			break;
		case DOWN:
			localFloatY+=SPEED;
			break;
		case NONE:
			localFloatX = localX = nextPoint.x;
			localFloatY = localY = nextPoint.y;
			nextPoint = path.poll();
			if(nextPoint==null){
				path = null;
			}
			break;
		}
	}
	
	public void setCornerCoordinates(int cornerX, int cornerY){
		this.cornerX = cornerX;
		this.cornerY = cornerY;
		this.localFloatX = this.localX = hero.getX()-cornerX;
		this.localFloatY = this.localY = hero.getY()-cornerY;
	}
	
	public void setPath(Queue<Point> path){
		this.path = path;
		this.direction = NONE;
		if(path==null){
			this.nextPoint = null;
		} else {
			this.nextPoint = path.poll();
			if(nextPoint==null){
				path = null;
			}
		}
	}
	
	public void setHeroCoordinates(int localX, int localY){
		hero.setX(localX+cornerX);
		hero.setY(localY+cornerY);
	}
	
	public void haltMovement(){
		localX = Math.round(localX);
		localY = Math.round(localY);
		hero.setX((int)localX+cornerX);
		hero.setY((int)localY+cornerY);
		path = null;
	}
	
	private void setDirection(){
		float diffX = nextPoint.x - localFloatX;
		float diffY = nextPoint.y - localFloatY;
		if(Math.abs(diffX) > Math.abs(diffY)){
			if(diffX > DELTA){
				direction = RIGHT;
				return;
			} else if(diffX < -DELTA){
				direction = LEFT;
				return;
			} else {
				direction = NONE;
				return;
			}
		} else {
			if(diffY > DELTA){
				direction = DOWN;
				return;
			} else if(diffY < -DELTA){
				direction = UP;
				return;
			} else {
				direction = NONE;
				return;
			}
		}
	}
	
	public void moveToNextLand(Passage passage, Land nextLand){
		hero.setCurrentLandId(nextLand.getId());
		hero.setX(passage.getNextX());
		hero.setY(passage.getNextY());
		cornerX = nextLand.getMinX();
		cornerY = nextLand.getMinY();
		this.localFloatX = this.localX = hero.getX()-cornerX;
		this.localFloatY = this.localY = hero.getY()-cornerY;
	}
	
	public int getDirection(){
		return direction;
	}
	
	public int getLocalX(){
		return localX;
	}
	
	public int getLocalY(){
		return localY;
	}
	
	public float getLocalFloatX(){
		return localFloatX;
	}
	
	public float getLocalFloatY(){
		return localFloatY;
	}
	
	public boolean onTheMove(){
		return path!=null && !path.isEmpty();
	}
	
	public boolean onObject(){
		return direction == NONE;
	}
	
}
