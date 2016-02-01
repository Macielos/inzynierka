package com.explorersguild.shared;

public class ValidationResult {
	
	public final boolean valid;
	public final String error;
	
	public ValidationResult(boolean valid, String error) {
		this.valid = valid;
		this.error = error;
	}
	
}
