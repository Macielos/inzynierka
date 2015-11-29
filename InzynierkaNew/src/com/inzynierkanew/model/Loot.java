package com.inzynierkanew.model;

import java.util.List;

import com.inzynierkanew.entities.players.itemendpoint.model.Item;

public class Loot {
	
	private int gold;
	private List<Item> items;
	
	public Loot(int gold, List<Item> items) {
		super();
		this.gold = gold;
		this.items = items;
	}

	public int getGold() {
		return gold;
	}

	public List<Item> getItems() {
		return items;
	}
	
}
