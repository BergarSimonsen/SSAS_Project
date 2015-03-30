<%@ page 
	language="java" 
	contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    
    import = "java.sql.*"
    import = "org.apache.commons.lang3.StringEscapeUtils"

    import = "dk.itu.ssas.project.Utils"
    import = "dk.itu.ssas.project.DB"
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%

	//don't cache

		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
		response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
		response.setDateHeader("Expires", 0); // Proxies.

	//login

		Utils.assertUserLoggedIn(session);
	
		String user_id	= session.getAttribute("user").toString();
		String username = session.getAttribute("username").toString();
	
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><c:out value="${Utils.TITLE}" /></title>
<style>
* {
	font-family: helvetica-neue, helvetica, arial;
	font-size: 10pt;
}
ul {
    list-style-type: none;
}
</style>
</head>
<body>
<p>Hello, <c:out value="${sessionScope.username}" />! <a href="logout.jsp">Log out</a>

<p><form method="post" enctype="multipart/form-data" action="Uploader">
	Add a picture: 
	<input type="hidden" name="token" value="${sessionScope.secret}">		
	<input type="file" name="pic" accept="jpeg">
	<input type="submit" value="Upload!">
</form>

<ul>
<%

	final String SELECT_IMAGE = "SELECT DISTINCT image_id FROM perms WHERE perms.user_id = ?";
	final String SELECT_OTHER = "SELECT username FROM users INNER JOIN images WHERE users.id = images.owner AND  images.id = ?";
	final String SELECT_VIEWERS = "SELECT users.username FROM users INNER JOIN perms WHERE users.id = perms.user_id AND perms.image_id = ?";
	final String SELECT_COMMENTS = "SELECT comments.comment, users.username FROM comments INNER JOIN users WHERE users.id = comments.user_id AND comments.image_id = ?";
	
	Connection con = DB.getConnection();
	PreparedStatement st = con.prepareStatement(SELECT_IMAGE);
	PreparedStatement st2 = con.prepareStatement(SELECT_OTHER);
	PreparedStatement st3 = con.prepareStatement(SELECT_VIEWERS);
	PreparedStatement st4 = con.prepareStatement(SELECT_COMMENTS);

	st.setString(1, user_id);
	ResultSet image_ids = st.executeQuery();

	while (image_ids.next()) {
    	  String image_id = image_ids.getString(1);
    	  st2.setString(1, image_id);
    	  ResultSet other = st2.executeQuery();
    	  other.next();
    	  String other_name = other.getString(1);
  %>
	<li> Posted by <%= other_name %>:<br><br>
	   <img src="Downloader?image_id=<%= image_id %>" width="60%"><br>
	    Shared with: 
<%      
		st3 = con.prepareStatement(SELECT_VIEWERS);
	   	st3.setString(1, image_id);
	   	ResultSet viewers = st3.executeQuery();
		while (viewers.next()) {
			String sharee = viewers.getString(1);
			if (sharee.equals (username)) {
				continue;
			}
			
			out.println(sharee);
		}			
%>
		<br><br>
<%
		st4 = con.prepareStatement(SELECT_COMMENTS);
		st4.setString(1, image_id);
		ResultSet comments = st4.executeQuery();
		
        while (comments.next()) {
%>
			From <b><%= comments.getString(2) %></b>: "<%= comments.getString(1) %>"<br>
<%
        }
 %>
 <br>
		<form action="Comment" method="post">
        	<input type='text' name='comment'>
            <input type="submit" value="Post comment!">
            <input type="hidden" name="image_id" value='<%= image_id %>'>
            <input type="hidden" name="token" value="<%= session.getAttribute("secret") %>">		
   		</form>	
   		<br>
   		
   		<form action="Invite" method="post">
   			<input type='text' name='other'>
            <input type="submit" value="Share image!">
            <input type="hidden" name="image_id" value="<%= image_id %>">
            <input type="hidden" name="token" value="<%= session.getAttribute("secret") %>">		
   		</form>
   		<br>
   		
	 </li>       
<%
	}
%>     
</ul>   

</body>
</html>