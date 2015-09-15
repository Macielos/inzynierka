package com.inzynierkanew.wrappers;


public class LoginResponse {
	
	private String sessionId;
	
	public LoginResponse() {
	}

	public LoginResponse(String sessionId) {
		this.sessionId = sessionId;
	}
	
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}	
}
