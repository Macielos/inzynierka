package com.explorersguild.entities.players;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Faction {

	@Id
	private Long id;
	
	private String name;
	
	private boolean appearsInTowns;
	
	private boolean appearsInDungeons;

	public Faction(Long id, String name, boolean appearsInTowns, boolean appearsInDungeons) {
		this.id = id;
		this.name = name;
		this.appearsInTowns = appearsInTowns;
		this.appearsInDungeons = appearsInDungeons;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public boolean isAppearsInTowns() {
		return appearsInTowns;
	}

	public boolean isAppearsInDungeons() {
		return appearsInDungeons;
	}
	
}
