package com.explorersguild.entities.players;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class PlayerSession {
	
	@Id
	private Long id;

	private String sessionId;
	
	private Date lastLogin;
	
	public PlayerSession(Long id, String sessionId, Date lastLogin) {
		this.id = id;
		this.sessionId = sessionId;
		this.lastLogin = lastLogin;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}
		
	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}
}

