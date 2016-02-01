package com.explorersguild.entities.map;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class FieldType {

	@Id
	private Long id;

	private String name;
	private boolean passable;
	private String texture;
	private double scale;
	
	public FieldType(Long id, String name, boolean passable, String texture, double scale) {
		this.id = id;
		this.name = name;
		this.passable = passable;
		this.texture = texture;
		this.scale = scale;
	}

	public long getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}

	public boolean isPassable() {
		return passable;
	}

	public String getTexture() {
		return texture;
	}

	public double getScale() {
		return scale;
	}
	
	
	
	
}
