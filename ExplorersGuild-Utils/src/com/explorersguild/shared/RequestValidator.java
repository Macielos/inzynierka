package com.explorersguild.shared;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class RequestValidator {
	
	private static final int MIN_NAME_LENGTH = 3;
	private static final int MIN_PASSWORD_LENGTH = 3;
	
	public static ValidationResult validateRegisterRequest(String name, byte[] password){
		if(StringUtils.isEmpty(name)){
			return new ValidationResult(false, "Name cannot be empty");
		}
		if(StringUtils.isEmpty(password)){
			return new ValidationResult(false, "Password cannot be empty");
		}
		if(name.length() < MIN_NAME_LENGTH){
			return new ValidationResult(false, "Name must contain at least "+MIN_NAME_LENGTH+" characters");
		}
		if(password.length < MIN_PASSWORD_LENGTH){
			return new ValidationResult(false, "Password must contain at least "+MIN_PASSWORD_LENGTH+" characters");
		}
		return new ValidationResult(true, null);
	}
	
	public static String hashPassword(byte[] password) {
        MessageDigest md;
		try {
			md = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
        md.update(password);
        
        byte byteData[] = md.digest();
 
        //convert the byte to hex format method 1
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
         sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }
     
        //convert the byte to hex format method 2
        StringBuffer hexString = new StringBuffer();
    	for (int i=0;i<byteData.length;i++) {
    		String hex=Integer.toHexString(0xff & byteData[i]);
   	     	if(hex.length()==1) hexString.append('0');
   	     	hexString.append(hex);
    	}
    	return hexString.toString();
	}

}
