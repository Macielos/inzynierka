package com.inzynierkanew.wrappers;

import java.util.Date;

public class LoginResponse {
	
	private String sessionId;
	private Date loginTime;
	
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public Date getLoginTime() {
		return loginTime;
	}
	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}
	
}
