package com.inzynierka.entities.map;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Land {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
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
