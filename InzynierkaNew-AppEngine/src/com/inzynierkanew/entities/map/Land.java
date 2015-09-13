package com.inzynierkanew.entities.map;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

//@Entity
public class Land {

	private static final long serialVersionUID = -7634942289359669606L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Field[] fields;
	
	public Land(Field[] fields) {
		super();
		this.fields = fields;
	}

	public long getId() {
		return id;
	}
	
	public Field[] getFields() {
		return fields;
	}
	
}
