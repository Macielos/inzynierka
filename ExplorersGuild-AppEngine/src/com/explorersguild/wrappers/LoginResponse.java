package com.explorersguild.wrappers;


public class LoginResponse {
	
	public final String playerId;
	public final String message;

	public LoginResponse(String message) {
		this.playerId = null;
		this.message = message;
	}
	
	public LoginResponse(String playerId, String message) {
		this.playerId = playerId;
		this.message = message;
	}

}
