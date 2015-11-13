package com.inzynierkanew.entities.general;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Property {

	@Id
	private String key;

	private String value;

	public Property(String key, String value) {
		super();
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public String getValue() {
		return value;
	}
		
}
