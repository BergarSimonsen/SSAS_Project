package dk.itu.ssas.project;

import java.sql.Connection;
import java.sql.SQLException;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class DB {
	
	private static final String URL = "jdbc:mysql://localhost:3306/image_site_db";	
	private static final String USER = "SSAS";
	private static final String PASSWORD = "SSAS";
	
	public static Connection getConnection() throws SQLException {
		// Better way to do this is setting database connect info in
		// servlet context; but for the SSAS project, that just adds
		// complications. 
		MysqlDataSource ds = null;
		
			ds = new MysqlDataSource();
			ds.setUrl(URL);
			ds.setUser(USER);
			ds.setPassword(PASSWORD);
			return ds.getConnection();
	}
}
