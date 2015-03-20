package dk.itu.ssas.project;

import javax.servlet.http.HttpSession;

public class Utils {
	public static boolean isSessionValid(HttpSession session) {
		if(session.getAttribute("user") == null || session.getAttribute("user").toString().length() == 0) return false;
		if(session.getAttribute("username") == null || session.getAttribute("username").toString().length() == 0) return false;
		return true;
	}
	
	public static final String TITLE = "SSAS Photo Sharing Webapp";
}
