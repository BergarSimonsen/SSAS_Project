package dk.itu.ssas.project.tools;

import javax.servlet.http.HttpSession;

import java.math.BigInteger;
import java.security.SecureRandom;

public class Utils {
	private static SecureRandom random;
	public static final String TITLE = "SSAS Photo Sharing Webapp";
	
	private static String[] htmlSymbols = new String[] {
		"&", 
		"<", 
		">", 
		"\"", 
		"'", 
		"/"
		};
	
	private static String[] escapeSymbols = new String[] {
		"&amp;", 
		"&lt;", 
		"&gt;", 
		"&quot;", 
		"&#x27;", 
		"&#x2F;"
		};
	
	public static boolean isSessionValid(HttpSession session) {
		if(session.getAttribute("user") == null || session.getAttribute("user").toString().length() == 0) return false;
		if(session.getAttribute("username") == null || session.getAttribute("username").toString().length() == 0) return false;
		return true;
	}
	
	/**
	 * Escape a html String, making it safe to insert into database
	 */
	public static String escapeHtml(String input) {
		String retval = "";
		
		
		// Check that htmlSymbols.length == escapeSymbols.length
		if(htmlSymbols.length == escapeSymbols.length) {
			retval = input;
			for(int i = 0; i < htmlSymbols.length; i++) {
				retval = retval.replace(htmlSymbols[i], escapeSymbols[i]);
			}
		} 
		
		return retval;
	}
	
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
