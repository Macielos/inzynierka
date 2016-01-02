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
	private int value;
	
	private String icon;
	
	private int attackBonus;
	private int defenceBonus;
	private int powerBonus;
		
	public Item(Long id, String name, ItemClass itemClass, int itemLevel, String icon, int value, 
			int attackBonus, int defenceBonus, int powerBonus) {
		this.id = id;
		this.name = name;
		this.itemClass = itemClass;
		this.itemLevel = itemLevel;
		this.value = value;
		this.icon = icon;
		this.attackBonus = attackBonus;
		this.defenceBonus = defenceBonus;
		this.powerBonus = powerBonus;
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
		
	public int getAttackBonus() {
		return attackBonus;
	}
	
	public int getDefenceBonus() {
		return defenceBonus;
	}
	
	public int getPowerBonus() {
		return powerBonus;
	}

}
