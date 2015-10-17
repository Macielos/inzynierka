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
	
	private boolean hasFreePassage;
	private boolean hasTown;
	
	private long mapSegment;
	
	private int minX;
	private int minY;
	private int maxX;
	private int maxY; 
	private int[] fields;
	
	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
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

	public int[] getFields() {
		return fields;
	}

	public void setFields(int[] fields) {
		this.fields = fields;
	}

	public int getMinX() {
		return minX;
	}

	public void setMinX(int minX) {
		this.minX = minX;
	}

	public int getMinY() {
		return minY;
	}

	public void setMinY(int minY) {
		this.minY = minY;
	}

	public int getMaxX() {
		return maxX;
	}

	public void setMaxX(int maxX) {
		this.maxX = maxX;
	}

	public int getMaxY() {
		return maxY;
	}

	public void setMaxY(int maxY) {
		this.maxY = maxY;
	}
	
	public boolean hasFreePassage() {
		return hasFreePassage;
	}

	public void setHasFreePassage(boolean hasFreePassage) {
		this.hasFreePassage = hasFreePassage;
	}

	public int getWidth(){
		return maxX-minX+1;
	}

	public int getHeight(){
		return maxY-minY+1;
	}

	public long getMapSegment() {
		return mapSegment;
	}

	public void setMapSegment(long mapSegment) {
		this.mapSegment = mapSegment;
	}

	public boolean hasTown() {
		return hasTown;
	}

	public void setHasTown(boolean hasTown) {
		this.hasTown = hasTown;
	}

}
