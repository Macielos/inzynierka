package com.inzynierkanew.entities.map;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

//@Entity
public class FieldType {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;
	private boolean passable;
	private String texture;
	
	public FieldType(String name, boolean passable, String texture) {
		super();
		this.name = name;
		this.passable = passable;
		this.texture = texture;
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
	
	
}
