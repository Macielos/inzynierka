package com.inzynierkanew.entities.players;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

//@PersistenceAware
@Entity
public class Player {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	private String password;

	private String deviceRegistrationID;

	private Date registrationTime;

	private String sessionId;

	private Date lastLogin;

//	@OneToOne(cascade = CascadeType.PERSIST)
//	private Hero hero;
	
	private Long heroId;

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDeviceRegistrationID() {
		return deviceRegistrationID;
	}

	public void setDeviceRegistrationID(String deviceRegistrationID) {
		this.deviceRegistrationID = deviceRegistrationID;
	}

	public Date getRegistrationTime() {
		return registrationTime;
	}

	public void setRegistrationTime(Date registrationTime) {
		this.registrationTime = registrationTime;
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

//	public Hero getHero() {
//		return hero;
//	}
//
//	public void setHero(Hero hero) {
//		this.hero = hero;
//	}
	
	public Long getHeroId() {
		return heroId;
	}

	public void setHeroId(Long heroId) {
		this.heroId = heroId;
	}

}
