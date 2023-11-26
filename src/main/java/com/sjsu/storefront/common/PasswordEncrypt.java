package com.sjsu.storefront.common;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordEncrypt {

	// Hash a password
	public static String hashPassword(String plainTextPassword) {
	    // Hash the password with a randomly generated salt
	    return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
	}
	
	// Check if a plain text password matches a hashed password
	public static boolean checkPassword(String plainTextPassword, String hashedPassword) {
	    // Check the password against the stored hash
	    return BCrypt.checkpw(plainTextPassword, hashedPassword);
	}
	
//	public static void main(String[] args) {
//	    // Example usage
//	    String plainTextPassword = "mySecurePassword";
//	
//	    // Hash the password before storing it in the database
//	    String hashedPassword = hashPassword(plainTextPassword);
//	    System.out.println("Hashed Password: " + hashedPassword);
//	
//	    // Example: Check if a login attempt is valid
//	    String userInputPassword = "userEnteredPassword";
//	
//	    if (checkPassword(userInputPassword, hashedPassword)) {
//	        System.out.println("Login Successful");
//	    } else {
//	        System.out.println("Invalid Password");
//	    }
//	}
}

