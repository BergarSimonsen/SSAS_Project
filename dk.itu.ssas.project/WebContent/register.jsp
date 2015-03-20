<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import = "java.sql.*"
    import = "dk.itu.ssas.project.MD5Converter"
    import = "dk.itu.ssas.project.DB" 
%>
<% 
	final String SQL_SELECT = "SELECT * FROM users WHERE username = ?";
	final String SQL_INSERT = "INSERT INTO users (username, password) values (?, ?)";

	String user = request.getParameter("username");   
    String pwd = request.getParameter("password");
    pwd = MD5Converter.toMd5(pwd); // convert password to md5 hash
   
    Connection con = DB.getConnection();
//     Statement st = con.createStatement();
//     ResultSet rs = st.executeQuery("SELECT * FROM users WHERE username='" + user + "'");
    
    PreparedStatement statement = con.prepareStatement(SQL_SELECT);
    statement.setString(1, user);
    ResultSet rs = statement.executeQuery();
    
    
    if (rs.next()) {
    	// Have a result; username already taken.  	
    	response.sendRedirect("index.jsp?create_failure=1");       
    } else {
    	// No result; user failed to authenticate; try again.
    	statement = con.prepareStatement(SQL_INSERT);
    	statement.setString(1, user);
		statement.setString(2, pwd);
    	statement.executeUpdate();
//     	st.executeUpdate("INSERT INTO users (username, password) values ('" + user + "', '" + pwd + "')");
    	
    	statement = con.prepareStatement(SQL_SELECT);
        statement.setString(1, user);
        rs = statement.executeQuery();
    	
//     	rs = st.executeQuery("SELECT * FROM users WHERE username='" + user + "'");
    	rs.next();
    	session.setAttribute("user", rs.getString(1));
    	session.setAttribute("username", user);
    	response.sendRedirect("main.jsp");
    }
%>