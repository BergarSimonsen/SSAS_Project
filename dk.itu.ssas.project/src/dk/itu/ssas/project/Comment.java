package dk.itu.ssas.project;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Comment
 */
@WebServlet("/Comment")
public class Comment extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private final String INSERT_COMMENT = "INSERT INTO comments (image_id, user_id, comment) VALUES(?, ?, ?)";

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//check the form token
		
			Utils.checkFormToken(request);

		//Get user id, and at the same time check the user is logged in.
			
			String user_id = Utils.getUserId(request);
		
		//check the user is authorized
				
				
		//do the insert
				
			try {
				
				Connection con = DB.getConnection();
				
				// escape html tags in comment
				String comment = Utils.escapeHtml(request.getParameter("comment"));

				PreparedStatement st = con.prepareStatement(INSERT_COMMENT);
				st.setString(1, request.getParameter("image_id"));
				st.setString(2, user_id);
				st.setString(3, comment);
				st.executeUpdate();
				
			} catch (Exception e) {
				throw new ServletException(e);
			}
			
		//done

			response.sendRedirect("main.jsp");

	}

}
