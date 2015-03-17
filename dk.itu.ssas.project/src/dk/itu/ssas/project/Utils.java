package dk.itu.ssas.project;

import javax.servlet.http.HttpSession;

public class Utils {
	public static boolean isSessionValid(HttpSession session) {
		System.out.println(session.getAttribute("user") == null ? "user is null" : session.getAttribute("user").toString());
		System.out.println(session.getAttribute("username") == null ? "username is null" : session.getAttribute("username").toString());
		
		if(session.getAttribute("user") == null || session.getAttribute("user").toString().length() == 0) return false;
		if(session.getAttribute("username") == null || session.getAttribute("username").toString().length() == 0) return false;
		return true;
	}
}
