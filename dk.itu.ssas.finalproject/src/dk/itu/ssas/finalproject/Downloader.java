package dk.itu.ssas.finalproject;

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
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		try 
		{
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
		}
		catch (SQLException e) {
			throw new ServletException(e);
		}
	}
}
