package com.inzynierkanew.wrappers;


public class LoginResponse {
	
	private String sessionId;
	
	public LoginResponse() {
		sessionId = null;
	}

	public LoginResponse(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getSessionId() {
		return sessionId;
	}

}
