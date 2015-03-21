package dk.itu.ssas.project;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileItemIterator;
import org.apache.tomcat.util.http.fileupload.FileItemStream;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

/**
 * Servlet implementation class Uploader
 */
@WebServlet("/Uploader")
public class Uploader extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		try {
			// Create a factory for disk-based file items
			DiskFileItemFactory factory = new DiskFileItemFactory();

			// Configure a repository (to ensure a secure temp location is used)
			ServletContext servletContext = this.getServletConfig().getServletContext();
			File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
			factory.setRepository(repository);

			// Create a new file upload handler
			ServletFileUpload upload = new ServletFileUpload(factory);

			//alternative way to get a input stream.
			FileItemIterator items2 = upload.getItemIterator(request);
			FileItemStream fileStream = items2.next();
			InputStream iis = fileStream.openStream();
			
			// Parse the request
//			List<FileItem> items = upload.parseRequest(request);
//			FileItem file = items.iterator().next();
//			InputStream iis = file.getInputStream();
			
			Connection con = DB.getConnection();

			String sql = "INSERT INTO images (jpeg, owner) values (?, ?)";
			PreparedStatement statement = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			statement.setBlob(1, iis);
			statement.setString(2, request.getSession().getAttribute("user").toString());
			statement.executeUpdate();

			ResultSet rs = statement.getGeneratedKeys();
			rs.next();
			String image_id = rs.getString(1);

			sql = "INSERT INTO perms (image_id, user_id) values (?, ?)";
			PreparedStatement st = con.prepareStatement(sql);
			st.setString(1, image_id);
			st.setString(2, request.getSession().getAttribute("user").toString());
			st.executeUpdate();

			response.sendRedirect("main.jsp");
		} 
		catch (SQLException | FileUploadException e) 
		{
			throw new ServletException(e);
		}		
	}

}
