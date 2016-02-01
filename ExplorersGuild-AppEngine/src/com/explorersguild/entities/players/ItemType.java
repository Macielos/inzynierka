package com.explorersguild.entities.players;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ItemType {

	public enum ItemClass {
		STANDARD, MAGICAL, LEGENDARY;
		}
	
	@Id
	private Long id;
	
	private String name;
	private ItemClass itemClass;
	private int itemLevel;
	private int value;
	
	private String icon;
	
	private int strengthBonus;
	private int agilityBonus;
	private int intelligenceBonus;
		
	public ItemType(Long id, String name, ItemClass itemClass, int itemLevel, String icon, int value, 
			int strengthBonus, int agilityBonus, int intelligenceBonus) {
		this.id = id;
		this.name = name;
		this.itemClass = itemClass;
		this.itemLevel = itemLevel;
		this.value = value;
		this.icon = icon;
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
	
	public int getValue() {
		return value;
	}

	public String getIcon() {
		return icon;
	}

	public int getStrengthBonus() {
		return strengthBonus;
	}

	public void setStrengthBonus(int strengthBonus) {
		this.strengthBonus = strengthBonus;
	}

	public int getAgilityBonus() {
		return agilityBonus;
	}

	public void setAgilityBonus(int agilityBonus) {
		this.agilityBonus = agilityBonus;
	}

	public int getIntelligenceBonus() {
		return intelligenceBonus;
	}

	public void setIntelligenceBonus(int intelligenceBonus) {
		this.intelligenceBonus = intelligenceBonus;
	}
	
}
