package com.inzynierkanew.messages;

import java.io.Serializable;

import com.inzynierkanew.shared.SharedConstants;

public class WorldUpdate implements Serializable {

	private static final long serialVersionUID = -4349039223332883019L;

	public final Integer action;
	public final Long heroId;
	public final Long currentLandId;
	public final Integer newX;
	public final Integer newY;
	
	public WorldUpdate(Integer action, Long heroId, Long currentLandId, Integer newX, Integer newY) {
		this.action = action;
		this.heroId = heroId;
		this.newX = newX;
		this.newY = newY;
		this.currentLandId = currentLandId;
	}

	@Override
	public String toString() {
		return "WorldUpdate [action=" + action + ", heroId=" + heroId + ", currentLandId=" + currentLandId + ", newX="
				+ newX + ", newY=" + newY + "]";
	}
}
