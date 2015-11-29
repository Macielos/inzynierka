package com.inzynierkanew.utils;

import com.inzynierkanew.entities.players.playerendpoint.model.Player;

public class RegistrationContainer {
	
	private Player player;
	private int strength;
	private int agility;
	private int intelligence;
	
	public RegistrationContainer(Player player, int strength, int agility, int intelligence) {
		this.player = player;
		this.strength = strength;
		this.agility = agility;
		this.intelligence = intelligence;
	}
	
	public Player getPlayer() {
		return player;
	}
	public int getStrength() {
		return strength;
	}
	public int getAgility() {
		return agility;
	}
	public int getIntelligence() {
		return intelligence;
	}
	
	

}
