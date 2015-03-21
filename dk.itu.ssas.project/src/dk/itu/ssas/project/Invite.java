package dk.itu.ssas.project;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Invite
 */
@WebServlet("/Invite")
public class Invite extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String INSERT_PERM = "INSERT INTO perms (image_id, user_id) SELECT ?, users.id FROM users WHERE users.username = ?";


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		try
		{
			Connection con = DB.getConnection();
			PreparedStatement st = con.prepareStatement(INSERT_PERM);
			st.setString(1, request.getParameter("image_id"));
			st.setString(2, request.getParameter("other"));
			st.executeUpdate();
					
			response.sendRedirect("main.jsp");
		}
		catch (SQLException e) 
		{
			throw new ServletException(e);
		}
	}
}
