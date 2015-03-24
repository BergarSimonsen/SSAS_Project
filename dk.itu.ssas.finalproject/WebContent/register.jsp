<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import = "java.sql.*"
    import = "dk.itu.ssas.finalproject.MD5Converter"
    import = "dk.itu.ssas.finalproject.DB" 
%>
<% 
	final String SQL_SELECT = "SELECT * FROM users WHERE username = ?";
	final String SQL_INSERT = "INSERT INTO users (username, password) values (?, ?)";

    Connection con = DB.getConnection();

    String user = request.getParameter("username");   
    String pwd = request.getParameter("password");
    pwd = MD5Converter.toMd5(pwd); // convert password to md5 hash
    
    PreparedStatement statement = con.prepareStatement(SQL_SELECT);
    statement.setString(1, user);
    ResultSet rs = statement.executeQuery();
    
    
    if (rs.next()) {
    	// Have a result; username already taken.  	
    	response.sendRedirect("index.jsp?create_failure=1");       
    } else {
    	// No result; The new user is created 
    	statement = con.prepareStatement(SQL_INSERT);
    	statement.setString(1, user);
		statement.setString(2, pwd);
    	statement.executeUpdate();
    	
    	statement = con.prepareStatement(SQL_SELECT);
        statement.setString(1, user);
        rs = statement.executeQuery();
    	
    	rs.next();
    	session.setAttribute("user", rs.getString(1));
    	session.setAttribute("username", user);
    	response.sendRedirect("main.jsp");
    }
%>