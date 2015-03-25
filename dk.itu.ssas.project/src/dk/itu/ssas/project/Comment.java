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
		
			if (!request.getParameter("token").equals(request.getSession().getAttribute("secret"))){
				throw new ServletException("Stop - Where did you get that form?");
			}
		
		//check the user is authorized
			
			if (!Utils.isSessionValid(request.getSession()))
				throw new ServletException("Not authorized");
				
		//do the insert
				
			try {
				
				Connection con = DB.getConnection();
				
				// escape html tags in comment
				String comment = Utils.escapeHtml(request.getParameter("comment"));

				PreparedStatement st = con.prepareStatement(INSERT_COMMENT);
				st.setString(1, request.getParameter("image_id"));
				st.setString(2, request.getParameter("user_id"));
				st.setString(3, comment);
				st.executeUpdate();

				response.sendRedirect("main.jsp");
				
			} catch (Exception e) {
				throw new ServletException(e);
			}


	}

}
