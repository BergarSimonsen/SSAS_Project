package dk.itu.ssas.project;

import javax.servlet.http.HttpSession;

import java.math.BigInteger;
import java.security.SecureRandom;

public class Utils {
	private static SecureRandom random;
	
	public static boolean isSessionValid(HttpSession session) {
		if(session.getAttribute("user") == null || session.getAttribute("user").toString().length() == 0) return false;
		if(session.getAttribute("username") == null || session.getAttribute("username").toString().length() == 0) return false;
		return true;
	}
	
	public static final String TITLE = "SSAS Photo Sharing Webapp";
	
	
	/**
	 * Generates a random string, which can be used for the secret every user has in his/her session.
	 * 
	 * @return String
	 */
	public static String getRandomSecret() {
		
		if (random == null)
			random = new SecureRandom();
		
		return new BigInteger(130, random).toString(32);
	}

}
