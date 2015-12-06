package com.inzynierkanew.entities.players;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Item {

	public enum ItemClass {
		STANDARD, MAGICAL, LEGENDARY;
		}
	
	@Id
	private Long id;
	
	private String name;
	private ItemClass itemClass;
	private int itemLevel;
	
	private int strengthBonus;
	private int agilityBonus;
	private int intelligenceBonus;
	
	public Item(Long id, String name, ItemClass itemClass, int itemLevel,
			int strengthBonus, int agilityBonus, int intelligenceBonus) {
		this.id = id;
		this.name = name;
		this.itemClass = itemClass;
		this.itemLevel = itemLevel;
		this.strengthBonus = strengthBonus;
		this.agilityBonus = agilityBonus;
		this.intelligenceBonus = intelligenceBonus;
	}
	
	public Long getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public ItemClass getItemClass() {
		return itemClass;
	}
	
	public int getItemLevel() {
		return itemLevel;
	}
	
	public int getStrengthBonus() {
		return strengthBonus;
	}
	
	public int getAgilityBonus() {
		return agilityBonus;
	}
	
	public int getIntelligenceBonus() {
		return intelligenceBonus;
	}
	
}
