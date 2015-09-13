package com.inzynierkanew.wrappers;

public class ServerResponse {

	public final ResponseStatus status;
	public final String message;
	
	public ServerResponse(ResponseStatus status, String message) {
		this.status = status;
		this.message = message;
	}
	
}
