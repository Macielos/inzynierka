package com.explorersguild.model;

import java.util.List;

import com.explorersguild.entities.players.itemtypeendpoint.model.ItemType;

public class Loot {
	
	private int gold;
	private List<ItemType> items;
	
	public Loot(int gold, List<ItemType> items) {
		super();
		this.gold = gold;
		this.items = items;
	}

	public int getGold() {
		return gold;
	}

	public List<ItemType> getItems() {
		return items;
	}
	
}
