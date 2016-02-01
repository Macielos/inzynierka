package com.explorersguild.entities.map;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Land {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long townId;

	private boolean hasTown;
	private boolean hasFreePassage;

	private long mapSegment;
	
	private int suggestedLevel;

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

	public Long getTownId() {
		return townId;
	}

	public void setTownId(Long townId) {
		this.townId = townId;
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

	public boolean hasTown() {
		return hasTown;
	}

	public void setHasTown(boolean hasTown) {
		this.hasTown = hasTown;
	}

	public int getWidth() {
		return maxX - minX + 1;
	}

	public int getHeight() {
		return maxY - minY + 1;
	}

	public long getMapSegment() {
		return mapSegment;
	}

	public void setMapSegment(long mapSegment) {
		this.mapSegment = mapSegment;
	}

	public int getSuggestedLevel() {
		return suggestedLevel;
	}

	public void setSuggestedLevel(int suggestedLevel) {
		this.suggestedLevel = suggestedLevel;
	}
}
