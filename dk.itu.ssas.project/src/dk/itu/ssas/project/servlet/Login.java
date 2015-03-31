package dk.itu.ssas.project.servlet;
import java.io.IOException;
import java.sql.*;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dk.itu.ssas.project.persistence.DB;
import dk.itu.ssas.project.tools.MD5Converter;
import dk.itu.ssas.project.tools.Utils;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	final String SQL_SELECT = "SELECT id FROM users WHERE username= ? AND password= ?";
	int attempt = 0;
	boolean can_attempt = true;
	long last_attempt_time = 0;
	
	/** 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//check the form token
		
			Utils.checkFormToken(request);

		//check the user credentials.

			HttpSession session = request.getSession();
			long current_time = new Date().getTime();
			long time_diff = current_time - last_attempt_time;
			
			try {
				
				Connection con = DB.getConnection();
				String user = request.getParameter("username");
				String pwd = request.getParameter("password");
				pwd = MD5Converter.toMd5(pwd); // convert password to md5 hash
				PreparedStatement statement = con.prepareStatement(SQL_SELECT);
				statement.setString(1, user);
				statement.setString(2, pwd);
				ResultSet rs = statement.executeQuery();
				if (rs.next()) {
					// Have a result; user is authenticated.
					if (last_attempt_time == 0 || time_diff >= 600000)
					{
						session.setAttribute("user", rs.getString(1));
						session.setAttribute("username", user);
						session.setAttribute("secret", Utils.getRandomSecret());
						response.sendRedirect("main.jsp");
						last_attempt_time = 0;
						attempt = 0;
					}
					else
					{
						session.setAttribute("message", "Login failure limit exceeded. Please wait 10 minutes.");
						response.sendRedirect("index.jsp?login_failure=1");
					}
				} else {
					// No result; user failed to authenticate; try again.
					if (attempt >= 4 )
		            {        
		            	last_attempt_time = session.getLastAccessedTime();
		            	session.setAttribute("message", "Login failure limit exceeded. Please wait 10 minutes.");
		            }
		            else
		            {
		                 attempt++;
		                 int attempts_left = 5 - attempt;
		                 session.setAttribute("message", "Invalid username or password, " + attempts_left + " attempts remaining.");
		            }
		            
		            response.sendRedirect("index.jsp?login_failure=1");
				}
				
			} catch (SQLException e) {
				throw new ServletException(e);
			}
	}
}