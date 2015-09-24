package com.inzynierkanew.entities.map;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Land {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne(cascade=CascadeType.ALL)
	private Town town;
	
	@OneToMany(cascade = CascadeType.ALL)
	private List<Passage> passages = new ArrayList<>();
	
	@OneToMany(cascade = CascadeType.ALL)
	private List<Dungeon> dungeons = new ArrayList<>();
	
	private int x;
	private int y;
	private int width;
	private int height; 
	private long[] fields;
	
	public long getId() {
		return id;
	}

	public Town getTown() {
		return town;
	}

	public void setTown(Town town) {
		this.town = town;
	}

	public List<Passage> getPassages() {
		return passages;
	}

	public void setPassages(List<Passage> passages) {
		this.passages = passages;
	}

	public List<Dungeon> getDungeons() {
		return dungeons;
	}

	public void setDungeons(List<Dungeon> dungeons) {
		this.dungeons = dungeons;
	}

	public long[] getFields() {
		return fields;
	}

	public void setFields(long[] fields) {
		this.fields = fields;
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

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
	public int getRightBorderX(){
		return x+width-1;
	}
	
	public int getBottomBorderY(){
		return y+height-1;
	}
	
}
