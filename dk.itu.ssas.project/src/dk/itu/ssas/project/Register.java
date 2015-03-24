package dk.itu.ssas.project;

import java.io.IOException;
import java.sql.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Register
 */
@WebServlet("/Register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private final String SQL_SELECT = "SELECT * FROM users WHERE username = ?";
	private final String SQL_INSERT = "INSERT INTO users (username, password) values (?, ?)";

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		
	//check the form token
		
		if (!request.getParameter("token").equals(session.getAttribute("secret"))){
			throw new ServletException("Stop - Where did you get that form?");
		}
		
	//create user

		try {
			
		    Connection con = DB.getConnection();
		    	
		    String user = request.getParameter("username");   
		    String pwd = request.getParameter("password");
		    pwd = MD5Converter.toMd5(pwd); // convert password to md5 hash
		    
		    PreparedStatement statement = con.prepareStatement(SQL_SELECT);
		    statement.setString(1, user);
		    ResultSet rs = statement.executeQuery();
		    
		    if (rs.next()) {
		    	
		    	// Have a result; username already taken.  	
		    	response.sendRedirect("index.jsp?create_failure=1");
		    	
		    } else {
		    	
		    	// No result; The new user is created 
		    	statement = con.prepareStatement(SQL_INSERT);
		    	statement.setString(1, user);
				statement.setString(2, pwd);
		    	statement.executeUpdate();
		    	
		    	statement = con.prepareStatement(SQL_SELECT);
		        statement.setString(1, user);
		        rs = statement.executeQuery();
		    	
		    	rs.next();
		    	session.setAttribute("user", rs.getString(1));
		    	session.setAttribute("username", user);
		    	response.sendRedirect("main.jsp");
		    }
		    
		} catch (SQLException e) {
			throw new ServletException(e);
		}
	    
	}

}
