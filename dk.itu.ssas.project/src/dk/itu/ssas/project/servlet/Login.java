package dk.itu.ssas.project.servlet;
import java.io.IOException;
import java.sql.*;
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
	
	/** 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
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
				session.setAttribute("user", rs.getString(1));
				session.setAttribute("username", user);
				session.setAttribute("secret", Utils.getRandomSecret());
				response.sendRedirect("main.jsp");
			} else {
				// No result; user failed to authenticate; try again.
				response.sendRedirect("index.jsp?login_failure=1");
			}
		} catch (SQLException e) {
			throw new ServletException(e);
		}
	}
}