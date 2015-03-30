package dk.itu.ssas.project.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dk.itu.ssas.project.persistence.DB;
import dk.itu.ssas.project.tools.Utils;



/**
 * Servlet implementation class Downloader
 */
@WebServlet("/Downloader")
public class Downloader extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String SELECT_IMAGE = "SELECT jpeg FROM images WHERE id = ?";

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	//Get user id, and at the same time check the user is logged in.

		String user_id = Utils.getUserId(request);

	//check the user is authorized to download the image
		
		
	//send the image
	
		try {
			
			Connection con = DB.getConnection();
			String image_id = request.getParameter("image_id");
			
			PreparedStatement st = con.prepareStatement(SELECT_IMAGE);
			st.setString(1, image_id);
			ResultSet image = st.executeQuery();
			image.next();
			byte[] content = image.getBytes("jpeg");
			response.setContentType("image/jpeg");
			response.setContentLength(content.length);
			response.getOutputStream().write(content);		
		
		} catch (SQLException e) {
			
			throw new ServletException(e);
			
		}
		
	}
}
