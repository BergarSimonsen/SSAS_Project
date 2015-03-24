<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" 
    import = "java.sql.*"
    import = "dk.itu.ssas.finalproject.DB"
    import = "dk.itu.ssas.finalproject.MD5Converter"
    import = "dk.itu.ssas.finalproject.Utils"
%>
<%
	final String SQL_SELECT = "SELECT id FROM users WHERE username= ? AND password= ?"; 

    Connection con = DB.getConnection();

    String user = request.getParameter("username");   
    String pwd = request.getParameter("password");
    
    pwd = MD5Converter.toMd5(pwd); // convert password to md5 hash
   
    PreparedStatement statement = con.prepareStatement(SQL_SELECT);
    statement.setString(1, user);
    statement.setString(2, pwd);
    ResultSet rs = statement.executeQuery();
    
    if (rs.next()) {
    	// Have a result; user is authenticated.
    	session.setAttribute("user", rs.getString(1));
    	session.setAttribute("username", user);
    	session.setAttribute("secret", Utils.getRandomSecret());
    	response.sendRedirect("main.jsp");
    } else {
    	// No result; user failed to authenticate; try again.
    	response.sendRedirect("index.jsp?login_failure=1");
    }
%>